package com.rabbitStudy.work;

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
		handler();
	}

	// 队列名
	private static final String QUEUE_NAME = "work_queue";

	public static void handler() throws IOException, TimeoutException {
		// 获取连接
		Connection con = ConnectionUtil.getConnection();
		// 获取通道
		Channel channel = con.createChannel();
		// 定义队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 客户端接收消息
		Consumer comsumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "utf-8");
				System.out.println("[1]recivedMessage:" + message);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("[1] has done");
				}
			}

		};
		// 监听队列 自动应答，一旦消息分发给消费者就会从内存中删除。如果消费者进程中途挂掉则消息消失。。。。。。
		channel.basicConsume(QUEUE_NAME, true, comsumer);
	}
}
