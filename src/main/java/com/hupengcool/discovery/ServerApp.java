package com.hupengcool.discovery;

import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

/**
 * Test ServiceRegistrar
 * In real application you may resister your service in a servlet listener.
 * You can use uriSpec to get the service url ,also you can store the information in a object
 */
public class ServerApp {

	public final static String ZK_HOST_PORT = "127.0.0.1:2181";
	public final static String ZK_BASE_PATH = "zuche/proxy2";
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_HOST_PORT, new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceRegistor serviceRegistrar = new ServiceRegistor(client,ZK_BASE_PATH);
        ServiceInstance<ServerNode> instance1 = ServiceInstance.<ServerNode>builder()
                .name("myservice1")//name建议unique。如果name一样
                .port(12345)
                .address("192.168.1.160")
                .payload(buildServerNode("carmapi","192.168.1.101","101"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
       
        
        ServiceInstance<ServerNode> instance11 = ServiceInstance.<ServerNode>builder()
                .name("myservice4")//name建议unique。如果name一样
                .port(12345)
                .address("192.168.1.160")
                .payload(buildServerNode("carmapi","192.168.1.101","111"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        ServiceInstance<ServerNode> instance21 = ServiceInstance.<ServerNode>builder()
                .name("myservice4")//name建议unique。如果name一样
                .port(12345)
                .address("192.168.1.160")
                .payload(buildServerNode("carmapi","192.168.1.102","222"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        ServiceInstance<ServerNode> instance31 = ServiceInstance.<ServerNode>builder()
                .name("myservice4")//name建议unique。如果name一样
                .port(12345)
                .address("192.168.1.160")
                .payload(buildServerNode("carmapi","192.168.1.103","333"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        
        serviceRegistrar.registerService(instance1);
        
        serviceRegistrar.registerService(instance11);
        serviceRegistrar.registerService(instance21);
        serviceRegistrar.registerService(instance31);
        
        serviceRegistrar.unregisterService(instance1);
        

        System.out.println("done.");
        System.out.println(new Date());
        Thread.sleep(1000*60L);
        System.out.println(new Date());
        //当server挂掉后，zk中的实例会自动销毁
        
    }
    
    private static ServerNode buildServerNode(String name,String address,String version){
    	ServerNode node = new ServerNode();
    	node.setAddress(address);
    	node.setName(name);
    	node.setVersion(version);
    	return node;
    }
}

/**
 * myservice4 是ServiceInstance的name。
 * 当注册多个name相同的实例时，会创建多个临时节点，每个节点表示一个实例。
 * 实例发现的时候，根据name查询服务，当存在某个name的实例存在多个时，根据指定的负载均衡算法获取其中之一。
 * [zk: localhost:2181(CONNECTED) 1] ls /zuche/proxy2/myservice4
[c73f91a7-8a60-473c-b975-92b3e3a4a4cc, 81e28ae7-8c49-4924-bc6c-d070ee26cb14, 6442d774-7af5-41b5-98cc-716734f2059a]
[zk: localhost:2181(CONNECTED) 2] ls /zuche/proxy2/myservice4
[]

*/
