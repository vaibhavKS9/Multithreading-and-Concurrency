import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

// reentrant lock outer inner method example

public class ReentrantLockExample{
    private final Lock lock=new ReentrantLock();

    public void outerMethod(){
        lock.lock();
        try {
            System.out.println("In outer method");
            innerMethod();
        } finally {
            lock.unlock();
        }
    }

    public void innerMethod(){
        lock.lock();
        try {
            System.out.println("In inner method");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        ReentrantLockExample example = new ReentrantLockExample();
        // same thread can acquire the lock multiple times thats reentrant property
        // main thread calling outerMethod which in turn calls innerMethod that requires the same lock
        example.outerMethod();
    }
}