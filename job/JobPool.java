package job;

import java.util.concurrent.*;

public class JobPool {

  private final ExecutorService executor;

  public JobPool(int numberOfThreads) {
    System.out.println("JobPool Constructed");
    this.executor = Executors.newFixedThreadPool(numberOfThreads);
  }

  public void submit(Job job) {
    System.out.println("Job submited");
    executor.submit(job);
  }

  public void shutdown() {
    System.out.println("Shutting Down the JobPool");
    executor.shutdown();
  }
}
