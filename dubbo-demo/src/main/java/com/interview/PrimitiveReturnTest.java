/**
 * 
 */
package com.interview;

/**
 * 测试int原型try finally返回的是啥
 * 
 * @author bailei
 *
 */
public class PrimitiveReturnTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(test(0));
		System.out.println(test(1));
		System.out.println(test(2));
	}

	public static int test(int n) {
		int i = 1;
		try {
			i = i / n;
			return i;
		} catch (Exception e) {
			i = 2;
			return i;
		} finally {
			i = 3;
		}
	}

}
