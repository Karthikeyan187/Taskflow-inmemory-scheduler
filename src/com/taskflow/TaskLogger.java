package com.taskflow;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Simple logger for TaskFlow.
 * Uses java.util.logging for console-based logging.
 * Logs task lifecycle events.
 */
public class TaskLogger {
    private static final Logger logger = Logger.getLogger("TaskFlow");

    static {
        // Configure logger
        logger.setLevel(Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false); // Disable default console handler
    }

    /**
     * Logs a task event.
     * @param event The event type (e.g., STARTED, COMPLETED)
     * @param task The task involved
     */
    public static void logTaskEvent(String event, Task task) {
        logger.info(String.format("Task %d %s", task.getTaskId(), event));
    }

    /**
     * Logs a general message.
     * @param message The message to log
     */
    public static void log(String message) {
        logger.info(message);
    }
}
