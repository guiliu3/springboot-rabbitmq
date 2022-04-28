package com.atguigu.springbootrabbitmq.test;

import com.atguigu.springbootrabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.test
 * @date 2022/4/25 20:59
 * @Copyright © 上海卓繁
 */
public class Consumer {

    public static final String QUEUE_NAME = "prior_queue";

    public static void main(String[] args) throws Exception {
        // 获取一个信道
        Channel channel = RabbitMqUtil.getChannel();
        // 声明一个队列
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10); // RabbitMQ 优先级支持 0-255
        channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);

        // 声明一个成功消费消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody());
            System.out.println("成功消费消息：" + msg);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, message -> {
        });
    }
}
