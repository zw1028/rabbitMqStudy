package com.rabbitStudy.publishAndSubscriber;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ListennerOne {
public static void main(String[] args) throws IOException, TimeoutException {
	hanlder();
}

private static final String QUEUE_NAME ="work_queue";

private static final String EXCHANGE_NAME = "test_exchange_fanout";

public static void hanlder () throws IOException, TimeoutException {
	//获取连接
	Connection connection = ConnectionUtil.getConnection();

	//获取通道
	Channel channel = connection.createChannel();
	//申明队列
	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	//设定一次只分发一条消息
	channel.basicQos(1);
	//绑定到交换机
	channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, "");
	//定义一个消费者
	Consumer consumer=new DefaultConsumer(channel) {

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {
			String message= new String(body,"utf-8");
			System.out.println("[1]recivedMessage:"+message);
			channel.basicAck(envelope.getDeliveryTag(), false);
		}
		
	};
	//监听
	channel.basicConsume(QUEUE_NAME, false, consumer);
}

}
