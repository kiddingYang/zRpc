package com.gs.register;

import java.util.List;

public interface ServiceDiscovery {


    List<ServiceMeta> discovery(String serviceName);


}
