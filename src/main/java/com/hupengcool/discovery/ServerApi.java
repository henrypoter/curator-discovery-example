package com.hupengcool.discovery;

import java.io.Serializable;

/**
 * API接口描述对象，用于封装每个API的元数据。
 */
public class ServerApi implements Serializable, Comparable<ServerApi> {

    private static final long serialVersionUID = -7294840314025892888L;

    private String apiName;

    private String apiDesc;

    private String appRoot;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getAppRoot() {
        return appRoot;
    }

    public void setAppRoot(String appRoot) {
        this.appRoot = appRoot;
    }

    public ServerApi(String apiName, String apiDesc, String appRoot) {
        this.apiName = apiName;
        this.apiDesc = apiDesc;
        this.appRoot = appRoot;
    }

    @Override
    public int compareTo(ServerApi apiVO) {
        String thisName = this.getApiName();
        String targetName = apiVO.getApiName();
        return thisName.compareTo(targetName);
    }
}



