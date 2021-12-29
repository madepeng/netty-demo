package cn.itcast.nio.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流， 2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("/Users/madepeng/hongen/code/study/netty-demo/src/data.txt").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            while (true) {
                int read = channel.read(byteBuffer);
                log.info("read size:{}", read);
                if (read == -1) {
                    log.info("read over");
                    return;
                }
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    log.info("read byte is :{}", (char) b);
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
