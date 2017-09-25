package me.wangkang.amqp.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	
	private static final String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws IOException, TimeoutException {
//		we can create a connection to the server:
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("jc");
		factory.setPassword("jc");
		factory.setVirtualHost("jc");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
//		To send, we must declare a queue for us to send to; then we can publish a message to the queue:
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello world!";
		for(int i=0; i<5; i++){
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("[x] Sent '" + message + "' " + i);
		}
		
//		Lastly, we close the channel and the connection;
		channel.close();
		connection.close();
	}
	
}
