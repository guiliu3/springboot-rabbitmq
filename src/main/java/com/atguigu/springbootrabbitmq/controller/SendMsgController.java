package com.atguigu.springbootrabbitmq.controller;

import com.atguigu.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 通过 get请求发送消息给交换机，并传递给2个QA,QB队列
 *
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.controller
 * @date 2022/4/23 17:24
 * @Copyright © 上海卓繁
 */
@RestController
@Slf4j
@RequestMapping("/ttl")
public class SendMsgController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{}，接收消息：{}，并开始转发消息至交换机对应的队列", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自于ttl 为10s的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自于ttl 为40s的队列" + message);
    }


    @GetMapping("/sendExpireMsg/{message}/{ttlTime}")
    public void sendExpireMsg(@PathVariable String message,@PathVariable String ttlTime) {
        log.info("当前时间：{}，设置的过期时间：{}，接收消息：{}，并开始转发消息至交换机对应的队列", new Date().toString(),ttlTime, message);
        rabbitTemplate.convertAndSend("X", "XC",message,msg->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    @GetMapping("/sendDelayedMsg/{message}/{ttlTime}")
    public void sendDelayedMsg(@PathVariable String message,@PathVariable Integer ttlTime) {
        log.info("当前时间：{}，设置的过期时间：{}，接收消息：{}，并开始转发消息至交换机对应的队列", new Date().toString(),ttlTime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY,message, msg->{
            msg.getMessageProperties().setDelay(ttlTime);
            return msg;
        });
    }
}
