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
public interface DemoService {
	public String sayHello(String name);
	
	public String testMethod(Map map, ProtocolConfig config) ;
}
