package com.hjy.rpc.server;

import com.hjy.rpc.Request;
import com.hjy.rpc.common.utils.ReflectionUtils;

public class SerivceInvoker {
    public Object invoke(ServiceInstance service,
                         Request request) {
        return ReflectionUtils.invoke(service.getTarget(),service.getMethod(),
                request.getParameters());
    }


}
