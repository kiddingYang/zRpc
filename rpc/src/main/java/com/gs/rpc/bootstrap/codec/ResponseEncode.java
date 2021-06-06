package com.gs.rpc.bootstrap.codec;

import com.gs.rpc.model.Response;
import com.gs.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseEncode extends MessageToByteEncoder<Response> {

    private Serializer serializer;

    public ResponseEncode(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {

    }
}
