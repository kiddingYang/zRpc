package com.gs.register;

import com.gs.rpc.register.ServiceDiscovery;
import com.gs.rpc.register.ServiceMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ZookeeperDiscovery implements ServiceDiscovery {


    private org.apache.curator.x.discovery.ServiceDiscovery<ServiceMeta> serviceDiscovery;

    private CuratorFramework curatorFramework;


    public ZookeeperDiscovery(String zkHost, int port, String servicePath) throws Exception {
        this.curatorFramework = CuratorFrameworkFactory.newClient(zkHost + ":" + port, new ExponentialBackoffRetry(1000, 3));
        this.curatorFramework.start();
        JsonInstanceSerializer<ServiceMeta> serializer = new JsonInstanceSerializer<>(ServiceMeta.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceMeta.class)
                .client(curatorFramework)
                .serializer(serializer)
                .basePath(servicePath)
                .build();
        serviceDiscovery.start();
    }

    @Override
    public List<ServiceMeta> discovery(String serviceName) {
        try {
            List<ServiceMeta> serviceMetas = new ArrayList<>();
            Collection<ServiceInstance<ServiceMeta>> serviceInstances = serviceDiscovery.queryForInstances(serviceName);
            for (ServiceInstance<ServiceMeta> serviceInstance : serviceInstances) {
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceId(serviceInstance.getId());
                serviceMeta.setHost(serviceInstance.getAddress());
                serviceMeta.setPort(serviceInstance.getPort());
                serviceMeta.setServiceName(serviceInstance.getName());
                serviceMetas.add(serviceMeta);
            }
            return serviceMetas;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void close() {
        try {
            serviceDiscovery.close();
            curatorFramework.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
