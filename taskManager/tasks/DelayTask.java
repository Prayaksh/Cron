package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class DelayTask extends Task {

  public DelayTask(Long executeAt, Payload payload) {
    //Payload - {int delay}
    super(
      new Metadata("DelayTask", "simulates long work"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    int delay = Integer.parseInt(payload.args[0]);

    System.out.println(
      Thread.currentThread().getName() + " | Starting delay: " + delay + "ms"
    );

    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(Thread.currentThread().getName() + " | Finished delay");
  }
}
