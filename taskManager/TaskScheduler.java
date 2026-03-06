package taskManager;

import java.util.PriorityQueue;

public class TaskScheduler {

  //   private static TaskScheduler instance;
  private TaskExecutor taskExecutor;
  public PriorityQueue<Task> schedulerQueue;

  private TaskScheduler(TaskExecutor taskExecutor) {
    this.schedulerQueue = new PriorityQueue<>((T1, T2) ->
      Long.compare(T1.getExecuteAt(), T2.getExecuteAt())
    );
    this.taskExecutor = taskExecutor;
    start();
  }

  private void start() {
    Thread schedulerThread = new Thread(() -> {
      while (true) {
        taskExecutor.execute(schedulerQueue.poll());
      }
    });
    schedulerThread.start();
  }

  //   public static synchronized TaskScheduler getTaskSchedulerInstance() {
  //     if (instance == null) {
  //       instance = new TaskScheduler();
  //     }
  //     return instance;
  //   }

  public void addTask(Task task) {
    schedulerQueue.add(task);
    //adding the task
  }

  public Task peekTask() {
    return schedulerQueue.peek();
    //just spits out the task
  }

  public Task getTask() {
    return schedulerQueue.poll();
    //returns the item and deletes it from the queue
  }
}
