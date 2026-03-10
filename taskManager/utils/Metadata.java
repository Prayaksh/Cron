package taskManager.utils;

import java.util.UUID;
import taskManager.TimeProvider;

public class Metadata {

  public final String taskID;
  public String name;
  public String info;
  public Long createdAt;

  public Metadata(String name, String info) {
    this.taskID = UUID.randomUUID().toString();
    this.createdAt = TimeProvider.getTimeProviderInstance().now();
    this.name = name;
    this.info = info;
  }
}
