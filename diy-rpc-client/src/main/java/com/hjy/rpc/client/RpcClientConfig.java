package com.hjy.rpc.client;

import com.hjy.rpc.Peer;
import com.hjy.rpc.codec.Decoder;
import com.hjy.rpc.codec.Encoder;
import com.hjy.rpc.codec.JSONDecoder;
import com.hjy.rpc.codec.JSONEncoder;
import com.hjy.rpc.transport.HTTPTransportClient;
import com.hjy.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass
            = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass
            = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 3000));

}
