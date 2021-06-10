package com.gs.rpc.bootstrap.handler;

import com.gs.rpc.model.Request;
import com.gs.rpc.provider.ProviderInterceptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ServiceRequestHandler extends SimpleChannelInboundHandler<Request> {

    private ServerInvokerFactory serverInvokerFactory;


    public ServiceRequestHandler(ServerInvokerFactory serverInvokerFactory) {
        this.serverInvokerFactory = serverInvokerFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {



    }
}
