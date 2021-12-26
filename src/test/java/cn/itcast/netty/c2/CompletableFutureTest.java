package cn.itcast.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author madepeng
 * @title: CompletableFutureTest
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/25 14:19
 */
@Slf4j
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("supplyAsync:{}", "test");
            return "test";
        });

        CompletableFuture<String> stringCompletableFuture1 = completableFuture.thenApply(s -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApply:{}", s + "thenapply");
            return s + "thenapply";
        });
        CompletableFuture<String> stringCompletableFuture2 = completableFuture.thenApplyAsync(s -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApplyAsync:{}", s + "thenapply");
            return s + "thenapply";
        });
//        completableFuture.cancel(true);
        CompletableFuture<String> stringCompletableFuture = completableFuture.whenComplete((s, throwable) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("whenCompleteAsync:{}", s);
        });
        log.info("completableFuture.getNumberOfDependents():{}", completableFuture.getNumberOfDependents());
//        log.info("completableFuture:{}", completableFuture.get());

        Thread.currentThread().join();
    }
}
