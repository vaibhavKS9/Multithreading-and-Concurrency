// import java.util.*;
import java.util.concurrent.*;

public class JavaExecutorFramework {

    public static long calculateFactorial(long n) throws InterruptedException {

        Thread.sleep(3000);
        long ans = 1;
        for (long i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    public static void main(String[] args) throws InterruptedException,ExecutionException {
        System.out.println("This is a placeholder for Java Executor Framework example.");

        // Single thread executor
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        Future<?> singleThreadFuture = singleThreadExecutor.submit(() -> "Task executed in single thread executor");
        while(!singleThreadFuture.isDone()){
            System.out.println("Waiting for single thread executor to complete...");
        }
        System.out.println("Single Thread Executor Successfully done with Result: " + singleThreadFuture.get());
        singleThreadExecutor.shutdown();

        // Thread pool with fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(5);
        long startTime = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            int index=i;
            executor.submit(()->{
                try {
                    long result = calculateFactorial(index);
                    System.out.println("Factorial of " + index + " is " + result);
                } catch (InterruptedException e) {
                    // handle exception
                }
            });
        }
        executor.shutdown();// it waits for previously submitted tasks to finish but does not stops main thread to execute further
        executor.awaitTermination(15, TimeUnit.SECONDS); // waits for termination for given time
        
        long endTime = System.currentTimeMillis();

        System.out.println("Executor Framework Time with multithreading: " + (endTime - startTime) + " ms");


    }
}