package cn.itcast.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author madepeng
 * @title: ChannelFutureTest
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/30 15:09
 */
@Slf4j
public class ChannelFutureTest {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8086);

        log.info("{}", channelFuture.channel()); // 1
        channelFuture.sync(); // 2
        log.info("{}", channelFuture.channel()); // 3

        /*ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8086);
        log.info("{}", channelFuture.channel()); // 1
        channelFuture.addListener((ChannelFutureListener) future -> {
            log.info("{}", future.channel()); // 2
        });*/

        ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        closeFuture.addListener(future -> {
            log.debug("处理关闭之后的操作");
        });
        channelFuture.channel().close();
    }
}
