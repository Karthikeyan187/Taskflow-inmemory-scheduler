package com.taskflow;

/**
 * Simple unit tests for TaskScheduler.
 * No external testing frameworks used.
 */
public class TaskSchedulerTest {

    public static void main(String[] args) {
        testTaskCreation();
        testSchedulerAddTask();
        testSchedulerExecution();
        System.out.println("All tests passed!");
    }

    private static void testTaskCreation() {
        Task task = new Task(5, 1000);
        assert task.getTaskId() > 0 : "Task ID should be positive";
        assert task.getPriority() == 5 : "Priority should be 5";
        assert task.getExecutionTime() == 1000 : "Execution time should be 1000";
        assert task.getStatus() == TaskStatus.PENDING : "Initial status should be PENDING";
        System.out.println("Task creation test passed");
    }

    private static void testSchedulerAddTask() {
        TaskScheduler scheduler = new TaskScheduler(2);
        Task task1 = new Task(3, 500);
        Task task2 = new Task(1, 500);

        scheduler.addTask(task1);
        scheduler.addTask(task2);

        assert scheduler.getTask(task1.getTaskId()) == task1 : "Task1 should be retrievable";
        assert scheduler.getTask(task2.getTaskId()) == task2 : "Task2 should be retrievable";
        assert scheduler.getPendingTaskCount() >= 0 : "Pending count should be non-negative";

        scheduler.shutdown();
        System.out.println("Scheduler add task test passed");
    }

    private static void testSchedulerExecution() {
        TaskScheduler scheduler = new TaskScheduler(1);
        Task task = new Task(1, 100); // Short execution time

        scheduler.addTask(task);

        // Wait for execution
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Task should be completed or running
        TaskStatus status = task.getStatus();
        assert status == TaskStatus.COMPLETED || status == TaskStatus.RUNNING : "Task should be completed or running";

        scheduler.shutdown();
        System.out.println("Scheduler execution test passed");
    }
}
