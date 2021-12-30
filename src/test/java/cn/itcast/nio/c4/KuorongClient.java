package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author madepeng
 * @title: KuorongClient
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/29 20:13
 */
public class KuorongClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8086));
        SocketAddress address = sc.getLocalAddress();
        sc.write(Charset.defaultCharset().encode("hello\nworld\n"));
        sc.write(Charset.defaultCharset().encode("0123\n456789abcdef"));
        sc.write(Charset.defaultCharset().encode("0123456789abcdef3333\n"));
        System.in.read();
    }
}
