package cn.itcast.protocol;

import cn.itcast.message.LoginRequestMessage;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author madepeng
 * @title: TestMessageCodec
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/28 16:13
 */
public class TestMessageCodec {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(), new MessageCodec());
        channel.writeOneOutbound(new LoginRequestMessage("zhangsan", "123"));
    }
}
