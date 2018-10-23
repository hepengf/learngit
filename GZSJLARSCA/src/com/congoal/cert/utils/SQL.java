package com.congoal.cert.utils;

import org.apache.commons.lang.xwork.StringUtils;

/**
 * 配置sql
 * @author huang
 *
 */
public abstract class SQL {
	
	public final static String ENABLESTATUS = "0";//启用状态
	public final static String DISABLESTATUS = "0";//停用状态
	
	//商户表
	public static abstract class User{
		public final static String FINDALLUSERS= "from User where 1=1";
		public final static String FINDALLENABLEUSERS= "from User where disabled="+1;
		public final static String FINDALLDISABLEUSERS= "from User where disabled="+0;
		/**
		 * 根据用户名查找用户
		 * @param name
		 * @return
		 */
		public static String findUserByName(String name)
		{
			return StringUtils.isEmpty(name)?"":"from User where name='"+name+"'";
		}
		/**
		 * 根据ID查找唯一用户
		 * @param id
		 * @return
		 */
		public static String findUserById(String id)
		{
			return StringUtils.isEmpty(id)?"":"from User where id="+id;
		}
	}
	
	//角色表
	public static abstract class Role{
		public final static String FINDALLROLE = "from Role where 1=1";//查找所有角色
		public final static String FINDROLEBYID = "from Role where 1=1 and id=";//查找所有角色
		public final static String FINDALLROLES= "from Role where status="+ENABLESTATUS;
		/**
		 * 根据ID查找唯一角色
		 * @param id
		 */
		public static String findRoleById(String id)
		{
			return StringUtils.isEmpty(id)?"":"from Role where id="+id +" and status="+ENABLESTATUS;
		}
		
		/**
		 * 根据ID串查找角色
		 * @param id
		 */
		public static String findRoleByIds(String ids)
		{
			return StringUtils.isEmpty(ids)?"":"from Role where id in ("+ids+") status="+ENABLESTATUS;
		}
		
		/**
		 * 根据ID串查找非ID所属的角色
		 * @param id
		 */
		public static String findRoleExceptIds(String ids)
		{
			return StringUtils.isEmpty(ids)?"":"from Role where id not in ("+ids+") and status="+ENABLESTATUS;
		}
	}
	
	//资源表
	public static abstract class Resource{
		 public final static String FINDALLRESOURCES = "from Resource where status="+ENABLESTATUS;
		 
		 public static String findMenu(int pid)
		 {
			 return "from Resource where pid="+pid+" and status="+ENABLESTATUS+" order by ordernum asc";
			 
		 }
		 
		 public static String findLeftMenu(String type,int pid)
		 {
			 return StringUtils.isEmpty(type)?"":"from Resource where type='"+type+"' and pid="+pid+" and status="+ENABLESTATUS+" order by ordernum asc";
		 }
		 
		 public static String findNoHasSubResource(int pid)
		 {
			 return "";
		 }
		 
		 public final static String findResourceByIds(String idz) {
			
			return StringUtils.isEmpty(idz)?"":"from Resource where id in ("+idz+")";
		 }
		 
		 public static String findResourcesByIds(String idz) {
			 return StringUtils.isEmpty(idz)?"":"from Resource where id in ("+idz+")";
		 }
		 
		 public static String findResourceById(String id) {
			 return StringUtils.isEmpty(id)?"":"from Resource where id ="+id;
		 }
	 }
	
	//配置参数表
	public static abstract class Config{
		public final static String FINDALLCONFIGS = "from UnConfig where status="+ENABLESTATUS;
		
		public final static String TYPE_BASIC = "BASIC";
		public final static String SESSIONCOUNT = "SESSIONCOUNT";
		
		/**
		 * 根据分类，名称查找唯一配置
		 * @param id
		 */
		public static String findConfigByTypeAndName(String type,String name)
		{
			return StringUtils.isEmpty(type)||StringUtils.isEmpty(name)?"":"from UnConfig where type='"+type+"' and name='"+name+"' and status="+ENABLESTATUS;
		}
	}
	
}
