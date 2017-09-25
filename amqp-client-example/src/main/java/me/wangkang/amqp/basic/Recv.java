package me.wangkang.amqp.basic;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @description: hello world!
 * 参考地址：https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * @author: wangk@jcinfo.com
 * @create: 2017年7月25日下午1:57:59
 * @copyright ©1995-2017 ChangChun JiaCheng Project Of The Network CO.,LTD.All Rights Reserved.
 */
public class Recv {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("127.0.0.1");
    factory.setUsername("jc");
	factory.setPassword("jc");
	factory.setVirtualHost("jc");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
