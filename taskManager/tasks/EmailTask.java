package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

//here lies all the tasks and there internal code that can be used to perform the work

//A map too if required basically the id and tasks mapped together for easy access

//Most of the code is written by AI cause tasks are more of a outsider thing rn

//ig i fucked up moved everything to there respective files JAVA SUCKS!!

public class EmailTask extends Task {

  public EmailTask(
    Long executeAt,
    String[] isRepeating,
    int retryCount,
    Payload payload
  ) {
    super(
      new Metadata("EmailTask", "sends email to the said reciever"),
      new Schedule(executeAt, isRepeating, 60_000_000, retryCount),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    // the actual logic goes here
  }
}

//TimerTask, MessageTask
