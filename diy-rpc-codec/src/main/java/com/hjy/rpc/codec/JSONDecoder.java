package com.hjy.rpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的反序列化
 */
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decoder(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes,clazz);
    }
}
