package com.congoal.cert.action;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.hql.ast.SqlASTFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.congoal.cert.pojo.Role;
import com.congoal.cert.pojo.User;
import com.congoal.cert.pojo.UserVo;
import com.congoal.cert.service.AdminService;
import com.congoal.cert.service.RoleService;
import com.congoal.cert.service.RolesService;
import com.congoal.cert.service.UserService;
import com.congoal.cert.utils.LoginUtils;
import com.congoal.cert.utils.SQL;

import common.Logger;
@SuppressWarnings("all" )
public class UserAdministration extends BaseAction {

	private Logger logger = Logger.getLogger(UserAdministration.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RolesService rolesService;
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 用户列表跳转
	 * 
	 * @return
	 */
	public String findAllUserList() {
		String hql = SQL.User.FINDALLUSERS;
		HttpServletRequest request = this.getRequest();
		
		String name = request.getParameter("fname");
		String fullname = request.getParameter("ffullname");
		String disabled = request.getParameter("disabled");
		
		logger.info("name: "+name);
		logger.info("fullname: "+fullname);
		logger.info("disabled: "+disabled);
		
		if (name!=null && !name.equals("")) {
			hql += " and name like '%"+name+"%'";
			
		}
		if (fullname!=null && !fullname.equals("")) {
			hql += " and fullname like '%"+fullname+"%'";
			
		}
		if (disabled!=null && !disabled.equals("")&&!disabled.equals("-1")) {
			hql += " and disabled="+disabled;
		}
		int totalSize = adminService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		hql+=" order by CREATEDATE desc";
		logger.info("hql -->"+pager.getCurrentPage());
		List<User> users = (List<User>) adminService.findAllUser(hql, pager);
		
		user=(User) this.getSession().get("user");
		this.getRequest().setAttribute("admin", user);
		request.setAttribute("userList", users);
		request.setAttribute("disabled", disabled);
		request.setAttribute("name", name);
		request.setAttribute("fullname", fullname);

		return SUCCESS;
	}

	/**
	 * 查询指定用户下所有角色
	 * @return
	 */
	public String userSetRoleCheck()
	{
		HttpServletRequest request = this.getRequest();
		
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");

		List<User> users = (List<User>)userService.findAll(SQL.User.findUserById(id));
		
		User user = users.get(0);
		
		List<Role> inUserRoleList =  new ArrayList<Role>(user.getRoles());//用户所具有的角色
		List<Role> inUserUsefulRoleList =  new ArrayList<Role>();//用户所具有的角色
		for(Role role:inUserRoleList)
		{
			long status=role.getStatus();
			if(status==(long)0)
			{
				inUserUsefulRoleList.add(role);
			}
		}
		
		//用户没有拥有的角色
		List<Role> notInUserRoleList = null;
		if(inUserUsefulRoleList == null || inUserUsefulRoleList.size()==0)
		{
			notInUserRoleList = (List<Role>)rolesService.findAll(SQL.Role.FINDALLROLES);
					/*findByPager(SQL.Role.FINDALLROLES, pager);*/
		}
		else
		{
			String[] ids = new String[inUserUsefulRoleList.size()];
			int i  = 0 ;
			for(Role r : inUserUsefulRoleList)
			{
				ids[i] = r.getId()+"";
			    i++;
			}
			
			String idStr = StringUtils.join(ids,",");
			
			notInUserRoleList =(List<Role>)rolesService.findAll(SQL.Role.findRoleExceptIds(idStr));
/*					(List<Role>)rolesService.
					findByPager(SQL.Role.findRoleExceptIds(idStr), pager);*/
		}
		request.setAttribute("inUserRoleList", inUserUsefulRoleList);
		request.setAttribute("notInUserRoleList", notInUserRoleList);
		request.setAttribute("userid", id);
		request.setAttribute("user", user);
		
		if(StringUtils.isNotEmpty(flag)&& StringUtils.equals(flag, "check"))
		{
			request.setAttribute("disabled", "true");
		}
		
		return "update";
	}
	
	/**
	 * 为指定用户分配角色
	 * @return
	 */
	public String userSetRole()
	{	
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password").trim();
		String cpassword = request.getParameter("cpassword").trim();
		String fullname = request.getParameter("fullname").trim();
		String engname = request.getParameter("engname").trim();
		String telephone = request.getParameter("telephone").trim();
		String phone = request.getParameter("phone").trim();
		String fax = request.getParameter("fax").trim();
		String address = request.getParameter("address").trim();
		String zipcode = request.getParameter("zipcode").trim();
		String orgid = request.getParameter("orgid").trim();
		String staffNo = request.getParameter("staffNo").trim();				
		List<User> users = (List<User>) this.userService.findAll("from User where id="+id); 
		User user=null;
		if (users.size() > 0) {
			user = users.get(0);
			user.setName(name.trim());
			if(password != null && !(password.trim().equals("")))
			{			
				user.setPassword(LoginUtils.createPassword(name.trim(), password.trim()));
			}
			user.setFullname(fullname.trim());
			user.setEngname(engname);
			user.setTelephone(telephone);
			user.setPhone(phone);
			user.setFax(fax);
			user.setAddress(address);
			user.setZipcode(zipcode);
			user.setOrgid(orgid);
			user.setStaffNo(staffNo);
			this.adminService.saveOrUpdate(user);
		} else {
			this.message = "不存在该用户!";
			this.getRequest().setAttribute("resultMsg", this.message);
			return ERROR;
		}
		String userRoleIds = request.getParameter("userRoleIds");
		String[] userRoleArray = null;
		
		if(null != userRoleIds && !"".equals(userRoleIds))
		{
			userRoleArray = userRoleIds.split(",");
			
			//先删除用户下原有角色
			user.setRoles(null);
			
			//再重新为用户分配角色
			if(null != userRoleArray && 0 < userRoleArray.length)
			{
				List<Role> roleList = null;
				Set<Role> roles = new HashSet();
				for(String roleId : userRoleArray)
				{
					roleList = (List<Role>)rolesService.
							findByPager(SQL.Role.findRoleById(roleId), pager);
					roles.add(roleList.get(0));
				}
				user.setRoles(roles);
			}
			
		}else
		{
			//先删除用户下原有角色
			user.setRoles(null);
		}
		
		
		
		userService.updateObj(user);
		this.message="操作成功！";
		return SUCCESS;
	}
	
	/**
	 * 跳转去新增用户界面
	 * @return
	 */
	public String addUserPage()
	{
		return SUCCESS;
	}
	
	public String personInfo() {
		user=(User) this.getSession().get("user");
		List<User> users = (List<User>) this.userService.findAll("from User where id="+user.getId());
		user=users.get(0); 
		this.getRequest().setAttribute("user", user);
		this.getRequest().setAttribute("resultMsg", "修改成功！");
		return SUCCESS;
	}
	/**
	 * 修改用户信息
	 * @return
	 */
	public String modUser() {
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password").trim();
		String cpassword = request.getParameter("cpassword").trim();
		String fullname = request.getParameter("fullname").trim();
		String engname = request.getParameter("engname").trim();
		String telephone = request.getParameter("telephone").trim();
		String phone = request.getParameter("phone").trim();
		String fax = request.getParameter("fax").trim();
		String address = request.getParameter("address").trim();
		String zipcode = request.getParameter("zipcode").trim();
		String orgid = request.getParameter("orgid").trim();
		String staffNo = request.getParameter("staffNo").trim();				
		List<User> users = (List<User>) this.userService.findAll("from User where id="+id); 
		if (users.size() > 0) {
			User user = users.get(0);
			user.setName(name.trim());
			if(password != null && !(password.trim().equals("")))
			{			
				user.setPassword(LoginUtils.createPassword(name.trim(), password.trim()));
			}
			user.setFullname(fullname.trim());
			user.setEngname(engname);
			user.setTelephone(telephone);
			user.setPhone(phone);
			user.setFax(fax);
			user.setAddress(address);
			user.setZipcode(zipcode);
			user.setOrgid(orgid);
			user.setStaffNo(staffNo);
			this.adminService.saveOrUpdate(user);
		} else {
			this.message = "不存在该用户!";
			this.getRequest().setAttribute("resultMsg", this.message);
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 新增用户
	 * @return
	 */
	public String addUser()
	{
		HttpServletRequest request = this.getRequest();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		String fullname = request.getParameter("fullname");
		String engname = request.getParameter("engname");
		String phone = request.getParameter("phone");
		String telephone = request.getParameter("telephone");
		String fax = request.getParameter("fax");
		String address = request.getParameter("address");
		String zipcode = request.getParameter("zipcode");
		String orgid = request.getParameter("orgid");
		String staffNo = request.getParameter("staffNo");
		
		if(name == null || name.trim().equals(""))
		{
			this.message="登陆名为空!";
			return ERROR;
		}
		if(password == null || password.trim().equals(""))
		{
			password="123456";
		}
		if(cpassword == null || cpassword.trim().equals(""))
		{
			cpassword="123456";
		}
		else
		{
			if(!password.trim().equals(cpassword.trim())){
				this.message="两次输入密码不一致!";
				return ERROR;
			} 
			
		}
		if(fullname == null || fullname.trim().equals(""))
		{
			this.message="真实姓名为空!";
			return ERROR;
		}
		
		User user = new User();
		user.setName(name.trim());
		user.setPassword(LoginUtils.createPassword(name.trim(), password.trim()));
		user.setFullname(fullname.trim());
		user.setEngname(engname);
		user.setPhone(phone);
		user.setTelephone(telephone);
		user.setFax(fax);
		user.setAddress(address);
		user.setZipcode(zipcode);
		user.setOrgid(orgid);
		user.setStaffNo(staffNo);
		
		//后台赋值
		user.setDisabled(1+"");
		user.setCreatedate(new Date());
		user.setDisabledcout(0);
		String hql = SQL.User.FINDALLUSERS;
		hql += " and name ='"+name+"'";
		List<User> users = (List<User>) adminService.findAllUser(hql, pager);
		if(users.size()==0)
		{			
			adminService.saveOrUpdate(user);
			this.message="添加成功！";
			return SUCCESS;
		}
		this.getRequest().setAttribute("newUser", user);
		this.message="用户名已存在!";
		return ERROR;
	}
	/**
	 * 作废用户
	 * @return
	 * @throws Exception 
	 */
	public void disableUser() throws Exception
	{
		PrintWriter writer = this.getResponse().getWriter();
		HttpServletRequest request = this.getRequest();
		String flag = request.getParameter("flag");//flag=enable
		String userid = request.getParameter("id");
		List<User> users = (List<User>)adminService.
				findByPager(SQL.User.findUserById(userid), pager);
		User user = users.get(0);
		String result = "false";
		
		if("disable".equals(flag))
		{
			user.setDisabled("0");
			user.setLastdisableddate(new Date());//最后一次停用日期
			Integer count = user.getDisabledcout();
			if(count == null || count == 0)
			{
				count = 1;
			}
			else
			{
				count++;
			}
			user.setDisabledcout(count);//停用次数
			
		}else if("enable".equals(flag))
		{
			user.setDisabled("1");
		}
		adminService.update(user);
		result = "true";
		
		writer.write("{\"result\":"+"\""+result+"\"}");
		writer.flush();
		writer.close();
		return;
	}
	/**
	 * 删除用户
	 * @throws IOException
	 */
	public void deleteUser() throws IOException {
		HttpServletRequest request = this.getRequest();
		String userid = request.getParameter("id");
		
		List<User> users = (List<User>) this.userService.findAll(SQL.User.findUserById(userid));
		User user = users.get(0);
		PrintWriter writer = this.getResponse().getWriter();
		String result = "";
		try {
			this.adminService.delete(user);
			result = "true";
		} catch (Exception e) {
			result = "false";
		}
		writer.write("{\"result\":"+"\""+result+"\"}");
		writer.flush();
		writer.close();
		return ;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public String info() {
		user=(User) this.getSession().get("user");
		List<User> users = (List<User>) this.userService.findAll("from User where id="+user.getId());
		user=users.get(0); 
		this.getRequest().setAttribute("user", user);
		return SUCCESS;
	}
	
}
