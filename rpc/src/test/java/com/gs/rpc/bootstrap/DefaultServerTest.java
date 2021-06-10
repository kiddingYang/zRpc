package com.gs.rpc.bootstrap;

import com.gs.rpc.model.meta.ServiceWrapper;
import com.gs.rpc.serializer.JdkSerializer;
import org.junit.Test;

public class DefaultServerTest {


    @Test
    public void serviceRegister() {

    }

    @Test
    public void start() {
        DefaultServer defaultServer = new DefaultServer(9999, new JdkSerializer());
        defaultServer.serviceRegister().registerSelf(9999);
        ServiceWrapper serviceWrapper = new ServiceWrapper(HelloService.class,new HelloServiceImpl());
        defaultServer.publish(serviceWrapper);
        defaultServer.start();
    }

    @Test
    public void shutdown() {
    }

    @Test
    public void publish() {
    }
}
