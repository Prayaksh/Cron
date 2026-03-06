package taskManager;

//the initial rule book kinda blueprint for every task
public interface Task {

    String getTaskID();
    long getExecuteAt();
    void  execute() throws Exception;

}