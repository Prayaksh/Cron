package job;

import java.util.concurrent.*;

public class JobPool {

  private final ExecutorService executor;

  public JobPool(int numberOfThreads) {
    System.out.println("JobPool.java - JobPool Constructed");
    this.executor = Executors.newFixedThreadPool(numberOfThreads);
  }

  public void submit(Job job) throws Exception {
    System.out.println("JobPool.java - Executing job " + job.jobID);

    Future<?> future = executor.submit(job);
    try {
      System.out.println(future.get());
    } catch (Exception e) {
      System.out.println(
        "JobPool.java - An error occurred while executing the task" + e
      );
      throw e;
    }
  }

  public void shutdown() {
    System.out.println("JobPool.java - Shutting Down the JobPool");
    executor.shutdown();
  }
}
