package com.hjy.rpc.server;


import com.hjy.rpc.codec.Decoder;
import com.hjy.rpc.codec.Encoder;
import com.hjy.rpc.codec.JSONDecoder;
import com.hjy.rpc.codec.JSONEncoder;
import com.hjy.rpc.transport.HTTPTransportServer;
import com.hjy.rpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass
            = HTTPTransportServer.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 3000;

}
