package com.taskflow;

import java.util.concurrent.*;
import java.util.*;

/**
 * TaskScheduler manages task submission and execution.
 * Uses PriorityQueue for priority-based scheduling (O(log n) for add).
 * Uses ConcurrentHashMap for fast task lookup (O(1)).
 * Uses ExecutorService with fixed thread pool for multithreading.
 * Thread-safe using synchronization for queue operations.
 */
public class TaskScheduler {
    private final PriorityQueue<Task> taskQueue;
    private final ConcurrentHashMap<Long, Task> taskMap;
    private final ExecutorService executor;
    private final int poolSize;

    public TaskScheduler(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new PriorityQueue<>();
        this.taskMap = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(poolSize);
        TaskLogger.log("TaskScheduler initialized with " + poolSize + " threads");
    }

    /**
     * Adds a task to the scheduler.
     * Time complexity: O(log n) due to PriorityQueue insertion.
     * @param task The task to add
     */
    public synchronized void addTask(Task task) {
        taskQueue.offer(task);
        taskMap.put(task.getTaskId(), task);
        TaskLogger.logTaskEvent("ADDED", task);
        // Submit to executor if possible
        submitNextTask();
    }

    /**
     * Retrieves a task by ID.
     * Time complexity: O(1) due to HashMap lookup.
     * @param taskId The task ID
     * @return The task or null if not found
     */
    public Task getTask(long taskId) {
        return taskMap.get(taskId);
    }

    /**
     * Submits the next highest priority task to the executor.
     * Called after adding a task or when a worker finishes.
     */
    private synchronized void submitNextTask() {
        if (!taskQueue.isEmpty()) {
            Task nextTask = taskQueue.poll();
            if (nextTask.getStatus() == TaskStatus.PENDING) {
                executor.submit(new Worker(nextTask));
            }
        }
    }

    /**
     * Shuts down the scheduler gracefully.
     * Waits for all tasks to complete.
     */
    public void shutdown() {
        TaskLogger.log("Shutting down TaskScheduler...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        TaskLogger.log("TaskScheduler shut down");
    }

    /**
     * Gets the current number of pending tasks.
     * @return Number of pending tasks
     */
    public synchronized int getPendingTaskCount() {
        return (int) taskQueue.stream().filter(t -> t.getStatus() == TaskStatus.PENDING).count();
    }
}
