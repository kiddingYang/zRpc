package com.gs.rpc.bootstrap;

public class HelloServiceImpl implements HelloService {
    @Override
    public Object sayHello(String msg) {
        return "say hello to " + msg;
    }
}
