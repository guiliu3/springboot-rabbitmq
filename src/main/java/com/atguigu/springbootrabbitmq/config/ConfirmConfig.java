package com.atguigu.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.config
 * @date 2022/4/25 16:06
 * @Copyright © 上海卓繁
 */
@Configuration
public class ConfirmConfig {

    // 发布确认交换机名称
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchage";
    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchage";
    // 发布确认队列名称
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    // 备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    // 警告队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    // 发布确认routingkey
    public static final String CONFIRM_ROUTING_KEY = "key1";


    @Bean
    public FanoutExchange backUpExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).alternate(BACKUP_EXCHANGE_NAME).build();
    }

    @Bean
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME);
    }

    @Bean
    public Queue backUpQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }

    @Bean
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE_NAME);
    }

    @Bean
    public Binding confirmExchangeBindConfirmQueue(@Qualifier("confirmQueue") Queue confirmQueue,
                                                   @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding backupQueueBindBackupExchange(@Qualifier("backUpQueue") Queue backUpQueue,
                                                 @Qualifier("backUpExchange") FanoutExchange backUpExchange) {
        return BindingBuilder.bind(backUpQueue).to(backUpExchange);
    }

    @Bean
    public Binding warningQueueBindBackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                  @Qualifier("backUpExchange") FanoutExchange backUpExchange) {
        return BindingBuilder.bind(warningQueue).to(backUpExchange);
    }

}
