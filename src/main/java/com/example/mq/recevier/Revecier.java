package com.example.mq.recevier;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Revecier {
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
        Session session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //将消息放入队列
        Queue queue = session.createQueue("QUENSName2018");
        MessageConsumer consu = session.createConsumer(queue);
        while (true){
            Message receive = consu.receive();
            TextMessage text = (TextMessage) receive;
            session.commit();
            if(text!=null){
                System.out.println(text.getText());
                //手动签收--不要设置成自动签收，会出现消息重复消费
                text.acknowledge();
            }else {
                break;
            }
        }

        //关闭连接
        conn.close();

    }


}
