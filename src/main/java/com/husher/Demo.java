package com.husher;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 776216
 */
public class Demo {
    private final ArrayList<Integer> arrayList = new ArrayList<Integer>();
    public static void main(String[] args)  {
        final Demo test = new Demo();

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 100; i++) {

            singleThreadPool.execute(()-> {
                test.insert(Thread.currentThread());
                count ++;
            });
        }
        singleThreadPool.shutdown();
    }
    public static int count = 0;
    public void insert(Thread thread) {
        //注意这个地方
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            String locked = "得到了锁";
            System.out.println(thread.getName()+ locked + "  " + count);
            TimeUnit.SECONDS.sleep(1);
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            lock.unlock();
            String unlocked = "释放了锁";
            System.out.println(thread.getName()+unlocked + "  " + count);
        }
    }
}
