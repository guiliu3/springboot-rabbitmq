package com.atguigu.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 基于插件的延迟消息消费者
 *
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.consumer
 * @date 2022/4/25 14:16
 * @Copyright © 上海卓繁
 */
@Component
@Slf4j
public class ReceiveDelayedConsumer {

    // 声明一个延迟队列
    public static final String DEALY_QUEUE_NAME = "delayed.queue";

    @RabbitListener(queues = DEALY_QUEUE_NAME)
    public void receiveDelayedMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("从延迟队列接收消息时间：{}，消息内容：{}", new Date().toString(), msg);
    }

}
