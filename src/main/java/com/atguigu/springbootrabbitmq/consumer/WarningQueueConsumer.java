package com.atguigu.springbootrabbitmq.consumer;

import com.atguigu.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.consumer
 * @date 2022/4/25 19:05
 * @Copyright © 上海卓繁
 */
@Component
@Slf4j
public class WarningQueueConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("备份交换机发送消息至警告队列，消费者从警告队列消息消费：{}", msg);
    }
}
