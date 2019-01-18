package com.example.mq.producter;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producter {

    public static void main(String[] args){
        try {
            start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void start() throws JMSException {
        ActiveMQConnectionFactory activeFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
        Connection conn = activeFactory.createConnection();
        //启动连接
        conn.start();
        //设置消息可靠性 自动签收
        Session session = conn.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //将消息放入队列
        Queue queue = session.createQueue("QUENSName2018");
        //设置存放消息队列内容
        MessageProducer producter = session.createProducer(queue);
        TextMessage text = session.createTextMessage("0000000555555555555");
        session.commit();
        producter.send(text);
        //关闭连接
        conn.close();

    }
}
