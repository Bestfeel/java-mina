package com.gizwits;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by feel on 16/5/15.
 */
public class TestIoBuffer {

    public static void main(String[] args) {


        IoBuffer buf = IoBuffer.allocate(0);
        buf.setAutoExpand(true);

        buf.put("123456".getBytes());

        byte[] bytes = {1, 2, 3, 4, 5, 6};
        buf.put(bytes);


        byte[] array = buf.array();
        for (byte b : array) {
            System.out.println(b);
        }

        System.out.println(array.length);

        System.out.println(new String(array));

        //   end
    }
}
