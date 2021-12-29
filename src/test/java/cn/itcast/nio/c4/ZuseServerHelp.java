package cn.itcast.nio.c4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author madepeng
 * @title: ZuseServerHelp
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/29 16:49
 */
public class ZuseServerHelp {
    static AtomicInteger i = new AtomicInteger(0);
    static Object o1 = new Object();//可以是任意一个对象，或者自定义的对象

    public static void main(String[] args) throws InterruptedException {
        synchronized (o1)// 主线程获取o1的对象锁
        {
            try {
                while (true) {
                    i.incrementAndGet();
                    System.out.println("Waiting for b to complete..." + i.get());
                    if (i.get() == 100000) {
                        System.out.println("I am wait");
                        o1.wait();//o1的对象锁释放，主线程进入等待状态
                    }
                    System.out.println("Completed.Now back to main thread");
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
