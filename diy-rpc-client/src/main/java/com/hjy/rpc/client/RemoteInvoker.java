package com.hjy.rpc.client;

import com.hjy.rpc.Request;
import com.hjy.rpc.Response;
import com.hjy.rpc.ServiceDescriptor;
import com.hjy.rpc.codec.Decoder;
import com.hjy.rpc.codec.Encoder;
import com.hjy.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    RemoteInvoker(Class clazz, Encoder encoder
                ,Decoder decoder,TransportSelector selector) {
        this.clazz = clazz;
        this.decoder = decoder;
        this.encoder = encoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy
            , Method method, Object[] args) throws Throwable {
        log.info("RemoteInvoker invoke()");
        // 1.创建request对象
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        // 2.远程传输去调用远程服务
        Response response = invokeRemote(request);
        if (response == null || response.getCode()!=0) {
            throw new IllegalStateException("fail to invoke remote: " + response);
        }

        // 3.返回
        return response.getData();
    }

    /**
     * 远程方法调用
     * @param request
     * @return
     */
    private Response invokeRemote(Request request) {
        Response response = null;
        TransportClient client = null;

        try {
            client = selector.select();

            byte[] outBytes = encoder.encode(request);
            InputStream revice = client.write(new ByteArrayInputStream(outBytes));

            byte[] inBytes = IOUtils.readFully(revice, revice.available());
            response = decoder.decoder(inBytes,Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            response.setCode(1);
            response.setMessage("RPClient got error: "
                    + e.getClass()
                    + " : " + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return response;
    }
}
