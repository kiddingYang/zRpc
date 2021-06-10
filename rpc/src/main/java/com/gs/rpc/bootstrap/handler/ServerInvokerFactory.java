package com.gs.rpc.bootstrap.handler;

import com.gs.rpc.bootstrap.LookupService;
import com.gs.rpc.model.Request;
import com.gs.rpc.model.Response;
import com.gs.rpc.model.meta.ServiceWrapper;
import com.gs.rpc.provider.ProviderInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerInvokerFactory {

    private LookupService lookupService;


    public ServerInvokerFactory(LookupService lookupService) {
        this.lookupService = lookupService;
    }


    public Response invoke(Request request) {
        Response response = new Response();
        Class clazz = request.getClazz();
        ServiceWrapper serviceWrapper = lookupService.lookupService(clazz.getName());
        beforeInvoker(serviceWrapper, request);
        String methodName = request.getMethodName();
        response.setRequestId(request.getRequestId());
        try {
            Method method = clazz.getMethod(methodName, request.getParamTypes());
            Object result = method.invoke(serviceWrapper.getServiceProvider(), request.getParams());
            response.setResult(result);
            response.setStatusCode(Response.SUCCESS);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            response.setStatusCode(Response.FAILURE);
            response.setResult(e);
        } finally {
            afterInvoker(serviceWrapper, request, response);
        }
        return response;
    }

    private void afterInvoker(ServiceWrapper serviceWrapper, Request request, Response response) {
        ProviderInterceptor[] providerInterceptors = serviceWrapper.getProviderInterceptors();
        if (providerInterceptors != null) {
            for (int i = providerInterceptors.length - 1; i >= 0; i--) {
                ProviderInterceptor providerInterceptor = providerInterceptors[i];
                providerInterceptor.afterInvoke(serviceWrapper.getServiceProvider(), request.getMethodName(), request.getParams(),
                        response, null);
            }
        }
    }

    private void beforeInvoker(ServiceWrapper serviceWrapper, Request request) {
        ProviderInterceptor[] providerInterceptors = serviceWrapper.getProviderInterceptors();
        if (providerInterceptors != null) {
            for (ProviderInterceptor providerInterceptor : providerInterceptors) {
                providerInterceptor.beforeInvoke(serviceWrapper.getServiceProvider(), request.getMethodName(), request.getParams());
            }
        }
    }

}
