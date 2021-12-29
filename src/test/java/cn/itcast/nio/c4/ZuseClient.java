package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author madepeng
 * @title: ZuseClient
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/29 16:09
 */
public class ZuseClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8086));
        sc.write(StandardCharsets.UTF_8.encode("hello world"));
        System.out.println("waiting...");
        System.in.read();
    }
}
