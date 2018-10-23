package com.congoal.cert.utils;

public abstract class StatusUtils {
	public final static String ENABLE = "0";
	public final static String UNKNOW = "未知";
	
	public final static String getEnableName(String enableCode)
	{
		if(ENABLE.equals(enableCode))
		{
			return "启用";
		}else
		{
			return UNKNOW;
		}
	}
}
