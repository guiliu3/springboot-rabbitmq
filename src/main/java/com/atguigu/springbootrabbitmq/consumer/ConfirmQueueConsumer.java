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
 * @date 2022/4/25 16:12
 * @Copyright © 上海卓繁
 */
@Component
@Slf4j
public class ConfirmQueueConsumer {


    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void ReceiveConfirmMessage(Message message) {
        String msg = new String(message.getBody());
        log.info("消息接收到消息:{}", msg);
    }
}
