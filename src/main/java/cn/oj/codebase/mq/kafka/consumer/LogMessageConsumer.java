package cn.oj.codebase.mq.kafka.consumer;

import cn.oj.codebase.mq.kafka.model.KafkaMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @program: codebase
 * @description: Kafka 消息消费者 - 日志消息
 * @author: 郑剑锋
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class LogMessageConsumer {

    @KafkaListener(topics = "log-topic", groupId = "log-group")
    public void onLogMessage(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        try {
            KafkaMessage message = JSON.parseObject(JSON.toJSONString(record.value()), KafkaMessage.class);
            // 日志消息的处理相对简单，直接记录
            log.info("日志消息: [{}] {}", message != null ? message.getType() : "unknown",
                    message != null ? message.getContent() : "null");
            ack.acknowledge();
        } catch (Exception e) {
            log.error("处理日志消息失败", e);
            ack.acknowledge();
        }
    }

}
