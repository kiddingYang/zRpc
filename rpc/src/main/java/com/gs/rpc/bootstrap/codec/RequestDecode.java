package com.gs.rpc.bootstrap.codec;

import com.gs.rpc.model.Constants;
import com.gs.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import sun.misc.Request;


public class RequestDecode extends LengthFieldBasedFrameDecoder {

    private Serializer serializer;

    public RequestDecode(int maxFrameLength, Serializer serializer) {
        super(maxFrameLength, 0, Constants.HEADER_FIELD_LENGTH, 0, Constants.HEADER_FIELD_LENGTH);
        this.serializer = serializer;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buffer = (ByteBuf) super.decode(ctx, in);

        if (buffer != null) {
            try {
                return serializer.deserialize(buffer.array(), Request.class);
            } finally {
                buffer.release();
            }
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
