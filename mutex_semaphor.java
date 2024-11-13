import java.util.*;
import java.util.concurrent.Semaphore;

class producer_consumer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int BUFFER_SIZE = 5;
    private int item = 0;
    private final Semaphore empty = new Semaphore(BUFFER_SIZE);
    private final Semaphore full = new Semaphore(0);
    private final Object lock = new Object();

    public void produceWithMutex() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.size() == BUFFER_SIZE) {
                buffer.wait();
            }
            item++;
            buffer.add(item);
            System.out.println("Produced(Mutex) : " + item);
            buffer.notifyAll();

        }
        Thread.sleep(1000);
    }

    public void consumeWithMutex() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.isEmpty()) {
                buffer.wait();
            }
            buffer.poll();
            System.out.println("Consumed(Mutex) : " + item);
            buffer.notifyAll();
        }
        Thread.sleep(1000);
    }

    public void produceWithSemaphore() throws InterruptedException {
        empty.acquire();
        synchronized (buffer) {
            item++;
            buffer.add(item);
            System.out.println("Produce(Semaphore) : " + item);
        }
        full.release();
        Thread.sleep(1000);
    }

    public void consumeWithSemaphore() throws InterruptedException {
        full.acquire();
        synchronized (buffer) {
            buffer.remove();
            System.out.println("Consumed(Semaphore) : " + item);

        }
        empty.release();
        Thread.sleep(1000);
    }

}

public class mutex_semaphor {
    public static void main(String[] args) {
        producer_consumer pc = new producer_consumer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice : ");
        System.out.println("1-Mutex\n2-Semaphore\n3-Exit");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                startmudexbased(pc);
                break;
            case 2:
                startSemaphorebased(pc);
                break;
            case 3:
                break;

        }
    }

    private static void startmudexbased(producer_consumer pc) {
        Thread produce = new Thread(() -> {
            try {
                while (true) {
                    pc.produceWithMutex();
                    // Thread.sleep(1000);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });
        Thread consume = new Thread(() -> {
            try {
                while (true) {
                    pc.consumeWithMutex();
                    // Thread.sleep(1000);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });
        produce.start();
        consume.start();

    }

    private static void startSemaphorebased(producer_consumer pc) {
        Thread produce = new Thread(() -> {
            try {
                while (true) {
                    pc.produceWithSemaphore();
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });
        Thread consume = new Thread(() -> {
            try {
                while (true) {
                    pc.consumeWithSemaphore();
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });
        produce.start();
        consume.start();
    }

}
