package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WriteServer {
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8086));

        while (true) {
            log.info("count:{}", count.incrementAndGet());
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, SelectionKey.OP_READ, null);

                    // 1. 向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 500000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    // 2. 返回值代表实际写入的字节数
                    int write = sc.write(buffer);
                    log.info("isAcceptable write:{}", write);

                    // 3. 判断是否有剩余内容
                    if (buffer.hasRemaining()) {
                        // 4. 关注可写事件   1                     4
                        sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
//                        sckey.interestOps(sckey.interestOps() | SelectionKey.OP_WRITE);
                        // 5. 把未写完的数据挂到 sckey 上
                        sckey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    log.info("isWritable sc:{}", sc);
                    int write = sc.write(buffer);
                    log.info("isWritable write:{}", write);
                    // 6. 清理操作
                    if (!buffer.hasRemaining()) {
                        key.attach(null); // 需要清除buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);//不需关注可写事件
                    }
                }
            }
        }
    }
}
