import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.*;

public class ReadWriteLockExample {

    private final ReadWriteLock lock=new ReentrantReadWriteLock(true);// if fair lock is not set true then reader starvation may occur as writers may keep acquiring lock due to default non fair nature and writers priority over readers
    private final Lock readLock=lock.readLock();
    private final Lock writeLock=lock.writeLock();

    private int sharedResource=0;

    public void readResource(){
        readLock.lock();
        try{
            System.out.println("Reading resource: " + sharedResource + " by " + Thread.currentThread().getName());
            // Thread.sleep(3000); // simulate read delay
            System.out.println("Finished reading by " + Thread.currentThread().getName());
        }finally {
            readLock.unlock();
        }
    }

    private void writeResource(int value){
        writeLock.lock();
        try{
            System.out.println("Writing resource: " + value + " by " + Thread.currentThread().getName());
            Thread.sleep(500); // simulate write delay
            sharedResource+=1;
            System.out.println("Finished writing by " + Thread.currentThread().getName());
        } catch (InterruptedException e){
            // handle exception
        }finally {
            writeLock.unlock();
        }
    }


    public static void main(String[] args){

        ReadWriteLockExample example=new ReadWriteLockExample();

        Thread writerThread1=new Thread(()->{
            for(int i=0;i<10;i++){
                example.writeResource(i);
            }
            // example.writeResource(42);
        },"Writer-Thread-1");

        Thread readerThread1=new Thread(()->{
            for(int i=0;i<10;i++){
                example.readResource();
            }
            // example.readResource();
        },"Reader-Thread-1");

        Thread readerThread2=new Thread(()->{
            for(int i=0;i<10;i++){
                example.readResource();
            }
        },"Reader-Thread-2");



        writerThread1.start();
        readerThread1.start();
        readerThread2.start();



    }
}