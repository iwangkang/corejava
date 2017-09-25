package me.wangkang.amqp.task;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestQueueCount {

	private static final String QUEUE_NAME = "queue+";
	private static final String EXCHANGE_NAME = "com.demo.exchange.fanout";
	private static final String EXCHANGE_NAME2 = "com.demo.exchange.fanout2";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
	    factory.setUsername("jc");
		factory.setPassword("jc");
		factory.setVirtualHost("jc");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		
		for (int i = 0; i < 50000; i++) {
			channel.queueDeclare((QUEUE_NAME+i), false, true, true, null);
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false);
			channel.exchangeDeclare(EXCHANGE_NAME2, BuiltinExchangeType.FANOUT, false);
			channel.queueBind((QUEUE_NAME+i), EXCHANGE_NAME, "");
			channel.queueBind((QUEUE_NAME+i), EXCHANGE_NAME2, "");
			System.out.println("[X] Create queue name is " + (QUEUE_NAME+i));
		}
		
		

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume((QUEUE_NAME+0), true, consumer);
	}
	
}
