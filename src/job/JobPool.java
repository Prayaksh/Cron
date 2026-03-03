package job;
import java.util.concurrent.*;


public class JobPool {

    private final ExecutorService executor;

    public JobPool(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void submit(Job job) {
        executor.submit(job);
    }

    public void shutdown() {
        executor.shutdown();
    }
}


