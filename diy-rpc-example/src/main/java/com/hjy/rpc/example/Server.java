package com.hjy.rpc.example;

import com.hjy.rpc.server.RpcServer;

public class Server {


    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
