package com.atguigu.springbootrabbitmq.controller;

import com.atguigu.springbootrabbitmq.config.ConfirmConfig;
import com.atguigu.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *  演示如下：
 *     1、生产者发送消息给交换机，交换机未接收到消息，通过消息确认回调函数
 *     2、生产者发送消息给队列，交换机路由转发错误，队列未接收消息，消息退回回调函数。
 *
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.controller
 * @date 2022/4/25 16:15
 * @Copyright © 上海卓繁
 */
@RequestMapping("/confirm")
@RestController
@Slf4j
public class ConfirmController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{}，生产者开始往队列发送消息：{}", new Date().toString(),message);
        CorrelationData correlationData = new  CorrelationData();
        correlationData.setId("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY, message,correlationData);

        log.info("当前时间：{}，生产者开始往队列发送消息：{}", new Date().toString(),message+"测试");
        CorrelationData correlationData1 = new  CorrelationData();
        correlationData1.setId("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY+1, message+"测试",correlationData1);
    }
}
