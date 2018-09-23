package com.rabbitStudy.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitStudy.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Listenner2 {
	public static void main(String[] args) throws IOException, TimeoutException {
		handler();
	}

	private static final String EXCHANGE_NAME = "test_exchange_direct";
	private static final String QUEUE_NAME = "test_direct2";

	public static void handler() throws IOException, TimeoutException {
		// 创建连接
		Connection connection = ConnectionUtil.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		// 申明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 队列绑定
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error", null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "message", null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info", null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "happy", null);
		// 设定队列分发消息数
		channel.basicQos(1);
		// 创建消费者
		Consumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "utf-8");
				System.out.println("[2]receviedMessage:" + message);
				channel.basicAck(envelope.getDeliveryTag(), false);
			}

		};
		// 监听队列
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
