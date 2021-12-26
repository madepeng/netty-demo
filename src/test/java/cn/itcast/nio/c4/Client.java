package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        System.out.println(sc.connect(new InetSocketAddress("localhost", 8086)));
        System.out.println(sc.write(StandardCharsets.UTF_8.encode("fdfsdfsdfdsfdsfdsfsd")));
        System.in.read();
    }
}