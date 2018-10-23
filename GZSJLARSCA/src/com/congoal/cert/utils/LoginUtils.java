package com.congoal.cert.utils;

import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class LoginUtils extends LogJob{
	public static Logger LOGGER = Logger.getLogger(LoginUtils.class);
	private static final String ENCRYPTION_FACTOR="WWW.HGSOFT.COM";

	private static final Class clazz  = LoginUtils.class;
	
	public final static String createPassword(String loginName,String password)
	{
		try {
			return MD5Coder.encodeMD5Hex(loginName+ENCRYPTION_FACTOR+password).toUpperCase();
		} catch (Exception e) {
			writeLog(e,LOGGER,clazz);
		}
		return null;
	}
	
	/**
	 * 
	 * @param loginName
	 * @param password 登陆密码
	 * @param originalPassword 原始密码
	 * @return
	 */
	public final static boolean verificationPassword(String loginName,String password,String originalPassword)
	{
		if(loginName == null || password==null||originalPassword==null)
		{
			LOGGER.error("登陆名或密码为空!");
			return false;
		}
		try {
			String newPassword = MD5Coder.encodeMD5Hex(loginName+ENCRYPTION_FACTOR+password);
			if(newPassword.toUpperCase().equals(originalPassword.toUpperCase()))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			writeLog(e,LOGGER,clazz);
		}
		return false;
	}
	
	public static void main(String[] args) {
		String loginName="json";
		String password="123456";
		
		System.out.println(createPassword(loginName,password));
	}
	
	
}
