package job;

import java.util.UUID;
import taskManager.Task;

//Job is the thread that would eventually work as a standalone program
public class Job implements Runnable {

  private final Task task;
  public String jobID;
  private long createdAt;

  public Job(Task task) {
    this.task = task;
    this.jobID = UUID.randomUUID().toString();
    this.createdAt = System.currentTimeMillis();
  }

  @Override
  public void run() {
    try {
      //the actual execution happens here
      task.execute();
    } catch (Exception e) {
      //todo increase the efficiency here
      System.err.println(e);
    }
  }
}
