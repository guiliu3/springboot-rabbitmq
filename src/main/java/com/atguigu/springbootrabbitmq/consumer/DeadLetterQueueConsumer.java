package com.atguigu.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 死信队列消费者监听
 *
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.consumer
 * @date 2022/4/23 17:34
 * @Copyright © 上海卓繁
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody(), "UTF-8");
        log.info("当前时间：{}，延迟队列接收到信息：{}", new Date().toString(), msg);
    }


}
