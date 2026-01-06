package com.taskflow;

/**
 * Enum representing the possible statuses of a task.
 * This helps in tracking the lifecycle of tasks in the scheduler.
 */
public enum TaskStatus {
    PENDING,    // Task is waiting to be executed
    RUNNING,    // Task is currently being executed
    COMPLETED,  // Task has finished successfully
    FAILED      // Task has failed during execution
}
