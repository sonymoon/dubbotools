package com.bailei.dubbo.demo;

public class TestReturn {

	public static int testFinally1() {
		int result = 1;
		try {
			result = 2;
			return result;
		} catch (Exception e) {
			return 0;
		} finally {
			result = 3; // 不会影响到try中return返回的result，因为基本数据类型不会受影响。
			System.out.println("execute finally1");
		}
	}

	public static StringBuffer testFinally2() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("Hello");
			return sb;
		} catch (Exception e) {
			return null;
		} finally {
			sb.append(" World");// 不同于上面的基本数据类型，这里会最终影响到return的sb 结果返回是Hello
								// World
			System.out.println("execute finally2");
		}
	}

	public static void main(String[] args) {
		int retVal = testFinally1();
		System.out.println(retVal);
		StringBuffer retBuffer = testFinally2();
		System.out.println(retBuffer);
	}
}