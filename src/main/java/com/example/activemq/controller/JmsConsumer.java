package com.example.activemq.controller;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author creator hujiawei 2020/10/13 11:02 下午
 * @author updater
 * @version 1.0.0
 */
public class JmsConsumer {
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        // 1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        // 2.通过连接工厂，获得连接connection + 启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3.创建会话 第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5.创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

//        while (true){
//            TextMessage textMessage = (TextMessage) messageConsumer.receive();
//            if(null != textMessage){
//                System.out.println("***消费者接受消息"+textMessage.getText());
//            } else {
//                break;
//            }
//        }
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof Message){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("***消费者接受消息"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();
        // 9。关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
