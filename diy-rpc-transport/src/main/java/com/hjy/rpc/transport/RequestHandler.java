package com.hjy.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求的hander
 */
public interface RequestHandler {

    void onRequest(InputStream receive, OutputStream toResponse);

}
