package com.rabbitStudy.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	private static final String VIRTUAL_HOST = "/myMQ";

	// 获取连接工具类
	public static Connection getConnection() throws IOException, TimeoutException {
		// 获取工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置参数
		factory.setHost("localhost");
		factory.setUsername("zw");
		factory.setPassword("zw");
		factory.setPort(5672);
		factory.setVirtualHost(VIRTUAL_HOST);

		return factory.newConnection();

	}

}
