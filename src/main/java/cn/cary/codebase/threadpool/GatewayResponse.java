package cn.cary.codebase.threadpool;

import java.util.StringJoiner;

public class GatewayResponse {

    private String applicationCode;

    private String appToken;

    private String tenantId;

    public String getApplicationCode() {
        return applicationCode;
    }

    public GatewayResponse setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public String getAppToken() {
        return appToken;
    }

    public GatewayResponse setAppToken(String appToken) {
        this.appToken = appToken;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public GatewayResponse setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GatewayResponse.class.getSimpleName() + "[", "]")
                .add("applicationCode='" + applicationCode + "'")
                .add("appToken='" + appToken + "'")
                .add("tenantId='" + tenantId + "'")
                .toString();
    }
}
