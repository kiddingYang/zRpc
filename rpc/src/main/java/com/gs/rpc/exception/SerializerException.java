package com.gs.rpc.exception;

public class SerializerException extends RpcException {

    public SerializerException() {
    }

    public SerializerException(String message) {
        super(message);
    }

    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(Throwable cause) {
        super(cause);
    }

    public SerializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
