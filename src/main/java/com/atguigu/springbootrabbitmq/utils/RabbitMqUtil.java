package com.atguigu.springbootrabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 获取RabbitMQ channel
 * @author liugui
 * @version V1.0
 * @Package com.zfsoft.atguigu.utils
 * @date 2022/4/20 7:38
 * @Copyright © 上海卓繁
 */
public class RabbitMqUtil {

    public static Channel getChannel() throws Exception {
        // 创建 Connect
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机
        connectionFactory.setHost("*.*.*.*");
        // 设置用户名
        connectionFactory.setUsername("admin");
        // 设置密码
        connectionFactory.setPassword("123");
        // 创建连接
        Connection connect = connectionFactory.newConnection();
        // 创建信道
        return connect.createChannel();
    }

}
