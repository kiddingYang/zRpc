package com.gs.rpc.register;

public interface ServiceRegister {


    void doRegister(ServiceMeta serviceMeta);


    void unRegister(ServiceMeta serviceMeta);


    void close();

}
