package com.rabbitStudy.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException {
		send();
	}

	private static final String EXCHANGE_NAME = "test_exchange_direct";

	public static void send() throws IOException, TimeoutException {
		// 创建连接
		Connection connection = ConnectionUtil.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		// 申明路由
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// 发送消息
		String str = "directSender";
		channel.basicPublish(EXCHANGE_NAME, "happy", null, str.getBytes());
		System.out.println("directSender_sendMessage");
		channel.close();
		connection.close();
	}
}
