package sample;

import taskManager.TimeProvider;
import taskManager.tasks.*;
import taskManager.utils.Payload;

public class Jobs {

  TimeProvider clock = TimeProvider.getTimeProviderInstance();

  //CPU Load Task
  public CpuLoadTask cpuLoadTask = new CpuLoadTask(
    clock.now() + 10_000,
    new Payload(new String[] { "10" })
  );

  //Delay Task
  public DelayTask delayTask = new DelayTask(
    clock.now() + 11_000,
    new Payload(new String[] { "11_000" })
  );

  //Log Task
  public LogTask logTask = new LogTask(
    clock.now() + 12_000,
    new Payload(
      new String[] { "First Message", "Second Message", "Third Message" }
    )
  );

  // Math Task (addition)
  public MathTask mathTask = new MathTask(
    clock.now() + 13_000,
    new Payload(new String[] { "9999", "1" })
  );

  //Random Wait Task
  public RandomWaitTask randomWaitTask = new RandomWaitTask(
    clock.now() + 14_000,
    new Payload(new String[] { "60_000" })
  );

  //Always Failing Task
  FailTask failTask = new FailTask(
    clock.now() + 10_000,
    new Payload(new String[] { "" })
  );
}
