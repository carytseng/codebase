package cn.oj.codebase.mq.kafka.controller;

import cn.oj.codebase.mq.kafka.model.KafkaMessage;
import cn.oj.codebase.mq.kafka.producer.KafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: codebase
 * @description: Kafka 消息控制器
 * @author: 郑剑锋
 **/
@Slf4j
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
@Tag(name = "Kafka 消息管理")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/send/{topic}")
    @Operation(summary = "发送消息到指定Topic")
    public Map<String, Object> sendMessage(
            @PathVariable String topic,
            @RequestBody KafkaMessage message) {
        Map<String, Object> result = new HashMap<>();
        try {
            kafkaProducer.send(topic, message);
            result.put("code", 0);
            result.put("msg", "消息发送成功");
            result.put("data", message.getId());
        } catch (Exception e) {
            log.error("发送消息失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/send/order")
    @Operation(summary = "发送订单消息")
    public Map<String, Object> sendOrderMessage(
            @Parameter(description = "订单内容") @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            KafkaMessage message = KafkaMessage.of("order", content);
            kafkaProducer.send("order-topic", message);
            result.put("code", 0);
            result.put("msg", "订单消息发送成功");
            result.put("data", message.getId());
        } catch (Exception e) {
            log.error("发送订单消息失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/send/notification")
    @Operation(summary = "发送通知消息")
    public Map<String, Object> sendNotificationMessage(
            @Parameter(description = "通知内容") @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            KafkaMessage message = KafkaMessage.of("notification", content);
            kafkaProducer.send("notification-topic", message);
            result.put("code", 0);
            result.put("msg", "通知消息发送成功");
            result.put("data", message.getId());
        } catch (Exception e) {
            log.error("发送通知消息失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/send/log")
    @Operation(summary = "发送日志消息")
    public Map<String, Object> sendLogMessage(
            @Parameter(description = "日志内容") @RequestParam String content,
            @Parameter(description = "日志类型") @RequestParam(defaultValue = "info") String type) {
        Map<String, Object> result = new HashMap<>();
        try {
            KafkaMessage message = KafkaMessage.of(type, content);
            kafkaProducer.send("log-topic", message);
            result.put("code", 0);
            result.put("msg", "日志消息发送成功");
            result.put("data", message.getId());
        } catch (Exception e) {
            log.error("发送日志消息失败", e);
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
