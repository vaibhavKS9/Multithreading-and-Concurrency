import java.util.*;

public class ThreadCommunication {

    // without proper interthread communication the thread would need to keep checking the condition leading to cpu wastage utilization
    // proper way is to use wait and notify mechanisms provided by Object class in java
    // can be used in synchronized(intrinsic lock) methods or blocks only

    private int sharedResource=0;

    public synchronized void produce() throws InterruptedException {
        
        while(sharedResource==10){
            System.out.println("Producer " + Thread.currentThread().getName() + " waiting to produce item as buffer full...");
            wait(); // releases the intrinsic lock and waits until notified
        }
        sharedResource+=1;
        System.out.println("Thread "+Thread.currentThread().getName()+" produced item!");
        System.out.println("sharedResource "+sharedResource);
        notifyAll(); // notify waiting threads that condition may have changed
    }
    public synchronized void consume() throws InterruptedException {
        
        while(sharedResource==0){
            System.out.println("Consumer " + Thread.currentThread().getName() + " waiting for item as buffer empty...");
            wait();
        }
        sharedResource-=1;
        System.out.println("Thread "+Thread.currentThread().getName()+" consumed item!");
        System.out.println("sharedResource "+sharedResource);
        notifyAll();
    }

    public static void main(String[] args) {

        ThreadCommunication pc=new ThreadCommunication();

        Thread producerThread=new Thread(()->{
            try {
                for(int i=0;i<20;i++){
                    pc.produce();
                    Thread.sleep(100); // simulate time taken to produce
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Producer-Thread");

        Thread consumerThread1=new Thread(()->{
            try {
                for(int i=0;i<10;i++){
                    pc.consume();
                    Thread.sleep(100); // simulate time taken to consume
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer-Thread-1");

        Thread consumerThread2=new Thread(()->{
            try {
                for(int i=0;i<10;i++){
                    pc.consume();
                    Thread.sleep(100); // simulate time taken to consume
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer-Thread-2");

        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();

    }
}