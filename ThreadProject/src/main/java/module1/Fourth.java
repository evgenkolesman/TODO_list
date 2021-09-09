package module1;

import com.sun.tools.javac.Main;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

enum JAR {BLACKBERRY, MUSHROOMS, CUCUMBERS}

public class Fourth {
    static Logger logger = Logger.getLogger(Fourth.class);
//volatile int a;
//    public Integer counter = 0;
//    public AtomicInteger atomicInteger = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Fourth main = new Fourth();

        String inVal = "1";
        AtomicReference reference = new AtomicReference(inVal);
        String inVal1 = "New val";

        boolean result = reference.compareAndSet(inVal, inVal1);
        System.out.println(result);
        boolean result1 = reference.compareAndSet(inVal, inVal1);
        System.out.println(result1);
        System.out.println(reference);



        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1_000; i++) {
            final int val = i;

            service.execute(() -> {
//                synchronized (main) {
//                    main.counter++;
//                }
//                main.atomicInteger.getAndIncrement();
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
                    reference.compareAndSet(inVal1, Integer.valueOf(val));
                } catch (InterruptedException e) {
                    logger.error("Error", e);
                }

            });
        }
        service.shutdown();
        TimeUnit.SECONDS.sleep(10);
//        System.out.println(main.counter);
//        System.out.println(main.atomicInteger.getAndIncrement());
        System.out.println(reference.get());
    }
}








   /*    List<Future<Long>> results = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        long sum = 0;

        for (int i = 0; i < 10_000; i++) {
            final int j = i;

            Callable<Long> task = () -> {

                long result = j * j;
                System.out.println(Thread.currentThread().getName());
                return result;
            };
            Future<Long> result = service.submit(task);
            results.add(result);
        }

        for (Future<Long>result : results) {
            sum+= result.get();
        }
        System.out.println(sum);
        System.out.println(((ThreadPoolExecutor)service).getLargestPoolSize());
        service.shutdown();
    }





    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newWorkStealingPool(2);
        List<Callable<String>> tasks = Arrays.asList(
                () -> "1",
                () -> "2",
                () -> "42"
        );
        service.invokeAll(tasks).stream().map(future -> {
            String a = "";
            try {
                a =  future.get();
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
            return a;
        }).forEach(System.out::println);
    }
}








   public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<JAR> jars = new CopyOnWriteArrayList<>();

        try {
            ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2);
            ScheduledFuture future = service.scheduleAtFixedRate(() -> {

                JAR jar = JAR.values()[ThreadLocalRandom.current().nextInt(2)];
                System.out.println("Send mail box " + jar.name());
                jars.add(jar);
            }, 0, 1, TimeUnit.SECONDS);

//            System.out.println(future.get());
             service.awaitTermination(6, TimeUnit.SECONDS);
//            future.cancel(true);

            //service.wait();
            service.shutdown();
        } finally {
            Thread.currentThread().interrupt();
        }

        System.out.println(jars.size());
    }*/


//    public static void main(String[] args) {
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        Runnable task = () -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(5000);
//            } catch (InterruptedException e) {
//                logger.error("Exception is ", e);
//            }
//            String threadName = Thread.currentThread().getName();
//            System.out.println(threadName + " is doing");
//        };
//
//        service.submit(task);
//        service.submit(task);
//        service.submit(task);
//        System.out.println("Start down of thread pool");
//        try {
//            service.shutdown();
//            service.awaitTermination(6, TimeUnit.SECONDS);
//            System.out.println("shutdown");
//        } catch (InterruptedException e) {
//            logger.error("Exception is ", e);
//        } finally {
//            if (service.isTerminated()) {
//            service.shutdownNow();
//            System.out.println("shutdownNow");
//            }
//        }
//    }

