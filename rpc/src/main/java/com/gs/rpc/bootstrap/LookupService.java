package com.gs.rpc.bootstrap;

import com.gs.rpc.model.meta.ServiceWrapper;

public interface LookupService {

    ServiceWrapper lookupService(String serviceId);
}
