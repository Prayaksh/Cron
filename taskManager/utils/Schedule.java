package taskManager.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Schedule {

  private static final AtomicLong sequencer = new AtomicLong();

  public Long executeAt; // Long execution time
  public String[] isRepeating; //[null if not repeating, Long milis if repeating time interval for repetition]
  public long timeout; //automatically kill the thread if not completed within the timeout bound
  public int retryCount;
  public final long sequence; //atomic tiebreaker
  public long delay;

  public Schedule(
    Long executeAt,
    String[] isRepeating,
    long timeout,
    int retryCount,
    long delay
  ) {
    this.executeAt = executeAt;
    this.isRepeating = isRepeating;
    this.timeout = timeout;
    this.retryCount = retryCount;
    this.sequence = sequencer.getAndIncrement();
    this.delay = delay;
  }
}
