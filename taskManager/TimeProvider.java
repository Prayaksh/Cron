package taskManager;

public class TimeProvider {

  private static TimeProvider timeProviderInstance = new TimeProvider();

  public static TimeProvider getTimeProviderInstance() {
    return timeProviderInstance;
  }

  public static void setTimeProvider(TimeProvider timeProvider) {
    timeProviderInstance = timeProvider;
  }

  public Long now() {
    return System.currentTimeMillis();
  }
}

//for something to actually change the time instance we need a class that extends the timeProvider with overriding feature of Instant now()
