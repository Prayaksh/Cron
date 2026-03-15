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
  private TaskScheduler taskScheduler;

  public TaskExecutor(JobPool jobPool) {
    System.out.println("TaskExecutor Constructed");
    this.jobPool = jobPool;
  }

  public void setTaskScheduler(TaskScheduler taskScheduler) {
    this.taskScheduler = taskScheduler;
  }

  private void rescheduleTask(Task task) {
    if (task.schedule.retryCount <= 0) {
      System.out.println("Cannot reschedule no more retry ");
      return;
    }
    System.out.println(
      "TaskExecutor.java - Old Execution time was -" + task.schedule.executeAt
    );
    task.schedule.executeAt =
      TimeProvider.getTimeProviderInstance().now() + task.schedule.delay;
    System.out.println(
      "TaskExecutor.java - Reschedule initiated with Execution time -" +
        task.schedule.executeAt
    );
    System.out.println(
      "TaskExecutor.class - rescheduleTask() - task retry count - " +
        task.schedule.retryCount
    );

    if (task.schedule.retryCount > 0) {
      task.schedule.retryCount--;
    }
    taskScheduler.addTask(task);
  }

  public void execute(Task task) {
    System.out.println(
      "TaskExecutor.java - Task execution started in Task Executor"
    );

    try {
      if (task == null) {
        return;
      }

      //convert the task to job
      Job newJob = new Job(task); //todo make it a real job
      System.out.println(
        "TaskExecutor.java - Task converted to job and moved to the JobPool"
      );
      jobPool.submit(newJob); //this will be the flow for the next execution
      System.out.println(
        "TaskExecutor.java - Retry count for the task is" +
          task.schedule.retryCount
      );
    } catch (Exception e) {
      //todo make it efficient
      System.out.println("TaskExecutor.java - An error occurred ");
      System.err.println(e);
      //retry logic goes here

      if (task.schedule.retryCount > 0) {
        System.out.println(
          "TaskExecutor.java - Retry left" + task.schedule.retryCount
        );
        System.out.println("TaskExecutor.java - Retrying...");
        rescheduleTask(task);
      } else {
        System.out.println(
          "TaskExecutor.java - Retry left" + task.schedule.retryCount
        );
        System.out.println(
          "TaskExecutor.java - No more retry left. Task execution failed permanently"
        );
      }
    }
  }
}
