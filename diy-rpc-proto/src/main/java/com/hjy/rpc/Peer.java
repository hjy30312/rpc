package com.hjy.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示网络传输的一个端点
 */
@Data
@AllArgsConstructor
public class Peer {
    // 地址
    private String host;
    // 端口
    private int port;
}
