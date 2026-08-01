package cn.oj.codebase.mq.kafka.producer;

import cn.oj.codebase.mq.kafka.model.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @program: codebase
 * @description: Kafka 消息生产者
 * @author: 郑剑锋
 **/
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息（异步）
     */
    public CompletableFuture<SendResult<String, Object>> send(String topic, KafkaMessage message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("消息发送成功: topic=[{}], messageId=[{}], offset=[{}]",
                        topic, message.getId(), result.getRecordMetadata().offset());
            } else {
                log.error("消息发送失败: topic=[{}], messageId=[{}]", topic, message.getId(), ex);
            }
        });
        return future;
    }

    /**
     * 发送消息（指定key，保证相同key的消息到同一分区）
     */
    public CompletableFuture<SendResult<String, Object>> send(String topic, String key, KafkaMessage message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("消息发送成功: topic=[{}], key=[{}], messageId=[{}], offset=[{}]",
                        topic, key, message.getId(), result.getRecordMetadata().offset());
            } else {
                log.error("消息发送失败: topic=[{}], key=[{}], messageId=[{}]", topic, key, message.getId(), ex);
            }
        });
        return future;
    }

    /**
     * 发送消息到默认分区
     */
    public CompletableFuture<SendResult<String, Object>> send(String topic, int partition, String key, KafkaMessage message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, partition, key, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("消息发送成功: topic=[{}], partition=[{}], key=[{}], messageId=[{}], offset=[{}]",
                        topic, partition, key, message.getId(), result.getRecordMetadata().offset());
            } else {
                log.error("消息发送失败: topic=[{}], partition=[{}], key=[{}], messageId=[{}]",
                        topic, partition, key, message.getId(), ex);
            }
        });
        return future;
    }

}
