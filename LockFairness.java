import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class LockFairness{

    private final Lock lock=new ReentrantLock(true);

    public void doSomeWork(){

        lock.lock();
        System.out.println("Lock acquired by "+Thread.currentThread().getName());
        try{
            System.out.println(Thread.currentThread().getName()+" is working...");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+" finished work.");
        }catch (InterruptedException e){

        } finally {
            lock.unlock();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        LockFairness fairLock=new LockFairness();

        Thread t1=new Thread(()->{
            fairLock.doSomeWork();
        },"Thread-1");

        Thread t2=new Thread(()->{
            fairLock.doSomeWork();
        },"Thread-2");

        Thread t3=new Thread(()->{
            fairLock.doSomeWork();
        },"Thread-3");

        t1.start();
        Thread.sleep(100); // slight delay to ensure order
        t2.start();
        Thread.sleep(100);
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}