package taskManager;

//here lies all the tasks and there internal code that can be used to perform the work

//A map too if required basically the id and tasks mapped together for easy access

class EmailTask extends Task {

  public EmailTask(
    Long executeAt,
    String[] isRepeating,
    int retryCount,
    Payload payload
  ) {
    super(
      new Metadata("EmailTask", "sends email to the said reciever"),
      new Schedule(executeAt, isRepeating, 60_000_000, retryCount),
      payload
    );
  }

  @Override
  public void execute() {
    // the actual logic goes here
  }
}

//TimerTask, MessageTask
