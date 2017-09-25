package me.wangkang.amqp.exchange.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {
	private static final String EXCHANGE_NAME = "com.demo.exchange.fanout";
	private static final String EXCHANGE_NAME2 = "com.demo.exchange.fanout2";
	
	private final static String QUEUE_NAME = "com.demo.queue.fanout";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
	    factory.setUsername("jc");
		factory.setPassword("jc");
		factory.setVirtualHost("jc");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false);
		channel.exchangeDeclare(EXCHANGE_NAME2, BuiltinExchangeType.FANOUT, false);
		channel.queueDeclare(QUEUE_NAME, false, true, true, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME2, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
