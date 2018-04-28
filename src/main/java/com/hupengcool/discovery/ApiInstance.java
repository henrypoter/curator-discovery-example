package com.hupengcool.discovery;

import org.codehaus.jackson.map.annotate.JsonRootName;


/**
 * Created by hupeng on 2014/9/16.
 */
@JsonRootName("details")
public class ApiInstance {

    private String id;

    private String host;

    private int port;

    private String apiName;
    
    //List<ServerApi> apiList;//TODO

    public ApiInstance(String id, String listenAddress, int listenPort,String interfaceName) {
        this.id = id;
        this.host = listenAddress;
        this.port = listenPort;
        this.apiName = interfaceName;
    }

    public ApiInstance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListenAddress() {
        return host;
    }

    public void setListenAddress(String listenAddress) {
        this.host = listenAddress;
    }

    public int getListenPort() {
        return port;
    }

    public void setListenPort(int listenPort) {
        this.port = listenPort;
    }

    public String getInterfaceName() {
        return apiName;
    }

    public void setInterfaceName(String interfaceName) {
        this.apiName = interfaceName;
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", apiName='" + apiName + '\'' +
                '}';
    }
}
