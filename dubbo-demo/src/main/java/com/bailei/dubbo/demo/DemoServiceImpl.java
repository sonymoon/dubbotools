/**
 * 
 */
package com.bailei.dubbo.demo;

import java.util.Map;

import com.alibaba.dubbo.config.ProtocolConfig;

/**
 * @author bailei
 *
 */
public class DemoServiceImpl implements DemoService {

	private String privateString;
	
	public String publicString;
	
	protected String protectedString;
	
	
	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public String testMethod(Map map, ProtocolConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrivateString() {
		return privateString;
	}

	public void setPrivateString(String privateString) {
		this.privateString = privateString;
	}

	public String getPublicString() {
		return publicString;
	}

	public void setPublicString(String publicString) {
		this.publicString = publicString;
	}

	public String getProtectedString() {
		return protectedString;
	}

	public void setProtectedString(String protectedString) {
		this.protectedString = protectedString;
	}

	
}
