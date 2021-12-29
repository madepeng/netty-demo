package cn.itcast.advance.c2;

/**
 * @author madepeng
 * @title: ByteTest
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/28 10:05
 */
public class ByteTest {
    /**
     * 字节码->ASCII码
     *
     * @param bt
     */
    private static void byteToASCII(int bt) {
        char[] chars = Character.toChars(bt);
        System.out.println("对应的ASCII码为:" + new String(chars));

    }

    /**
     * ASCII码->字节码
     *
     * @param asc
     */
    private static void ascToByte(String asc) {
        int codePointAt = Character.codePointAt(asc, 0);
        System.out.println("对应的字节码为:" + codePointAt);

    }

    public static void main(String[] args) {
        byte a = 0x11;
        byte b = 011;
        String s = Integer.toBinaryString(8);
        System.out.println(a);
        System.out.println(b);
        System.out.println(s);
        final byte[] LINE = {13, 10};
        System.out.println(LINE.toString());
        byteToASCII(13);
        byteToASCII(13);
        byteToASCII(10);
        byteToASCII(10);
        ascToByte("a");

        char f = 'a';
        System.out.println(f);
        System.out.println(f + 1);
        char g = 0x21;
        char m = 655;
        char n = 0x1134;
        System.out.println(g);
        System.out.println(m);
        System.out.println(n);
        System.out.println();
        System.out.println(0x2b);
        System.out.println(0x4f);
        System.out.println(0x4b);
        System.out.println(0x0d);
        System.out.println(0x0a);
    }
}
