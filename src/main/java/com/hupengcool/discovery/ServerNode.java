package com.hupengcool.discovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * MAPI实例，代表一台MAPI服务器节点。 
 */
@JsonRootName("ServerNode")
public class ServerNode implements Serializable{

	private static final long serialVersionUID = -9035815628279408461L;

	/**
     * 实例版本
     */
    private String version;

    /**
     * 版本描述
     */
    private String desc;

    /**
     * 地址
     */
    private String address;

    /**
     * 项目名
     */
    private String name;

    /**
     * 支持渠道,请求会被优先分配到匹配channel的服务器上
     */
    private String[] channels;

    /**
     * 实例下的所有API列表
     */
    private List<ServerApi> apiList = new ArrayList<ServerApi>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channel) {
        this.channels = channel;
    }

    public List<ServerApi> getApiList() {
        return apiList;
    }

    public void setApiList(List<ServerApi> apiList) {
        this.apiList = apiList;
    }

    public boolean supportApi(String apiName) {
        for (ServerApi api : apiList) {
            if (api.getApiName().equals(apiName)) {
                return true;
            }
        }
        return false;
    }

    public boolean supportChannel(String channel) {
        if(channels == null || channel == null){
            return false;
        }
        for (String c : channels) {
            if (c.equals(channel)) {
                return true;
            }
        }
        return false;
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerNode that = (ServerNode) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
