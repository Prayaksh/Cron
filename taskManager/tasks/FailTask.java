package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class FailTask extends Task {

  public FailTask(Long executeAt, Payload payload) {
    super(
      new Metadata("FailTask", "always throws exception"),
      new Schedule(executeAt, null, 0, 3, 10_000),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println(
      Thread.currentThread().getName() + " | Starting AlwaysFailTask"
    );

    throw new RuntimeException(
      "Intentional failure for testing scheduler flow"
    );
  }
}
