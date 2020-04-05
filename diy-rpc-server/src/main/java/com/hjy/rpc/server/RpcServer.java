package com.hjy.rpc.server;

import com.hjy.rpc.Request;
import com.hjy.rpc.Response;
import com.hjy.rpc.codec.Decoder;
import com.hjy.rpc.codec.Encoder;
import com.hjy.rpc.common.utils.ReflectionUtils;
import com.hjy.rpc.transport.RequestHandler;
import com.hjy.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private SerivceInvoker serivceInvoker;

    public RpcServer(RpcServerConfig config) {
        this.config = config;

        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);

        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        this.serviceManager = new ServiceManager();
        this.serivceInvoker = new SerivceInvoker();
    }

    public  <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass,bean);
    }

    public void start() {
        this.net.start();
    }
    public void end() {
        this.net.stop();
    }


    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResponse) {
            Response response = new Response();
            try {
                // 1.把所有可用的读出来
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                // 2.反序列化成request
                Request request = decoder.decoder(inBytes,Request.class);
                log.info("get request: {}" , receive);
                // 3.查找服务实例
                ServiceInstance sis = serviceManager.lookup(request);
                // 4.调用实例方法获取返回信息
                Object ret = serivceInvoker.invoke(sis, request);
                response.setData(ret);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                response.setCode(500);
                response.setMessage("rpcServer got error: "
                        + e.getClass().getName()
                        + " : " + e.getMessage());
            } finally {
                try {
                    byte[] outBytes = encoder.encode(response);
                    toResponse.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };

}
