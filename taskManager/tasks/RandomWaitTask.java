package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class RandomWaitTask extends Task {

  public RandomWaitTask(Long executeAt, Payload payload) {
    //Payload - {int maxDelay}
    super(
      new Metadata("RandomWaitTask", "waits random time"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    int maxDelay = Integer.parseInt(payload.args[0]);
    int delay = (int) (Math.random() * maxDelay);

    System.out.println(
      Thread.currentThread().getName() + " | Random wait: " + delay + "ms"
    );

    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(
      Thread.currentThread().getName() + " | Finished random wait"
    );
  }
}
