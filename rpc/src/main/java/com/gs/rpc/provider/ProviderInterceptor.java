package com.gs.rpc.provider;

public interface ProviderInterceptor {

    void beforeInvoke(Object provider, String methodName, Object[] args);

    void afterInvoke(Object provider,String methodName, Object[] args, Object result, Throwable failCause);

}
