package com.rabbitStudy.publishAndSubscriber;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
//一个消息发给多个消费者
public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException {
		send();
	}

	private static final String EXCHANGE_NAME = "test_exchange_fanout";

	public static void send() throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//获取通道
		Channel channel=connection.createChannel();
		//申明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//分发：fanout
		//发送消息
		String message = "test_exchange_str";
		channel.basicPublish(EXCHANGE_NAME,"",null, message.getBytes());
		System.out.println("sendMessage");
		channel.close();
		connection.close();
	}
}
