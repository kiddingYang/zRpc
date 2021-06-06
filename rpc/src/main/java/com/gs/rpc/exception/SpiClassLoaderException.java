package com.gs.rpc.exception;


public class SpiClassLoaderException extends RpcException {


    public SpiClassLoaderException() {
    }

    public SpiClassLoaderException(String message) {
        super(message);
    }

    public SpiClassLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpiClassLoaderException(Throwable cause) {
        super(cause);
    }

    public SpiClassLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
