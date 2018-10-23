package com.congoal.cert.test;

import java.util.HashMap;

public class Stack {

	public static void main(String[] args) {
		String aa = "aa";
		String bb = "bb";
		HashMap<String, String> items = new HashMap<String, String>();
		items.put(aa, aa);
		items.put(bb, bb);
		
		System.out.println(items.get(aa));
	}
}
