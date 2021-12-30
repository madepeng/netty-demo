package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {

       /* ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf);
        System.out.println(buf.getClass());
        System.out.println(buf.maxCapacity());
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        log(buf);
*/
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(4);
        buf1.writeBytes(new byte[]{1, 2, 3, 4});
        buf1.writeInt(5);

        buf1.readByte();
        buf1.readByte();
        buf1.readByte();
        buf1.readByte();
        buf1.markReaderIndex();
        System.out.println(buf1.readInt());
        buf1.writeInt(6);

        System.out.println(buf1.readableBytes());
        System.out.println(buf1.release());
        System.out.println(buf1.readByte());
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf);
    }
}
