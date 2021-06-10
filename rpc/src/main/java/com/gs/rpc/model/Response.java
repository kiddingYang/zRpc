package com.gs.rpc.model;

import lombok.Data;

@Data
public class Response {


    public static final byte SUCCESS = 1;
    public static final byte FAILURE = 0;

    private long requestId;

    private byte statusCode;

    private Object result;



}
