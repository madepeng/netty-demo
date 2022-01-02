package cn.itcast.advance.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Client1 {

    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 10; i++) {
        send();
//        }
        System.out.println("finish");
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<Person>() {

                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
                            log.info("{}", msg);
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            log.info("channelActive send");
                            ctx.writeAndFlush("fdfd");
                            ctx.writeAndFlush(new Person("madepeng"));
                            super.channelActive(ctx);
                        }
                    });
                }
            });
            bootstrap.connect("127.0.0.1", 8086).sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        }
    }
}