package com.gs.register;

public interface ServiceRegister {


    void doRegister(ServiceMeta serviceMeta);


    void unRegister(ServiceMeta serviceMeta);


    void close();

    void registerSelf(int port);

}
