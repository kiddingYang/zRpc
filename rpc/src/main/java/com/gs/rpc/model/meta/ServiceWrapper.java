package com.gs.rpc.model.meta;

import com.gs.rpc.provider.ProviderInterceptor;
import com.gs.rpc.util.ValidUtils;
import lombok.Data;

import java.util.concurrent.Executor;

@Data
public class ServiceWrapper {

    private Class serviceProviderInterfaceClass;

    private final Object serviceProvider;

    private Executor executor;

    private ProviderInterceptor[] providerInterceptors;

    public ServiceWrapper(Class serviceProviderInterfaceClass, Object serviceProvider) {
        this.serviceProviderInterfaceClass = serviceProviderInterfaceClass;
        this.serviceProvider = serviceProvider;
    }

    public ServiceWrapper(Class serviceProviderInterfaceClass, Object serviceProvider, Executor executor, ProviderInterceptor[] providerInterceptors) {
        this.serviceProvider = serviceProvider;
        this.executor = executor;
        this.providerInterceptors = providerInterceptors;
        this.serviceProviderInterfaceClass = serviceProviderInterfaceClass;
        ValidUtils.requiredNotNull(serviceProvider,"service provider");
        ValidUtils.requiredNotNull(serviceProviderInterfaceClass, "provider class");
    }


}
