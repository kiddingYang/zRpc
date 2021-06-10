package com.gs.rpc.bootstrap;

import com.gs.register.ServiceRegister;
import com.gs.register.impl.Config;
import com.gs.register.impl.ZookeeperRegister;
import com.gs.rpc.bootstrap.codec.RequestDecode;
import com.gs.rpc.bootstrap.codec.ResponseEncode;
import com.gs.rpc.bootstrap.handler.ServerInvokerFactory;
import com.gs.rpc.bootstrap.handler.ServiceRequestHandler;
import com.gs.rpc.exception.RpcException;
import com.gs.rpc.model.Constants;
import com.gs.rpc.model.meta.ServiceWrapper;
import com.gs.rpc.serializer.Serializer;
import com.gs.rpc.util.ValidUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServer implements Server {

    // TODO SPI
    private ServiceRegister serviceRegister;

    private Serializer serializer;

    private Map<String, ServiceWrapper> providers = new ConcurrentHashMap<>();

    private int port;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private volatile Channel channel;

    public DefaultServer(int port, Serializer serializer) {
        serviceRegister = new ZookeeperRegister(Config.ZOOKEEPER_HOST, Config.ZOOKEEPER_PORT,
                Config.SERIVCE_PATH);
        this.port = port;
        this.serializer = serializer;
    }

    @Override
    public ServiceRegister serviceRegister() {
        return serviceRegister;
    }

    @Override
    public void start() {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline()
                                .addLast("request decode", new RequestDecode(Constants.MAX_FRAME_LENGTH, serializer))
                                .addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast("response encode", new ResponseEncode(serializer))
                                .addLast("service request handler", new ServiceRequestHandler(
                                        new ServerInvokerFactory(serviceId -> providers.get(serviceId))));
                    }
                });

        try {
            channel = bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            throw new RpcException(e);
        }

    }

    @Override
    public void shutdown() {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RpcException(e);
        }
    }

    @Override
    public Server publish(ServiceWrapper... serviceWrappers) {
        ValidUtils.requiredNotNull(serviceWrappers, "service wrapper");
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            providers.put(serviceWrapper.getServiceProviderInterfaceClass().getName(), serviceWrapper);
        }
        return this;
    }

}
