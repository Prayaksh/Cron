package taskManager;

import job.Job;
import job.JobPool;

//Main jobs of taskExecutor
//1. get tasks from the schedulerQueue
//2. make it a job
//3. send it to jobPool.submit
//4. wait for it to succeed or fail
//      if success - done
//      if failure - retry logic

public class TaskExecutor {

  private final JobPool jobPool;

  public TaskExecutor(JobPool jobPool) {
    System.out.println("TaskExecutor Constructed");
    this.jobPool = jobPool;
  }

  public void execute(Task task) {
    System.out.println("Task execution started in TaskExecutor");
    try {
      if (task == null) {
        return;
      }
      //convert the task to job
      Job newJob = new Job(task); //todo make it a real job
      jobPool.submit(newJob); //this will be the flow for the next execution
      System.out.println("Task converted to job and moved to the JobPool");
    } catch (Exception e) {
      //todo make it efficient
      System.err.println(e);
    }
  }
}
