package com.hjy.rpc.example;

import com.hjy.rpc.client.RpcClient;

public class Client {

    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService service = client.getProxy(CalcService.class);

        // 实际为调用RemoteInvoker.invoke()
        int r1 = service.add(1,2);
        int r2 = service.minus(10,2);

        System.out.println(r1);
        System.out.println(r2);
    }

}
