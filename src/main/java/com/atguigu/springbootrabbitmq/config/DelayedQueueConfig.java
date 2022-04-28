package com.atguigu.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.config
 * @date 2022/4/25 14:01
 * @Copyright © 上海卓繁
 */
@Configuration
public class DelayedQueueConfig {

    // 声明一个延迟队列
    public static final String DEALY_QUEUE_NAME = "delayed.queue";

    // 声明一个延迟交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    // 声明一个延迟routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    /**
     * 声明一个延迟交换机
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 声明一个延迟队列
     */
    @Bean
    public Queue delayedQueue() {
        return new Queue(DEALY_QUEUE_NAME);
    }

    @Bean
    public Binding delayedQueueBindDelayedExchange(@Qualifier("delayedExchange") CustomExchange delayedExchange,
                                                   @Qualifier("delayedQueue") Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
