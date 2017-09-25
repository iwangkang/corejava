package me.wangkang.amqp.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * @description: fanout类型的交换器会将收到的消息广播到绑定该交换器的所有队列上
 * 参考地址：https://www.rabbitmq.com/tutorials/tutorial-three-java.html
 * @author: wangk@jcinfo.com
 * @create: 2017年7月24日下午3:40:33
 * @copyright ©1995-2017 ChangChun JiaCheng Project Of The Network CO.,LTD.All Rights Reserved.
 */
public class EmitLog {

	private static final String EXCHANGE_NAME = "com.demo.exchange.fanout";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("jc");
		factory.setPassword("jc");
		factory.setVirtualHost("jc");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

//		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

		String message = getMessage(argv);

		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		return "info: Hello World!";
	}

}
