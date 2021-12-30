package cn.itcast.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 准备 EventLoop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 2. 可以主动创建 promise, 结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        // 设置回调，异步接收结果
        promise.addListener(future -> {
            // 这里的 future 就是上面的 promise
            log.debug("now结果是: {}", promise.getNow());
        });

        eventLoop.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("set success, {}", 10);
            promise.setSuccess(10);
        });
        /*
        new Thread(() -> {
            // 3. 任意一个线程执行计算，计算完毕后向 promise 填充结果
            log.debug("开始计算...");
            try {
//                int i = 1 / 0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();*/
        // 4. 接收结果的线程
        log.debug("等待结果...");
//        log.debug("结果是: {}", promise.get());
    }

}
