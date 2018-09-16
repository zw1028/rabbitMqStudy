package com.rabbitStudy.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Listener {
	private final static String QUEUE_NAME = "simple_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		listenning();
	}
	
	public static void listenning() throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//获取频道
		Channel channel=connection.createChannel();
		//申明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//接收消息
		Consumer consumer=new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"utf-8");
				System.out.println("recivedMessage:"+message);
			}
		};
		//监听队列
		channel.basicConsume(QUEUE_NAME , true, consumer);
	}

}
