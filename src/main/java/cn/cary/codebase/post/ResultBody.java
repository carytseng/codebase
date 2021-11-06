package cn.cary.codebase.post;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.jackson.JsonObjectDeserializer;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName ResultBody.java
 * @Description TODO
 * @createTime 2021年10月15日 11:27:00
 */
@Data
public class ResultBody {
    private Integer resultCode;
    private String message;
    private JSONObject dataItems;

    public ResultBody() {}
    public ResultBody(Integer resultCode) {
        this.resultCode = resultCode;
        this.message = "处理成功";
    }
}
