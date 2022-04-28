package com.atguigu.springbootrabbitmq.test;

import com.atguigu.springbootrabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.test
 * @date 2022/4/25 19:43
 * @Copyright © 上海卓繁
 */
public class Producer {

    public static final String QUEUE_NAME = "prior_queue";

    public static void main(String[] args) throws Exception {
        // 获取一个信道
        Channel channel = RabbitMqUtil.getChannel();

        for (int i = 0; i < 10; i++) {
            String msg = "info" + i;
            if (i == 5) {
                AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
                new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());
            } else {
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            }
            System.out.println("生产者开始生产消息："+msg);
        }

    }
}
