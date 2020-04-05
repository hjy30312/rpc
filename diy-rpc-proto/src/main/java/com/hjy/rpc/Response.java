package com.hjy.rpc;

import lombok.Data;

/**
 * 表示RPC的返回
 */
@Data
public class Response {
    /**
     * 服务返回编码， 200-成功，非200失败
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 返回的数据
     */
    private Object data;
}
