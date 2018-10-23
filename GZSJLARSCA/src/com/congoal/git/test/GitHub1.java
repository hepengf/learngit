package com.congoal.git.test;

public class GitHub1 {

	/*出道即巅峰*/
	public String getLab(String name) {
		return getMac()+name+getRandom("123");
	}
	
	public String getMac() {
		return "Mac-";
	}
	
	public String getRandom(String seed) {
		return "-Random-seed:"+seed;
	}
}
