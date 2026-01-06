package com.taskflow;

/**
 * Worker class that executes tasks.
 * Implements Runnable to be used with ExecutorService.
 * Simulates task execution by sleeping for the execution time.
 */
public class Worker implements Runnable {
    private final Task task;

    public Worker(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            task.setStatus(TaskStatus.RUNNING);
            TaskLogger.logTaskEvent("STARTED", task);

            // Simulate execution
            Thread.sleep(task.getExecutionTime());

            task.setStatus(TaskStatus.COMPLETED);
            TaskLogger.logTaskEvent("COMPLETED", task);
        } catch (InterruptedException e) {
            task.setStatus(TaskStatus.FAILED);
            TaskLogger.logTaskEvent("FAILED (Interrupted)", task);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            task.setStatus(TaskStatus.FAILED);
            TaskLogger.logTaskEvent("FAILED (" + e.getMessage() + ")", task);
        }
    }
}
