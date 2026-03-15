package taskManager;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class TaskScheduler {

  private TaskExecutor taskExecutor;
  private TimeProvider clock;
  private PriorityBlockingQueue<Task> schedulerQueue; //priority queue -> priority blocking queue
  //couldve used delayQueue too (to remove the parkSupport part completely cause it has that inbuilt)
  private Thread schedulerThread;

  public TaskScheduler(TaskExecutor taskExecutor) {
    System.out.println("TaskScheduler.java - TaskScheduler constructed");

    //Queue order => execution time -> creation time -> sequence
    this.schedulerQueue = new PriorityBlockingQueue<Task>(
      11,
      (task1, task2) -> {
        //to avoid collision
        if (task1.getExecuteAt() == task2.getExecuteAt()) {
          if (task1.metadata.createdAt == task2.metadata.createdAt) {
            //TieBreaker
            return Long.compare(
              task1.schedule.sequence,
              task2.schedule.sequence
            );
          }
          return Long.compare(
            task1.metadata.createdAt,
            task2.metadata.createdAt
          );
        }

        return Long.compare(task1.getExecuteAt(), task2.getExecuteAt());
      }
    );
    this.taskExecutor = taskExecutor;
    this.clock = TimeProvider.getTimeProviderInstance();
    System.out.println("Current time is " + clock.now());
    start();
  }

  private void start() {
    this.schedulerThread = new Thread(() -> {
      System.out.println("New Scheduler Thread initiated");
      while (true) {
        try {
          Task task = schedulerQueue.peek();
          if (task == null) {
            System.out.println(
              "Scheduler Thread Parked - No task in the queue"
            );
            LockSupport.park();
            continue;
            //no task in the queue to execute
          }

          System.out.println(
            task.metadata.name +
              task.metadata.taskID +
              " will be executed at " +
              task.getExecuteAt()
          );

          long sleepTime = task.getExecuteAt() - clock.now();

          if (sleepTime > 0) {
            System.out.println("Scheduler thread parked for " + sleepTime);
            LockSupport.parkNanos(sleepTime * 1000000);
            continue;
            //just realised we can use 1_000_000 for 1000000 for better readability
          }
          System.out.println("Moving task from Scheduler to Executor");
          taskExecutor.execute(schedulerQueue.poll());
        } catch (Throwable t) {
          //inturruption happend
          System.out.println("TaskScheduler.java An error occurred");
          System.err.println(t);
          System.out.println("Interrupting the Scheduler Thread");
          Thread.currentThread().interrupt();
          break;
        }
      }
    });
    schedulerThread.start();
  }

  private void wakeThread() {
    System.out.println("Waking up the Scheduler Thread");
    LockSupport.unpark(schedulerThread);
  }

  public void addTask(Task task) {
    //wake the thread up once a new task is getting added
    if (task.getExecuteAt() < TimeProvider.getTimeProviderInstance().now()) {
      System.out.println("Task execution time cannot occur");
      return;
    }
    System.out.println(
      task.metadata.taskID + " needs to be executed at " + task.getExecuteAt()
    );
    schedulerQueue.add(task);
    System.out.println("Task added to the Scheduler Thread");
    wakeThread();
    //adding the task
  }

  public Task peekTask() {
    return schedulerQueue.peek();
    //just spits out the task
  }

  public Task getTask() {
    System.out.println("Task Polling initiated");
    return schedulerQueue.poll();
    //returns the item and deletes it from the queue
    //couldve used take but that would initiate a thread intruption method and we used ParkSupport (easier)
  }
}
