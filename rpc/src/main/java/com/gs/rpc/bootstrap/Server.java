package com.gs.rpc.bootstrap;

import com.gs.register.ServiceRegister;
import com.gs.rpc.model.meta.ServiceWrapper;

public interface Server {

    ServiceRegister serviceRegister();

    void start();

    void shutdown();

    Server publish(ServiceWrapper... ServiceWrapper);

}
