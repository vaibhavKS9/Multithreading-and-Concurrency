import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final Semaphore rwMutex = new Semaphore(1); // controls access to the shared resource
    private static final Semaphore mutex = new Semaphore(1);   // protects readCount
    // readers unlimited access no cap
    private static int readCount = 0;

    private static int sharedResource = 0;

    public static void readResource() throws InterruptedException {
        // entry section
        mutex.acquire();
        readCount++;
        if (readCount == 1) rwMutex.acquire();  // first reader blocks writers
        mutex.release();

        try {
            System.out.println(Thread.currentThread().getName() + " reading " + sharedResource);
        } finally {
            // exit section
            mutex.acquire();
            readCount--;
            if (readCount == 0) rwMutex.release(); // last reader allows writers
            mutex.release();
        }
    }

    public static void updateResource() throws InterruptedException {
        rwMutex.acquire(); // writer needs exclusive access
        try {
            sharedResource++;
            System.out.println(Thread.currentThread().getName() + " wrote " + sharedResource);
        } finally {
            rwMutex.release();
        }
    }

    public static void main(String[] args) {
        Thread[] readers = new Thread[5];
        Thread[] writers = new Thread[2];

        for (int i = 0; i < 2; i++) {
            int id = i;
            writers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    try { updateResource(); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }, "Writer-" + id);
            writers[i].start();
        }

        for (int i = 0; i < 5; i++) {
            int id = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    try { readResource(); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }, "Reader-" + id);
            readers[i].start();
        }
    }
}
