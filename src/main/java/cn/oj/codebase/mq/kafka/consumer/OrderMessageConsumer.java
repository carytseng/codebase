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
 * @description: Kafka 消息消费者 - 订单消息
 * @author: 郑剑锋
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class OrderMessageConsumer {

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void onOrderMessage(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        try {
            KafkaMessage message = JSON.parseObject(JSON.toJSONString(record.value()), KafkaMessage.class);
            log.info("收到订单消息: topic=[{}], partition=[{}], offset=[{}], messageId=[{}], content=[{}]",
                    record.topic(), record.partition(), record.offset(),
                    message != null ? message.getId() : "null",
                    message != null ? message.getContent() : "null");

            // TODO: 业务处理 - 处理订单逻辑

            // 手动提交偏移量
            ack.acknowledge();
        } catch (Exception e) {
            log.error("处理订单消息失败", e);
            // 根据业务决定是否提交偏移量
            ack.acknowledge();
        }
    }

}
