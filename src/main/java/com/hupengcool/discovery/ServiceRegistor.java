package com.hupengcool.discovery;

import com.google.common.base.Preconditions;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

 
public class ServiceRegistor{

    private ServiceDiscovery<ServerNode> serviceDiscovery;
    private final CuratorFramework client;
    private AtomicBoolean closed = new AtomicBoolean(false);

    public ServiceRegistor(CuratorFramework client,String basePath) throws Exception {
        this.client = client;
        JsonInstanceSerializer<ServerNode> serializer = new JsonInstanceSerializer<ServerNode>(ServerNode.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServerNode.class)
                .client(client)
                .serializer(serializer)
                .basePath(basePath)
                .build();
        serviceDiscovery.start();
    }

    public void registerService(ServiceInstance<ServerNode> serviceInstance) throws Exception {
    	ServiceInstance<ServerNode> in = serviceDiscovery.queryForInstance(serviceInstance.getName(), serviceInstance.getId());
        if(in == null){
        	serviceDiscovery.registerService(serviceInstance);	
        }else{
        	serviceDiscovery.updateService(serviceInstance);
        }
    	
    }

    public void unregisterService(ServiceInstance<ServerNode> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);

    }

    public void updateService(ServiceInstance<ServerNode> serviceInstance) throws Exception {
        serviceDiscovery.updateService(serviceInstance);

    }

    public void close() throws IOException {
        Preconditions.checkState(closed.compareAndSet(false, true), "Registry service already closed...");
        serviceDiscovery.close();
    }
}
