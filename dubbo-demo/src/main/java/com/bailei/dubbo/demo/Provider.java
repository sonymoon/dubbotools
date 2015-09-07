package com.bailei.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class Provider {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:provider.xml" });
		context.start();

		System.in.read(); // 按任意键退出
	}

}
