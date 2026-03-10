package taskManager.tasks;

import taskManager.Task;
import taskManager.utils.*;

public class CpuLoadTask extends Task {

  public CpuLoadTask(Long executeAt, Payload payload) {
    //Payload - {int interations}
    super(
      new Metadata("CpuLoadTask", "simulates CPU load"),
      new Schedule(executeAt, null, 0, 0),
      payload
    );
  }

  @Override
  public void execute() {
    System.out.println("Started the task execution");
    int iterations = Integer.parseInt(payload.args[0]);

    long sum = 0;

    for (int i = 0; i < iterations; i++) {
      sum += i * i;
    }

    System.out.println(
      Thread.currentThread().getName() + " | CPU task finished | sum=" + sum
    );
  }
}
