package module1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;


public class Second {

    private static volatile boolean isActive = true;

    public static void main(String[] args) {
        Semaphore writingSemaphore = new Semaphore(1);
        Semaphore readingSemaphore = new Semaphore(10);
        List<Thread> pool = new ArrayList<>();

        for(int i=0; i <1000; i++) {
            int val= i;
            Thread writer = new Thread(() -> {
                try {
                    writingSemaphore.acquire();
                    System.out.println("Change state " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writingSemaphore.release();
                }
            });


            Thread reader = new Thread(() -> {
                try {
                    readingSemaphore.acquire();
                    System.out.println("Reading state " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readingSemaphore.release();
                }
            });
            writer.setName("Writer" + val);
            pool.add(writer);
            writer.setName("Reader" + val);
            pool.add(reader);
        }

        pool.forEach(a ->a.start());
        pool.forEach(a -> {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}




        /*Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());
        Thread t3 = new Thread(new Worker());
        Thread t4 = new Thread(new Worker());
        Thread t5 = new Thread(new Worker());
        Thread t6 = new Thread(new Worker());

        t1.setPriority(10);
        t2.setPriority(10);
        t3.setPriority(1);
        t4.setPriority(10);
        t5.setPriority(10);
        t6.setPriority(1);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isActive = false;
    }


    private static class Worker implements Runnable {
        Logger logger = Logger.getAnonymousLogger();
        private Integer counter = 0;

        @Override
        public void run() {
            while (isActive) {
                synchronized (Object.class) {
                    try {
                        doHardWork();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() +" " + counter);
        }

        private void doHardWork() {
            counter++;
        }
    }
}






    /*public int counter = 0;
    static Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        ReadWriteArrayList<Integer> list = new ReadWriteArrayList<>();
        list.add(0);
        Main main = new Main();
        for (int i =0; i < 1000; i++) {
            final int val = i;
            if (val%100 == 0) {
                new Thread(() -> {

                    try {
                        Thread.sleep(ThreadLocalRandom.
                                current().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add(val);
                }).start();

                new Thread(() -> {
                    try {
                        Thread.sleep(ThreadLocalRandom.
                                current().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.get(0);
                }).start();
            }
        }
    }

}


class ReadWriteArrayList<E> {
    private List<E> list = new ArrayList<>();
    private ReadWriteLock rwL = new ReentrantReadWriteLock();

    public void  add(E o) {
        rwL.writeLock().lock();
        try {
            list.add(o);
            System.out.println("El was added by:" + Thread.currentThread().getName());
        } finally {
            rwL.writeLock().unlock();
        }
    }

    public E get(int i) {
        rwL.readLock().lock();
        try {

            System.out.println("El was recieved by: " + Thread.currentThread().getName());
            return list.get(i);
        } finally {
            rwL.readLock().unlock();
        }
    }
}*/