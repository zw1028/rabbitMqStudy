package com.rabbitStudy.fairDpatch;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		sendMessage();
	}
	// 队列名
	private static final String QUEUE_NAME = "work_queue";

	public static void sendMessage() throws IOException, TimeoutException, InterruptedException {
		// 获取连接
		Connection con = ConnectionUtil.getConnection();
		// 获取通道
		Channel channel = con.createChannel();
		// 定义队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 设定队列一次只发送一条消息
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		// 发送消息
		for(int i=1;i<=20;i++) {
			System.out.println("sendMessage:"+i);
			byte[] b= new String("sendMessage:"+i).getBytes();
			channel.basicPublish("",QUEUE_NAME, null, b);
			Thread.sleep(1000);
		}
		channel.close();
		con.close();
	}

}
