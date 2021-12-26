package cn.itcast.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author madepeng
 * @title: FutureTest
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/24 15:20
 */
@Slf4j
public class FutureTest {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(1);

        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> queryCode("中国石油"), service);

        // cfQuery成功后继续执行下一个任务:
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> fetchPrice(code), service);

        // cfFetch成功后打印结果:
        cfFetch.thenAcceptAsync((result) -> log.info("price:{}", result), service);

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
//        Thread.sleep(2000);
//        service.shutdown();
    }

    static String queryCode(String name) {
        try {
            log.info("name start:{}", name);
            Thread.sleep(2000);
            log.info("name:{}", name);
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            log.info("code start:{}", code);
            Thread.sleep(1000);
            log.info("code:{}", code);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
