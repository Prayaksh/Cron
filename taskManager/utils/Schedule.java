package taskManager.utils;

public class Schedule {

  public Long executeAt; // Long execution time
  public String[] isRepeating; //[null if not repeating, Long milis if repeating time interval for repetition]
  public long timeout; //automatically kill the thread if not completed within the timeout bound
  public int retryCount;

  public Schedule(
    Long executeAt,
    String[] isRepeating,
    long timeout,
    int retryCount
  ) {
    this.executeAt = executeAt;
    this.isRepeating = isRepeating;
    this.timeout = timeout;
    this.retryCount = retryCount;
  }
}
