package com.gs.rpc.bootstrap.handler;

import com.gs.rpc.model.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServiceRequestHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {

    }
}
