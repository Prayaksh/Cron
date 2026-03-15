import java.util.concurrent.ThreadLocalRandom;
import job.JobPool;
import taskManager.*;
import taskManager.tasks.*;
import taskManager.utils.*;

public class Main {

  public static void main(String[] args) {
    // the code goes here

    JobPool jobPool = new JobPool(10);
    TaskExecutor taskExecutor = new TaskExecutor(jobPool);
    TaskScheduler taskScheduler = new TaskScheduler(taskExecutor);
    TimeProvider clock = TimeProvider.getTimeProviderInstance();

    taskExecutor.setTaskScheduler(taskScheduler);

    FailTask failTask = new FailTask(
      clock.now() + 10_000,
      new Payload(new String[] { "" })
    );

    taskScheduler.addTask(failTask);
    // Helper helper = new Helper(10, taskScheduler);
    // helper.taskGenerator();
  }
}

class Helper {

  //all methods and variables can be listed here

  public int taskCount;
  public TaskScheduler taskScheduler;

  public void taskGenerator() {
    for (int i = 0; i < taskCount; i++) {
      long randLongTime = ThreadLocalRandom.current().nextLong(0, 60_000);
      System.out.println("Random time of execution is" + randLongTime);
      taskScheduler.addTask(
        new LogTask(
          System.currentTimeMillis() + randLongTime,
          new Payload(
            new String[] {
              "Starting job...",
              "Processing data...",
              "Job finished successfully.",
            }
          )
        )
      );
    }
  }

  public Helper(int taskCount, TaskScheduler taskScheduler) {
    this.taskCount = taskCount;
    this.taskScheduler = taskScheduler;
  }
}
