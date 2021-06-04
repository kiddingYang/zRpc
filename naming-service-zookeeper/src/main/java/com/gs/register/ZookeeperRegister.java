package com.gs.register;

import com.gs.rpc.register.ServiceMeta;
import com.gs.rpc.register.ServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;

@Slf4j
public class ZookeeperRegister implements ServiceRegister {


    private ServiceDiscovery<ServiceMeta> serviceDiscovery;

    private CuratorFramework curatorFramework;

    public ZookeeperRegister(String zkHost, int port, String servicePath) throws Exception {
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
    public void doRegister(ServiceMeta serviceMeta) {
        try {
            log.info("register service : {}", serviceMeta);
            serviceDiscovery.registerService(convertServiceInstance(serviceMeta));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) {
        try {
            log.info("un register service : {}", serviceMeta);
            serviceDiscovery.unregisterService(convertServiceInstance(serviceMeta));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            serviceDiscovery.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        curatorFramework.close();
    }

    private ServiceInstance<ServiceMeta> convertServiceInstance(ServiceMeta serviceMeta) {
        try {
            return ServiceInstance.<ServiceMeta>builder()
                    .id(serviceMeta.getServiceId())
                    .name(serviceMeta.getServiceName())
                    .address(serviceMeta.getHost())
                    .port(serviceMeta.getPort())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
