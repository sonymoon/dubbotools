package com.bailei.dubbo.demo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.cluster.Cluster;

public class Test {

	public static void main(String[] args) {
		ExtensionLoader<Cluster> loader ;
	}

	
	public <T> String validateParam(T t){
		t.getClass().equals(null);
		return null;
	}
}
