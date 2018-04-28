package com.hupengcool.discovery;

import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * test ServiceDiscoverer
 */
public class ClientApp {

    public static void main(String[] args) throws Exception {
    	try{
    		 CuratorFramework client = CuratorFrameworkFactory.newClient(ServerApp.ZK_HOST_PORT, new ExponentialBackoffRetry(1000, 3));
    	        client.start();
    	        ServiceDiscoverer serviceDiscoverer = new ServiceDiscoverer(client,ServerApp.ZK_BASE_PATH);

    	        for(int i=0;i<10000;i++){
    	        	  ServiceInstance<ServerNode> instance1 = serviceDiscoverer.getInstanceByName("myservice4");

    	              System.out.println(instance1.getPayload().getVersion());
    	              System.out.println(instance1.getPayload().getAddress());
    	              System.out.println("---------------------------");
    	              Thread.sleep(10L);
    	        }
    	      

    	        ServiceInstance<ServerNode> instance2 = serviceDiscoverer.getInstanceByName("myservice1");

    	        System.out.println(instance2.buildUriSpec());
    	        System.out.println(instance2.getPayload().getAddress());

    	        serviceDiscoverer.close();
    	        CloseableUtils.closeQuietly(client);
    	        System.out.println("client done");
    	}catch(Exception e){

    		System.out.println(new Date());
    		throw e;
    	}
       
    }
}
