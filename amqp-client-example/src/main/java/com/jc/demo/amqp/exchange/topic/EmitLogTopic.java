package com.jc.demo.amqp.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * @description: topic交换器允许不同来源的消息到达同一个队列，根据routingKey将消息分发到对应匹配的队列中去
 * 参考地址：https://www.rabbitmq.com/tutorials/tutorial-five-java.html
 * @author: wangk@jcinfo.com
 * @create: 2017年7月24日下午4:27:38
 * @copyright ©1995-2017 ChangChun JiaCheng Project Of The Network CO.,LTD.All Rights Reserved.
 */
public class EmitLogTopic {

	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			factory.setUsername("jc");
			factory.setPassword("jc");
			factory.setVirtualHost("jc");

			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

			String routingKey = getRouting(argv);
			String message = getMessage(argv);

			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	private static String getRouting(String[] strings) {
		return "*.orange.*";
	}

	private static String getMessage(String[] strings) {
		return "lazy.orange.elephant";
	}

}
