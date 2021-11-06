package cn.cary.codebase.post;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName JsController.java
 * @Description TODO
 * @createTime 2021年10月15日 11:10:00
 */
@Slf4j
@RestController
@RequestMapping("/park")
public class JsController {

    @PostMapping("/parkIn")
    public ResultBody inPark(@RequestBody ParkInDTO parkInDTO) {
        log.info("入场信息...");
        log.info(JSONObject.toJSONString(parkInDTO));
        ResultBody resultBody= new ResultBody(0);
        return resultBody;
    }

    @PostMapping("/parkOut")
    public ResultBody outPark(@RequestBody ParkOutDTO parkOutDTO) {
        log.info("出场信息...");
        log.info(JSONObject.toJSONString(parkOutDTO));
        ResultBody resultBody= new ResultBody(0);
        return resultBody;
    }

    @PostMapping("/parkCharge")
    public ResultBody parkCharge(@RequestBody ParkCharge parkCharge) {
        log.info("收费信息...");
        log.info(JSONObject.toJSONString(parkCharge));
        ResultBody resultBody= new ResultBody(0);
        return resultBody;
    }



}
