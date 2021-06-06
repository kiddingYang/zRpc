package com.gs.rpc.model;

import lombok.Data;

@Data
public class Request {

    private long requestId;

    private String methodName;

    private Class clazz;

    private Class[] paramTypes;

    private Object[] params;

    private long requestTimeMillis;

    public Request() {

    }

    public Request(long requestId, String methodName, Class clazz, Class[] paramTypes, Object[] params) {
        this.requestId = requestId;
        this.methodName = methodName;
        this.clazz = clazz;
        this.paramTypes = paramTypes;
        this.params = params;
    }
}
