package com.example.activemq.controller;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author creator hujiawei 2020/10/12 8:59 下午
 * @author updater
 * @version 1.0.0
 */
public class JmsProduce {

    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        // 1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        // 2.通过连接工厂，获得连接connection + 启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3.创建会话 第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5.创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 6.通过MessageProducer生产3个消息放到消息队列中
        for (int i = 0; i < 3; i++) {
            // 7.创建消息
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            // 8.通过producer发送到mq
            messageProducer.send(textMessage);
        }
        // 9。关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("***消息发布到mq");
    }
}
