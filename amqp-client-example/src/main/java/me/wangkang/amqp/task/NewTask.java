package me.wangkang.amqp.task;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @description: 任务队列示例
 * 参考地址：https://www.rabbitmq.com/tutorials/tutorial-two-java.html
 * @author: wangk@jcinfo.com
 * @create: 2017年7月25日下午1:59:21
 * @copyright ©1995-2017 ChangChun JiaCheng Project Of The Network CO.,LTD.All Rights Reserved.
 */
public class NewTask {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("127.0.0.1");
    factory.setUsername("jc");
	factory.setPassword("jc");
	factory.setVirtualHost("jc");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    String message = getMessage(argv);

    channel.basicPublish("", TASK_QUEUE_NAME,
        MessageProperties.PERSISTENT_TEXT_PLAIN,
        message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings) {
      return "Hello World!";
  }

}
