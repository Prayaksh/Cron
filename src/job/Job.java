package job;
import taskManager.Task;

//Job is the thread that would eventually work as a standalone program
public class Job implements Runnable {
    private final Task task;

    public Job(Task task){
        this.task = task;
    }

    @Override 
    public void run() {
        try {
            //the actual execution happens here
            task.execute();
        } catch (Exception e) {
            //todo increase the efficiency here
            System.err.println(e);
        }
    }
}
