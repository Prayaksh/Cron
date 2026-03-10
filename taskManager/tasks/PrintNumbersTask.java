package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class PrintNumbersTask extends Task {

  public PrintNumbersTask(Long executeAt, Payload payload) {
    //Payload - {int count}
    super(
      new Metadata("PrintNumbersTask", "prints numbers slowly"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    int count = Integer.parseInt(payload.args[0]);

    for (int i = 1; i <= count; i++) {
      System.out.println(Thread.currentThread().getName() + " | Number: " + i);

      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
