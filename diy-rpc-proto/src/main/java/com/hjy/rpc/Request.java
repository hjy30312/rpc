package com.hjy.rpc;

import lombok.Data;

/**
 * 表示RPC的一个轻轻
 */
@Data
public class Request {
    private ServiceDescriptor service;
    private Object[] parameters;
}
