package com.bailei.dubbo.demo;

import java.util.Map;

import com.alibaba.dubbo.config.ProtocolConfig;

public class DemoServiceStub implements DemoService {

	/**
	 * 
	 */
	private DemoService demoService;
	
	public DemoServiceStub(DemoService demoService) {
		this.demoService = demoService ;
	}

	@Override
	public String sayHello(String name) {
		return "DemoServiceStub";
	}

	@Override
	public String testMethod(Map map, ProtocolConfig config) {
		return "DemoServiceStub";
	}

}
