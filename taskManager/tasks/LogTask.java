package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class LogTask extends Task {

  public LogTask(Long executeAt, Payload payload) {
    super(
      new Metadata("LogTask", "prints logs"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    if (payload == null || payload.args == null || payload.args.length == 0) {
      System.out.println(
        Thread.currentThread().getName() + " | LogTask: No messages to print."
      );
      return;
    }

    for (String message : payload.args) {
      System.out.println(
        Thread.currentThread().getName() + " | LogTask: " + message
      );

      try {
        Thread.sleep(100); // simulate some work delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println("LogTask interrupted while printing logs");
      }
    }
  }
}
