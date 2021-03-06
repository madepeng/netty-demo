package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Server {
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8086));
        log.info("1.serverSocketChannel:{}", serverSocketChannel);

        // 2. 建立 selector 和 channel 的联系（注册）
        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        // 1. 创建 selector, 管理多个 channel
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);


        while (true) {
            log.info("loop count:{}", count.incrementAndGet());
            // 3. select 方法, 没有事件发生，线程阻塞，有事件，线程才会恢复运行
            // select 在事件未处理时，它不会阻塞, 事件发生后要么处理，要么取消，不能置之不理
            int select = selector.select();
            log.info("2.有{}个channel发生了事件", select);

            // 4. 处理事件, selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); // accept, read
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                // 处理key 时，要从 selectedKeys 集合中删除，否则下次处理就会有问题
                iter.remove();
                log.debug("3.SelectionKey: {}", key);
                // 5. 区分事件类型
                if (key.isAcceptable()) { // 如果是 connectedChannel
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) key.channel();
                    SocketChannel connectedChannel = acceptChannel.accept();
                    log.debug("4.连接成功后的事件 SocketChannel：{}", connectedChannel);
                    connectedChannel.configureBlocking(false);
                    connectedChannel.register(selector, SelectionKey.OP_READ, null);
                } else if (key.isReadable()) { // 如果是 read
                    try {
                        SocketChannel connectChannel = (SocketChannel) key.channel(); // 拿到触发事件的channel
                        log.debug("5.事件 SocketChannel：{}，应该和4一样", connectChannel);
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        int read = connectChannel.read(buffer); // 如果是正常断开，read 的方法的返回值是 -1
                        if (read == -1) {
                            key.cancel();
                        } else {
                            buffer.flip();
//                            debugAll(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();  // 因为客户端断开了,因此需要将 key 取消（从 selector 的 keys 集合中真正删除 key）
                    }
                }
            }
        }
    }
}
