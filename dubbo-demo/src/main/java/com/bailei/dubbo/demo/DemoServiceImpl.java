/**
 * 
 */
package com.bailei.dubbo.demo;

/**
 * @author bailei
 *
 */
public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		return "Hello " + name;
	}

}
