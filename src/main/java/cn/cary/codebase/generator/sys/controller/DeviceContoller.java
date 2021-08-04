package cn.cary.codebase.generator.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-05-26 18:57
 **/
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceContoller {

    @PostMapping("/receive")
    public void receiveMsg (@RequestBody Map<String,Object> str) {
        log.info(str.toString());
    }
}
