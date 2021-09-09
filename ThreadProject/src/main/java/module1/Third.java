package module1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Third {
    public static void main(String[] args) {

        Map<Integer, Integer> res = new ConcurrentHashMap<>(10_000);
        List<Thread> threads = new ArrayList<>();
//        res = Collections.synchronizedMap(res);
        Lock lock = new ReentrantLock();
//        ConcurrentHashMap<Integer, Integer> finalRes = new ConcurrentHashMap<>();
        for (int i = 0; i < 10000; i++) {
//            final Map<Integer, Integer> finalRes = res;

            final int val = i;
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                lock.lock();
                res.put(val, val);
//                lock.unlock();
            });
            threads.add(t);
        }

        threads.forEach(Thread :: start);
        threads.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        Iterator<Map.Entry<Integer, Integer>> iterator = res.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getKey());
        }
        System.out.println("Size is " + res.size());
    }


    /*public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10_000);

        Long[] arrFromClientAPI = new Long[10000];
        for (int i = 0; i < 10_000; i++) {
            arrFromClientAPI[i] = Long.valueOf(i);
        }

        for (int i = 0; i < 10000; i++) {
            final int val = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
                arrFromClientAPI[val] = arrFromClientAPI[val] * arrFromClientAPI[val];
            }).start();
        }

        try {
            latch.await();
            long sum = 0;
            for (Long a : arrFromClientAPI) {
                sum += a;
            }
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
