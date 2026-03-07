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
    this.schedulerQueue = new PriorityBlockingQueue<Task>(11, (task1, task2) ->
      Long.compare(task1.getExecuteAt(), task2.getExecuteAt())
    );
    this.taskExecutor = taskExecutor;
    this.clock = TimeProvider.getTimeProviderInstance();
    start();
  }

  private void start() {
    this.schedulerThread = new Thread(() -> {
      while (true) {
        //todo - what if two tasks have same executeAt?? think
        try {
          Task task = schedulerQueue.peek();
          if (task == null) {
            LockSupport.park();
            continue;
            //no task in the queue to execute
          }

          long sleepTime = task.getExecuteAt() - clock.now();

          if (sleepTime > 0) {
            LockSupport.parkNanos(sleepTime * 1000000);
            continue;
            //just realised we can use 1_000_000 for 1000000 for better readability
          }
          taskExecutor.execute(schedulerQueue.poll());
        } catch (Throwable t) {
          //inturruption happend
          System.err.println(t);
          Thread.currentThread().interrupt();
          break;
        }
      }
    });
    schedulerThread.start();
  }

  private void wakeThread() {
    LockSupport.unpark(schedulerThread);
  }

  public void addTask(Task task) {
    //wake the thread up once a new task is getting added
    schedulerQueue.add(task);
    wakeThread();
    //adding the task
  }

  public Task peekTask() {
    return schedulerQueue.peek();
    //just spits out the task
  }

  public Task getTask() {
    return schedulerQueue.poll();
    //returns the item and deletes it from the queue
    //couldve used take but that would initiate a thread intruption method and we used ParkSupport (easier)
  }
}
