package taskManager;

import taskManager.utils.*;

//the initial rule book kinda blueprint for every task
public abstract class Task {

  protected Metadata metadata;
  protected Schedule schedule;
  protected Payload payload;

  public Task(Metadata metadata, Schedule schedule, Payload payload) {
    System.out.println("Task Constructed");
    this.metadata = metadata;
    this.schedule = schedule;
    this.payload = payload;
  }

  public Long getExecuteAt() {
    return this.schedule.executeAt;
  }

  public Long getSequence() {
    return this.schedule.sequence;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public abstract void execute();
  //thread logic resides here
  //may or maynot use things of payload here
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
