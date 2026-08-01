package cn.oj.codebase.mq.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: codebase
 * @description: Kafka 消息体
 * @author: 郑剑锋
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {

    /**
     * 消息ID
     */
    private String id;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息来源
     */
    private String source;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 业务键
     */
    private String businessKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 快速创建消息
     */
    public static KafkaMessage of(String type, String content) {
        return KafkaMessage.builder()
                .id(java.util.UUID.randomUUID().toString().replace("-", ""))
                .type(type)
                .content(content)
                .source("codebase")
                .createTime(new Date())
                .build();
    }

}
