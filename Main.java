import job.JobPool;
import taskManager.*;
import taskManager.tasks.*;
import taskManager.utils.*;

public class Main {

  public static void main(String[] args) {
    // the code goes here

    JobPool jobPool = new JobPool(5);
    TaskExecutor taskExecutor = new TaskExecutor(jobPool);
    TaskScheduler taskScheduler = new TaskScheduler(taskExecutor);

    LogTask logTask = new LogTask(
      System.currentTimeMillis() + 10_000,
      new Payload(
        new String[] {
          "Starting job...",
          "Processing data...",
          "Job finished successfully.",
        }
      )
    );

    taskScheduler.addTask(logTask);
  }
}

class helper {
  //all methods and variables can be listed here
}
