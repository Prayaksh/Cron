package taskManager;

import java.util.UUID;

//the initial rule book kinda blueprint for every task
public abstract class Task {

  protected Metadata metadata;
  protected Schedule schedule;
  protected Payload payload;

  public Task(Metadata metadata, Schedule schedule, Payload payload) {
    this.metadata = metadata;
    this.schedule = schedule;
    this.payload = payload;
  }

  public Long getExecuteAt() {
    return this.schedule.executeAt;
  }

  public abstract void execute();
  //thread logic resides here
  //may or maynot use things of payload here
}

class Metadata {

  public final String uniqueID;
  public String uniqueName;
  public String info;
  public Long createdAt;

  public Metadata(String name, String info) {
    this.uniqueID = UUID.randomUUID().toString();
    this.createdAt = System.currentTimeMillis();
    this.uniqueName = name;
    this.info = info;
  }
}

class Schedule {

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

class Payload {

  public String[] args;

  public Payload(String[] args) {
    this.args = args;
  }
}
// A task should store everything that the compiler may need when actually executing that task

// Metadata
//      unique id
//      unique name
//      taskinfo

// Timing related stuff -
//      execution at
//      timeout
//      createdAt

// retry logic -
//      number of retries
//

// recurring logic -
//       repeat After each what interval

// Payload -
//      everything related to the jobs (basically the collection of data)

// execute - the execution code that would eventually use the payload
//*NOTE - we are least bothered about the thing actually getting done and all the logic should be occuring in the same execute function itself
