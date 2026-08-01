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
 * @description: Kafka 消息消费者 - 通知消息
 * @author: 郑剑锋
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class NotificationMessageConsumer {

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void onNotificationMessage(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        try {
            KafkaMessage message = JSON.parseObject(JSON.toJSONString(record.value()), KafkaMessage.class);
            log.info("收到通知消息: topic=[{}], partition=[{}], offset=[{}], type=[{}], content=[{}]",
                    record.topic(), record.partition(), record.offset(),
                    message != null ? message.getType() : "null",
                    message != null ? message.getContent() : "null");

            // TODO: 业务处理 - 发送通知（邮件、短信、站内信等）

            ack.acknowledge();
        } catch (Exception e) {
            log.error("处理通知消息失败", e);
            ack.acknowledge();
        }
    }

    /**
     * 批量消费示例
     */
    @KafkaListener(topics = "notification-topic", groupId = "notification-batch-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onBatchMessage(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        // 批量消费需要在配置中设置 batch listener
        log.debug("批量消息: {}", record.value());
        ack.acknowledge();
    }

}
