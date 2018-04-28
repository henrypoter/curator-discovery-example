package com.hupengcool.discovery;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.apache.curator.x.discovery.strategies.RoundRobinStrategy;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ServiceDiscoverer {
    private ServiceDiscovery<ServerNode> serviceDiscovery;
    private Map<String, ServiceProvider<ServerNode>> providers = Maps.newConcurrentMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private AtomicBoolean closed = new AtomicBoolean(false);
    private Object lock = new Object();


    public ServiceDiscoverer(CuratorFramework client, String basePath) throws Exception {
        JsonInstanceSerializer<ServerNode> serializer = new JsonInstanceSerializer<ServerNode>(ServerNode.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServerNode.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();
    }


    public ServiceInstance<ServerNode> getInstanceByName(String serviceName) throws Exception {
        ServiceProvider<ServerNode> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscovery.serviceProviderBuilder().
                            serviceName(serviceName).
                            providerStrategy(new RoundRobinStrategy<ServerNode>())
                            .build();
                    provider.start();
                    closeableList.add(provider);
                    providers.put(serviceName, provider);
                }
            }
        }

        return provider.getInstance();
    }


    public void close() {
        Preconditions.checkState(closed.compareAndSet(false, true), "discovery service already closed...");

        for (Closeable closeable : closeableList) {
            CloseableUtils.closeQuietly(closeable);
        }
    }


}
