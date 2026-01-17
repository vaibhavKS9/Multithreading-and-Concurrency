import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class BankAccount{
    private double balance=100.0;
    private final Lock lock=new ReentrantLock();
    
    public void withdraw(double amount) throws InterruptedException {
        System.out.println("current thread attempting to withdraw:: " + Thread.currentThread().getName());
        if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
            if(amount <= balance){
                try {
                    Thread.sleep(3000); // simulate delay
                    balance -= amount;
                    System.out.println("Withdrawal of " + amount + " successful. New balance: " + balance+". by thread:: " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Insufficient funds by thread:: " + Thread.currentThread().getName());
            }
        } else {
            System.out.println("Could not acquire lock, withdrawal skipped for thread try later:: " + Thread.currentThread().getName());
        }
    
    }

}

public class JavaLocks{
    public static void main(String[] args){

        BankAccount account = new BankAccount();

        Thread t1 = new Thread(() -> {
            try {
                account.withdraw(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            try {
                account.withdraw(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        // if the process sleep is more than the reentrant try lock wait time then one thread will fail to acquire lock
        // lock.lock() is similar to synchronized block it will wait indefinitely to acquire lock
        System.out.println("Hello, World!");
    }
}