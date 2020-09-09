package com.sunnyday.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

/**
 * 中文乱码
 * InputStream读取的是字节byte，因为一个汉字占两个字节，而当中英文混合的时候，有的字符占一个字节，有的字符占两个字节。
 * 所以如果直接读字节，而数据比较长，没有一次读完的时候，很可能刚好读到一个汉字的前一个字节，这样，这个中文就成了乱码，后面的数据因为没有字节对齐，也都成了乱码。
 * 所以我们需要用Reader来读取，它读到的是字符，所以不会读到半个字符的情况，不会出现乱码。
 *
 * @author TMW
 * @date 2020/9/9 9:12
 */
public class GarbledTest {

    private static File file;

    @BeforeAll
    public static void init() {
        final URL url = GarbledTest.class.getClassLoader().getResource("io/garbledTest.txt");
        assert url != null;
        file = new File(url.getPath());
    }

    /**
     * 字节流
     *
     * @throws IOException
     */
    @Test
    public void inputStreamTest() throws IOException {
        InputStream ips = new FileInputStream(file);

        StringBuilder sb = new StringBuilder();

        byte[] bytes = new byte[1024];
        int n;
        while ((n = ips.read()) != -1) {
            ips.read(bytes, 0, n);
            sb.append(new String(bytes));
        }

        System.out.println(sb.toString());
    }

    /**
     * 缓存字节流
     */
    @Test
    public void bufferedInputStreamTest() throws IOException {
        BufferedInputStream bips = new BufferedInputStream(new FileInputStream(file));
        StringBuilder sb = new StringBuilder();

        byte[] bytes = new byte[1024];
        int n;
        while ((n = bips.read()) != -1) {
            bips.read(bytes, 0, n);
            sb.append(new String(bytes));
        }

        System.out.println(sb.toString());
    }

    /**
     * 字符流
     */
    @Test
    public void readerTest() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
        }
        System.out.println(sb.toString());
    }

}
