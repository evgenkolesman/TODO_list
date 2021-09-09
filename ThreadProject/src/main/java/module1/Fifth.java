package module1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

public class Fifth {
    public static void main(String[] args) {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            try {
                 TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 42;
        }).thenApply(e -> e + 42).thenApply(e -> e - 42).
                thenAccept(System.out  :: println).
                thenRun(() -> System.out.println("It`s final"));

        System.out.println("Doing right job");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/*
ExecutorService service = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        Runnable writeTask = () -> {
            long stamp = lock.writeLock();
            try {
                TimeUnit.SECONDS.sleep(3);
                map.put("1", "First");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(stamp);
            }
        };

        service.submit(writeTask);
        Runnable readTask = () -> {
            long stamp = lock.tryOptimisticRead();
//            long stamp = lock.readLock();
            try {

                if (!lock.validate(stamp)) {
                    System.out.println(lock.validate(stamp));
                    System.out.println(Thread.currentThread().getName() + " sleep");
                    TimeUnit.SECONDS.sleep(1);}
                System.out.println(lock.validate(stamp));
                System.out.println(map.get("1"));

                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
//                lock.unlock(stamp);
                lock.unlockRead(stamp);
            }
        };
        service.submit(readTask);
        service.submit(readTask);

        service.shutdown();
    }
 */