package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class MathTask extends Task {

  public MathTask(Long executeAt, Payload payload) {
    //Payload - {int firstInteger, int secondInteger}
    super(
      new Metadata("MathTask", "adds two numbers"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    int a = Integer.parseInt(payload.args[0]);
    int b = Integer.parseInt(payload.args[1]);

    int result = a + b;

    System.out.println(
      Thread.currentThread().getName() + " | " + a + " + " + b + " = " + result
    );
  }
}
