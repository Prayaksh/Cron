package job;

import java.util.UUID;
import taskManager.Task;
import taskManager.TimeProvider;

//Job is the thread that would eventually work as a standalone program
public class Job implements Runnable {

  private final Task task;
  public String jobID;
  public long createdAt;

  public Job(Task task) {
    this.task = task;
    this.jobID =
      task.getMetadata().name +
      "-" +
      UUID.randomUUID().toString().substring(0, 6);
    this.createdAt = TimeProvider.getTimeProviderInstance().now();
  }

  public long getTaskTimeout() {
    return task.getTimeout();
  }

  @Override
  public void run() {
    try {
      //the actual execution happens here
      task.execute();
    } catch (Exception e) {
      //todo increase the efficiency here
      System.out.println("Job.java - Error occurred in the " + jobID);
      System.err.println(e);

      throw e;
    }
  }
}
