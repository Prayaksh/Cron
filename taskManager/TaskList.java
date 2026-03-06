package taskManager;

import java.util.UUID;

//here lies all the tasks and there internal code that can be used to perform the work

class EmailTask implements Task {

    public final long executeAt;
    public final String taskId;


    public EmailTask(long executeAt){
        this.taskId = UUID.randomUUID().toString();
        this.executeAt = executeAt;

        //constructor goes here
    }

    public long getExecuteAt(){
        return this.executeAt;
    }

    
    public String getTaskID(){
        return this.taskId;
    }

    @Override
    public void execute() throws Exception {

    }
}

//TimerTask, MessageTask

public class TaskList {

}
