package com.taskflow;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a task in the TaskFlow scheduler.
 * Each task has a unique ID, priority, execution time, and status.
 * Priority is used for scheduling (higher priority first).
 * Execution time is simulated in milliseconds.
 */
public class Task implements Comparable<Task> {
    private static final AtomicLong idGenerator = new AtomicLong(1);

    private final long taskId;
    private final int priority; // Higher number means higher priority
    private final long executionTime; // In milliseconds
    private TaskStatus status;

    public Task(int priority, long executionTime) {
        this.taskId = idGenerator.getAndIncrement();
        this.priority = priority;
        this.executionTime = executionTime;
        this.status = TaskStatus.PENDING;
    }

    // Getters
    public long getTaskId() { return taskId; }
    public int getPriority() { return priority; }
    public long getExecutionTime() { return executionTime; }
    public TaskStatus getStatus() { return status; }

    // Setter for status (used by scheduler)
    public void setStatus(TaskStatus status) { this.status = status; }

    @Override
    public int compareTo(Task other) {
        // Higher priority first (reverse order)
        return Integer.compare(other.priority, this.priority);
    }

    @Override
    public String toString() {
        return String.format("Task{id=%d, priority=%d, execTime=%dms, status=%s}",
                taskId, priority, executionTime, status);
    }
}
