package com.atguigu.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.config
 * @date 2022/4/25 16:20
 * @Copyright © 上海卓繁
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * @param correlationData 包含消息的id和消息
     * @param ack             回应boolean值，true表示成功接收到消息  false:表示未接收到消息
     * @param reason          成功接收到消息，则为空，否则为失败原因
     * @return void
     * @author liugui
     * @time 2022/4/25 16:21
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String reason) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("成功接收到消息:id:{}", id);
        } else {
            log.info("未成功接收到消息:id:{},原因：{}", id, reason);
        }
    }


    /**
     * 这个回调只有未成功接收到消息，才会触发
     *
     * @param returnedMessage
     * @return void
     * @author liugui
     * @time 2022/4/25 16:32
     */

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        Message message = returnedMessage.getMessage();
        log.info("未成功接收到消息:{},原因：{},交换机：{},routingKey：{}", message.getBody()
                , returnedMessage.getReplyText()
                , returnedMessage.getExchange(),
                returnedMessage.getRoutingKey());
    }
}
