package cn.cary.codebase.threadpool;

import java.util.StringJoiner;

public class GatewayRequest {

    private String ssoUrl;

    private String umsToken;

    private String applicationCode;

    public String getSsoUrl() {
        return ssoUrl;
    }

    public GatewayRequest setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
        return this;
    }

    public String getUmsToken() {
        return umsToken;
    }

    public GatewayRequest setUmsToken(String umsToken) {
        this.umsToken = umsToken;
        return this;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public GatewayRequest setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GatewayRequest.class.getSimpleName() + "[", "]")
                .add("ssoUrl='" + ssoUrl + "'")
                .add("umsToken='" + umsToken + "'")
                .add("applicationCode='" + applicationCode + "'")
                .toString();
    }
}
