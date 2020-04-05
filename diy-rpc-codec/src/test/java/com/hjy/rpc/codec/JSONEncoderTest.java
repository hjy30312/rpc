package com.hjy.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();

        TestBean bean = new TestBean();
        bean.setName("hjy");
        bean.setAge(12);

        byte[] bytes = encoder.encode(bean);
        assertNotNull(bytes);
    }
}
