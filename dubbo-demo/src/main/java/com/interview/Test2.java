package com.interview;

public class Test2 {
	public static String reverse(String originStr) {
		if (originStr == null || originStr.length() <= 1)
			return originStr;
		return reverse(originStr.substring(1)) + originStr.charAt(0);
	}

	public static void main(String[] args) {
		System.out.println(reverse("bailei"));
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.reverse();
	}
}
