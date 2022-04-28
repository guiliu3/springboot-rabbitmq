package com.atguigu.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ 配置类
 * 完成、普通交换机、死信队列交换机，普通队列、死信队列的配置
 * 并完成交换机和RoutingKey的绑定关系
 *
 * @author liugui
 * @version V1.0SS
 * @Package com.atguigu.springbootrabbitmq.config
 * @date 2022/4/23 16:57
 * @Copyright © 上海卓繁
 */
@Configuration
public class TtlQueueConfig {
    // 普通交换机 X
    public static final String X_CHANGE_NAME =  "X";
    // 死信队列交换机 Y
    public static final String Y_DEAD_LETTER_CHANGE_NAME =  "Y";
    // 普通队列QA
    public static final String QA_QUEUE_NAME =  "QA";
    // 普通队列QB
    public static final String QB_QUEUE_NAME =  "QB";
    // 死信队列
    public static final String QD_DEAD_LETTER_QUEUE_NAME =  "QD";
    // 普通队列QC,但是不设置过期时间，过期时间由生产者传递
    public static final String QC_QUEUE_NAME =  "QC";


    @Bean("QC")
    public Queue queueC(){
        Map<String,Object> args = new HashMap<>();
        // 设置死信队列交换机
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_CHANGE_NAME);
        // 设置RoutingKey
        args.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QC_QUEUE_NAME).withArguments(args).build();
    }

    /**
     *  QC和普通交换机X的绑定关系
     */
    @Bean
    public Binding queueCBindingX(@Qualifier("QC") Queue QC,@Qualifier("xExChange") DirectExchange xExChange){
        return BindingBuilder.bind(QC).to(xExChange).with("XC");
    }

    /**
     *  X普通交换机的Bean定义
     */
    @Bean("xExChange")
    public DirectExchange xExChange(){
        return new DirectExchange(X_CHANGE_NAME);
    }

    /**
     *  Y死信队列交换机的Bean定义
     */
    @Bean("yExChange")
    public DirectExchange yExChange(){
        return new DirectExchange(Y_DEAD_LETTER_CHANGE_NAME);
    }

    /**
     *  普通队列QA的定义
     */
    @Bean("QA")
    public Queue queueA(){
        Map<String,Object> args = new HashMap<>();
        // 设置过期时间
        args.put("x-message-ttl",10000);
        // 设置死信队列交换机
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_CHANGE_NAME);
        // 设置RoutingKey
        args.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QA_QUEUE_NAME).withArguments(args).build();
    }

    /**
     *  普通队列QB的定义
     */
    @Bean("QB")
    public Queue queueB(){
        Map<String,Object> args = new HashMap<>();
        // 设置过期时间
        args.put("x-message-ttl",40000);
        // 设置死信队列交换机
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_CHANGE_NAME);
        // 设置RoutingKey
        args.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QB_QUEUE_NAME).withArguments(args).build();
    }

    /**
     *  死信队列的定义
     */
    @Bean("QD")
    public Queue queueD(){
        return new Queue(QD_DEAD_LETTER_QUEUE_NAME);
    }


    /**
     *  QA 和 QB 和普通交换机X的绑定关系
     */
    @Bean
    public Binding queueABindingX(@Qualifier("QA") Queue QA,@Qualifier("xExChange") DirectExchange xExChange){
        return BindingBuilder.bind(QA).to(xExChange).with("XA");
    }

    /**
     *  QA 和 QB 和普通交换机X的绑定关系
     */
    @Bean
    public Binding queueBBindingX(@Qualifier("QB") Queue QB,@Qualifier("xExChange") DirectExchange xExChange){
        return BindingBuilder.bind(QB).to(xExChange).with("XB");
    }

    /**
     * QD 和死信队列的绑定关系
     */
    @Bean
    public Binding queueDBindingY(@Qualifier("QD") Queue QD,@Qualifier("yExChange") DirectExchange yExChange){
        return BindingBuilder.bind(QD).to(yExChange).with("YD");
    }
}
