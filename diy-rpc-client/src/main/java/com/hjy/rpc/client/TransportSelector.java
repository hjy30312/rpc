package com.hjy.rpc.client;

import com.hjy.rpc.Peer;
import com.hjy.rpc.transport.TransportClient;

import java.util.List;

/**
 * 表示现在哪个server去连接
 */
public interface TransportSelector {

    /**
     * 初始化selector
     * @param peers 可以连接的server端点信息
     * @param count client与server建立多少个连接
     * @param clazz client实现class
     */
    void init(List<Peer> peers, int count
            , Class<? extends TransportClient> clazz);

    /**
     * 现在一个transport与server做交互
     * @return
     */
    TransportClient select();

    /**
     * 释放用完的client
     * @param client
     */
    void release(TransportClient client);

    void close();
}
