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
            log.info("supplyAsync:{}", "test");
            return "test";
        });

        completableFuture.thenApply(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApply:{}", s + "thenapply");
            return s + "thenapply";
        });

        completableFuture.thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApplyAsync:{}", s + "thenapply1");
            return s + "thenapply";
        });

        CompletableFuture<String> stringCompletableFuture2 = completableFuture.thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApplyAsync:{}", s + "thenapply2");
            return s + "thenapply";
        });

        stringCompletableFuture2.thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApplyAsync:{}", s + "stringCompletableFuture2 thenapply");
            return s + "thenapply again";
        });

        stringCompletableFuture2.thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thenApplyAsync:{}", s + "stringCompletableFuture2 thenapply again");
            return s + "thenapply again";
        });

        CompletableFuture<String> stringCompletableFuture = completableFuture.whenComplete((s, throwable) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("whenCompleteAsync:{}", s);
        });
        log.info("completableFuture.getNumberOfDependents():{}", completableFuture.getNumberOfDependents());


        Thread.currentThread().join();
    }
}
