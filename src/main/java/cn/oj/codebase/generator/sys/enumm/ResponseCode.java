package cn.oj.codebase.generator.sys.enumm;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ResponseCode.java
 * @Description TODO
 * @createTime 2021年08月06日 09:04:00
 */
public enum ResponseCode implements Code {
    SUCCESS("200", "执行成功"),
    FAILED("30001", "业务异常"),
    GETEWAY_FLOW("100", "接口限流"),
    GETEWAY_DEGRADE("101", "服务降级"),
    GETEWAY_PARAMFLOW("102", "热点参数限流"),
    GETEWAY_SYSTEMBLOCK("103", "触发系统保护规则"),
    GETEWAY_AUTHORITY("104", "授权规则不通过"),
    GETEWAY_ERROR("105", "网关异常"),
    GETEWAY_TIMEOUT("106", "网关超时"),
    NOT_FOUND("404", "未找到资源"),
    UNAUTH("403", "无访问权限"),
    ERROR("500", "系统错误"),
    AUTH_TOKEN_EXISTENT("2100", "请求中token为空"),
    AUTH_TOKEN_INVALID("2101", "无效token"),
    AUTH_TOKEN_TIMEOUT("2102", "token已过期"),
    AUTH_URI_UNABLE("2103", "用户权限不足"),
    AUTH_IDENTITY_INVALID("2104", "无效身份"),
    VERSION_NUMBERS_INCONSISTENT("2017", "数据不是最新状态，请获取最新状态再修改"),
    SERVER_NO_CONTACT("2013", "服务器无法连通，测试失败"),
    NO_SUCH_DATA_INFORMATION("2016", "无此数据信息");

    private final String code;
    private final String msg;

    private ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode fromCode(String code) {
        ResponseCode[] ecs = values();
        ResponseCode[] var2 = ecs;
        int var3 = ecs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ResponseCode ec = var2[var4];
            if (ec.getCode().equals(code)) {
                return ec;
            }
        }

        return SUCCESS;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", this.code, this.msg);
    }
}

