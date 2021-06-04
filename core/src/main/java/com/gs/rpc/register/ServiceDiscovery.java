package com.gs.rpc.register;

import java.util.List;

public interface ServiceDiscovery {


    List<ServiceMeta> discovery(String serviceName);


}
