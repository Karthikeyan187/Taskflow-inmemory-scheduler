# TaskFlow: In-Memory Task Scheduler

> High-performance Java-based task scheduler demonstrating multithreading, priority scheduling, and system design.

A high-performance, production-ready Java-based task scheduler that simulates a low-latency, mission-critical scheduling system. Built with Core Java principles, featuring priority-based scheduling, multithreading, and thread-safe operations.

## ⚡ Performance at a Glance

| Operation | Time Complexity | Data Structure |
|-----------|-----------------|----------------|
| Add Task | O(log n) | PriorityQueue |
| Get Task | O(1) | ConcurrentHashMap |
| Execute Task | O(1) | Thread Pool |

## 🏗️ System Architecture

```
┌─────────┐    ┌─────────────┐    ┌─────────┐    ┌─────────┐
│  Task   │───▶│  Scheduler  │───▶│ Worker  │───▶│ Logger  │
│         │    │             │    │         │    │         │
│ • ID    │    │ • PriorityQ │    │ • Exec  │    │ • Event │
│ • Priority │ │ • HashMap   │    │ • Status│    │ • Console│
│ • Status │   │ • ThreadPool│    │         │    │         │
└─────────┘    └─────────────┘    └─────────┘    └─────────┘
```

## 🚀 Features

- **Priority-Based Scheduling**: Tasks are scheduled based on priority using a PriorityQueue (higher priority first)
- **Fast Task Lookup**: O(1) task retrieval using ConcurrentHashMap
- **Multithreaded Execution**: Configurable thread pool with ExecutorService for concurrent task processing
- **Thread Safety**: Synchronized operations ensure data integrity in concurrent environments
- **Lifecycle Management**: Complete task status tracking (PENDING → RUNNING → COMPLETED/FAILED)
- **Console Logging**: Built-in logging for task events and system monitoring
- **Graceful Shutdown**: Clean termination with proper resource cleanup
- **Error Handling**: Robust failure management with automatic task status updates
- **Unit Testing**: Comprehensive tests without external testing frameworks

## 🏗️ Architecture

### Core Components

#### Task
Represents a schedulable unit of work with the following attributes:
- `taskId`: Unique identifier (auto-generated)
- `priority`: Integer priority (higher values = higher priority)
- `executionTime`: Simulated execution time in milliseconds
- `status`: Current lifecycle state (PENDING, RUNNING, COMPLETED, FAILED)

#### TaskScheduler
The central scheduling component featuring:
- **PriorityQueue<Task>**: For O(log n) priority-based task insertion
- **ConcurrentHashMap<Long, Task>**: For O(1) task lookup by ID
- **ExecutorService**: Fixed thread pool for concurrent execution
- Thread-safe operations using synchronization

#### Worker
Runnable implementation that executes tasks with:
- Status updates during execution
- Exception handling with failure marking
- Event logging for monitoring

#### TaskLogger
Console-based logging utility using `java.util.logging`:
- Task lifecycle event tracking
- Configurable log levels
- Clean, readable output format

### Design Patterns
- **Object-Oriented Design**: Clear separation of concerns with dedicated classes
- **Singleton Pattern**: Logger instance management
- **Runnable Pattern**: Worker thread execution
- **Factory Pattern**: Task creation with auto-generated IDs

## ⏱️ Time Complexity

| Operation | Time Complexity | Data Structure |
|-----------|-----------------|----------------|
| Add Task | O(log n) | PriorityQueue insertion |
| Get Task | O(1) | HashMap lookup |
| Execute Task | O(1) | Thread pool submission |
| Priority Sort | O(log n) | Heap operations |

## 📋 Requirements

- **Java**: JDK 8 or higher
- **No External Dependencies**: Pure Core Java implementation

## 🛠️ Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Karthikeyan187/Taskflow-inmemory-scheduler.git
   cd Taskflow-inmemory-scheduler
   ```

2. **Compile the project**:
   ```bash
   javac -d . src/com/taskflow/*.java
   ```

3. **Run the demonstration**:
   ```bash
   java com.taskflow.Main
   ```

4. **Run unit tests**:
   ```bash
   java com.taskflow.TaskSchedulerTest
   ```

## 💡 Usage Example

```java
// Create scheduler with 4 worker threads
TaskScheduler scheduler = new TaskScheduler(4);

// Create and submit tasks with different priorities
Task highPriorityTask = new Task(10, 2000);    // Priority 10, 2 seconds
Task mediumPriorityTask = new Task(5, 1000);   // Priority 5, 1 second
Task lowPriorityTask = new Task(1, 500);       // Priority 1, 0.5 seconds

scheduler.addTask(highPriorityTask);
scheduler.addTask(mediumPriorityTask);
scheduler.addTask(lowPriorityTask);

// Retrieve task status
Task task = scheduler.getTask(highPriorityTask.getTaskId());
System.out.println("Task Status: " + task.getStatus());

// Graceful shutdown
scheduler.shutdown();
```

### Sample Output
```
TaskScheduler initialized with 4 threads
Task 1 ADDED
Task 2 ADDED
Task 3 ADDED
Task 1 STARTED
Task 1 COMPLETED
Task 2 STARTED
Task 2 COMPLETED
Task 3 STARTED
Task 3 COMPLETED
TaskScheduler shut down
```

## 🧪 Testing

The project includes comprehensive unit tests in `TaskSchedulerTest.java`:

- **Task Creation Test**: Validates task initialization and ID generation
- **Scheduler Add Test**: Verifies task addition and retrieval
- **Execution Test**: Confirms proper task execution and status updates

Run tests:
```bash
java com.taskflow.TaskSchedulerTest
```

Expected output:
```
Task creation test passed
Scheduler add task test passed
Scheduler execution test passed
All tests passed!
```

## 🔧 Configuration

### Thread Pool Size
Adjust the number of worker threads by modifying the constructor parameter:
```java
TaskScheduler scheduler = new TaskScheduler(desiredThreadCount);
```

### Log Level
Modify logging level in `TaskLogger.java`:
```java
logger.setLevel(Level.INFO); // Change to DEBUG, WARNING, etc.
```

## 🚀 Future Improvements

- **Persistent Storage**: Add database integration for task persistence
- **REST API**: Expose scheduler functionality via HTTP endpoints
- **Metrics & Monitoring**: Integrate with monitoring tools (Prometheus, Grafana)
- **Configuration Management**: External configuration files for thread pools and timeouts
- **Task Dependencies**: Support for task dependency chains
- **Retry Mechanism**: Automatic retry for failed tasks with exponential backoff
- **Load Balancing**: Distribute tasks across multiple scheduler instances
- **GUI Dashboard**: Web-based monitoring interface

## 📊 Performance Characteristics

- **Memory Efficient**: In-memory storage with minimal overhead
- **CPU Optimized**: Concurrent execution maximizes throughput
- **Scalable**: Thread pool size can be tuned for workload requirements
- **Low Latency**: PriorityQueue ensures high-priority tasks execute immediately

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Karthikeyan K**
- GitHub: [@Karthikeyan187](https://github.com/Karthikeyan187)

## 🙏 Acknowledgments

- Built as a demonstration of Core Java proficiency
- Designed for production use cases requiring reliable task scheduling
- Suitable for mission-critical systems with high concurrency requirements

