package com.rabbitStudy.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException {
		send();
	}

	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void send() throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//获取频道
		Channel channel = connection.createChannel();
		//申明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//发送消息
		String str="topicSender_sendMessage";
		channel.basicPublish(EXCHANGE_NAME, "message.update", false, null, str.getBytes());
		channel.close();
		connection.close();
	}
}
