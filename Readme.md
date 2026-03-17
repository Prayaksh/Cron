# Java Multi-Threaded Job Scheduler

![Java](https://img.shields.io/badge/Java-17-pink?logo=java&logoColor=white)
![Cron](https://img.shields.io/badge/Clone-Cron-yellow?logo=cron&logoColor=blue)
![Executor Service](https://img.shields.io/badge/Service-Executor-red)
![JobPool](https://img.shields.io/badge/JobPool-red)
![Priority Blocking Queue](https://img.shields.io/badge/PriorityBlockingQueue-red)
![Locks](https://img.shields.io/badge/Locks-red)

A **multi-threaded job scheduling system written in plain Java** that allows tasks to be scheduled for execution at a specific time and processed concurrently by a pool of worker threads.

This project focuses on **concurrency design, thread coordination, and task scheduling architecture**, implementing a lightweight scheduler conceptually similar to a cron system.

## Unlike cron, this system is designed from scratch using **Java concurrency primitives and custom scheduling logic**.

# Overview

The scheduler allows users to submit tasks with the following parameters:

- **Execution Time** – when the task should run
- **Retry Count** – how many times a failed task should be retried
- **Retry Delay** – delay between retries
- **Timeout** – maximum execution time
- **Payload** – arbitrary data passed to the task

Tasks are submitted to a **scheduler thread**, stored in a **priority queue**, and executed by a **pool of worker threads** once their execution time arrives.

The system emphasizes:

- **Thread-safe task scheduling**
- **Concurrent execution**
- **Failure handling and retry logic**
- **Decoupled scheduler and executor architecture**

---

# Key Features

### Multi-threaded Execution

Tasks are executed concurrently using a **worker thread pool** (`JobPool`).

### Priority-based Scheduling

Tasks are stored in a **PriorityBlockingQueue** ordered by execution timestamp.

### Retry Mechanism

Tasks that fail are retried automatically based on:

- configured retry count
- retry delay

### Task Metadata and Payload Support

Tasks can include metadata and arbitrary payload data for flexible task execution.

### Separation of Responsibilities

The system separates:

- **Task submission**
- **Scheduling**
- **Execution**
- **Job management**

This improves maintainability and system extensibility.

---

# Architecture

```
        User
         │
         ▼
   TaskScheduler
   (Scheduler Thread)
         │
         ▼
 PriorityBlockingQueue
   (Scheduled Tasks)
         │
   when execution time arrives
         │
         ▼
     TaskExecutor
         │
         ▼
        Job
         │
         ▼
      JobPool
 (Worker Thread Pool)
         │
         ▼
   Runnable.run()
 (Actual Task Execution)
```

---

# System Components

## Task

A **Task** represents the user-defined unit of work.

Each task defines its execution logic by implementing `execute()`.

Internally the task is wrapped into a `Runnable` job for execution.

Example:

```java
public class FailTask extends Task {

  public FailTask(Long executeAt, Payload payload) {
    super(
      new Metadata("FailTask", "always throws exception"),
      new Schedule(executeAt, null, 100_000, 3, 10_000),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println(
      Thread.currentThread().getName() + " | Starting AlwaysFailTask"
    );

    throw new RuntimeException(
      "Intentional failure for testing scheduler flow"
    );
  }
}
```

---

## TaskScheduler

Responsible for:

- accepting tasks from the user
- storing them in the **PriorityBlockingQueue**
- monitoring execution time
- forwarding ready tasks to the executor

Runs on its own **dedicated scheduler thread**.

---

## TaskExecutor

Handles:

- converting **Tasks → Jobs**
- sending jobs to the worker pool

This layer separates **scheduling logic from execution logic**.

---

## Job

A **Job** is the executable wrapper around a task.

Jobs implement `Runnable` and are submitted to the worker pool.

---

## JobPool

The **JobPool** acts as a **worker thread pool** that executes jobs concurrently.

Multiple jobs can run simultaneously depending on the pool configuration.

---

## Utility Components

### Metadata

Stores identifying information about a task.

Example:

```
Task Name
Description
```

### Schedule

Defines the scheduling configuration:

- execution time
- timeout
- retry count
- retry delay

### Payload

Contains data required by the task during execution.

---

# Task Lifecycle

```
User creates Task
        │
        ▼
Task submitted to Scheduler
        │
        ▼
Stored in PriorityBlockingQueue
        │
Execution time reached
        │
        ▼
TaskExecutor converts Task → Job
        │
        ▼
JobPool executes Runnable
        │
        ▼
Task.run() → execute()
        │
   Success / Failure
        │
        ├── Success → Completed
        └── Failure → Retry (if retries left)
```

---

# Project Structure

```
src/
│
├── job/
│   ├── Job.java
│   └── JobPool.java
│
├── stateManager/
│   └── JobState.java
│
├── taskManager/
│   ├── tasks/
│   │   └── (user defined tasks)
│   │
│   ├── utils/
│   │   ├── Metadata.java
│   │   ├── Schedule.java
│   │   └── Payload.java
│   │
│   ├── Task.java
│   ├── TaskExecutor.java
│   ├── TaskScheduler.java
│   └── TimeProvider.java
│
└── Main.java
```

---

# Example Usage

Creating and scheduling a task:

```java
FailTask failTask = new FailTask(
    clock.now() + 10_000,
    new Payload(new String[] { "" })
);
```

This task will:

- run **10 seconds later**
- intentionally fail
- retry according to its schedule configuration

---

# Running the Project

Compile and run using standard Java.

```
javac src/**/*.java
java Main
```

Or run directly from your IDE.

Requires:

```
Java 20+
```

---

# Future Improvements

Possible extensions:

- Persistent job storage
- Cron-style scheduling expressions
- Job state tracking
- REST API for task submission
- Dynamic thread pool scaling
- Monitoring dashboard
- Dead-letter queue for permanently failed jobs

---

# Learning Goals

This project was built to explore:

- **Java concurrency primitives**
- **thread coordination**
- **priority-based scheduling**
- **worker pool design**
- **fault tolerance and retry systems**
- **modular scheduler architecture**

---
