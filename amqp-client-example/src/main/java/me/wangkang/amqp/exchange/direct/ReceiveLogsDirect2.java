package me.wangkang.amqp.exchange.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsDirect2 {

	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
	    factory.setUsername("jc");
		factory.setPassword("jc");
		factory.setVirtualHost("jc");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		String queueName = channel.queueDeclare().getQueue();

//		for (String severity : argv) {
//			channel.queueBind(queueName, EXCHANGE_NAME, severity);
//		}
		channel.queueBind(queueName, EXCHANGE_NAME, "comd.jc.routingKey2");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
