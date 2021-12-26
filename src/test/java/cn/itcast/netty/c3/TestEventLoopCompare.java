package cn.itcast.netty.c3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author madepeng
 * @title: TestEventLoopCompare
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/23 23:24
 */
@Slf4j
public class TestEventLoopCompare {
    public static void main(String[] args) {
//        log.info("main start");
        new Thread(() -> {
//            log.info("test thread");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test");
        }).start();
//        log.info("main end");
        System.out.println("end");
    }
}
