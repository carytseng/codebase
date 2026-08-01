package cn.oj.codebase.mq.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: codebase
 * @description: Kafka 配置类
 * @author: 郑剑锋
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:127.0.0.1:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.properties.sasl.mechanism:}")
    private String saslMechanism;

    @Value("${spring.kafka.consumer.properties.sasl.jaas.config:}")
    private String saslJaasConfig;

    @Value("${spring.kafka.consumer.properties.security.protocol:}")
    private String securityProtocol;

    @Value("${spring.kafka.listener.concurrency:3}")
    private int concurrency;

    // ==================== Topic 定义 ====================

    /**
     * 示例 Topic：订单消息
     */
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * 示例 Topic：通知消息
     */
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name("notification-topic")
                .partitions(2)
                .replicas(1)
                .build();
    }

    /**
     * 示例 Topic：日志消息
     */
    @Bean
    public NewTopic logTopic() {
        return TopicBuilder.name("log-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // ==================== 生产者配置 ====================

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // 生产者确认机制
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 批量发送大小
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 缓冲区大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // SASL 认证（如果配置了）
        addSaslIfConfigured(props);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ==================== 消费者配置 ====================

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "message:cn.oj.codebase.mq.kafka.model.KafkaMessage");
        // 自动提交偏移量（建议手动提交）
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 从最早的消息开始消费
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 会话超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);

        // SASL 认证（如果配置了）
        addSaslIfConfigured(props);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        // 设置手动提交模式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    // ==================== 辅助方法 ====================

    /**
     * 如果配置了 SASL 认证，则添加认证信息
     */
    private void addSaslIfConfigured(Map<String, Object> props) {
        if (saslMechanism != null && !saslMechanism.isEmpty()) {
            props.put("sasl.mechanism", saslMechanism);
        }
        if (saslJaasConfig != null && !saslJaasConfig.isEmpty()) {
            props.put("sasl.jaas.config", saslJaasConfig);
        }
        if (securityProtocol != null && !securityProtocol.isEmpty()) {
            props.put("security.protocol", securityProtocol);
        }
    }

}
