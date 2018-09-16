package com.rabbitStudy.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {
	private static final String QUEUE_NAME = "simple_queue";
	private static final String MESSAGE = "hello world";
	public static void main(String[] args) throws Exception {
		sendMessage();
	}

	// 发送消息
	public static void sendMessage() throws IOException, TimeoutException {
		
		// 创建连接
		Connection con = ConnectionUtil.getConnection();

		// 创建通道
		Channel channel = con.createChannel();
		// 创建队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 发送消息
		channel.basicPublish("", QUEUE_NAME, null, MESSAGE.getBytes());
		
		System.out.println("发送消息："+MESSAGE);
		
		//关闭资源
		try {
			channel.close();
			con.close();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
