package cn.cary.codebase.post;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName TestListener.java
 * @Description TODO
 * @createTime 2021年11月02日 15:38:00
 */
@Service
public class TestListener {

    @KafkaListener(topics = "paas_realtime_data",groupId = "test-group")
    public void test(String data){
        System.out.println("kakfa消息数据："+data);
    }

}