import java.util.*;

// Problems prior to Java Executor Framework
// 1. Manual thread management
// 2. Resource exhaustion
// 3. Scalability issues
// 4. Thread Reuse
// 5. Error handling



public class JavaWithoutExecutorFramework {

    static long calculateFactorial(long n) throws InterruptedException {
        if (n <= 1) return 1;
        Thread.sleep(100);
        long ans=1;
        for (long i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans; 
    }

    static void manualThreadManagement() throws InterruptedException {
        // Creating and managing threads manually
        long startTime= System.currentTimeMillis();
        Thread[] threads= new Thread[10];
        for (int i = 0; i < 10; i++) {

            int ind=i;
            threads[i] = new Thread(() -> {
                try {
                    long result = calculateFactorial(ind);
                    System.out.println("Factorial of " + ind + " is " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            });
            threads[i].start();
        }
        for(Thread thread : threads){
            thread.join();
        }
        // without multithreading
        // for(int i=0;i<20;i++){
        //     System.out.println("Factorial of "+i+" is "+calculateFactorial(i));
        // }
        long endTime= System.currentTimeMillis();
        System.out.println("Manual Thread Management Time with multithreading: " + (endTime - startTime) + " ms");
    }


    public static void main(String[] args) throws InterruptedException {
        manualThreadManagement();

    }
}
