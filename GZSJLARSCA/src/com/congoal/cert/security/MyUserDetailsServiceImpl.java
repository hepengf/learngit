package com.congoal.cert.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.congoal.cert.pojo.User;
import com.congoal.cert.service.RoleService;
import com.congoal.cert.service.UserService;
import com.congoal.cert.utils.SQL;
@SuppressWarnings("all")
public class MyUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 用户登录成功后，给用户授权
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
//		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MyUserDetailsServiceImpl loadUserByUsername>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		User user = new User();
		try {
			user.setName(userName);
			//String hql = "from User where name='" + user.getName() + "'";
			String hql = SQL.User.findUserByName(user.getName());
			
			
			List<User> users = (List<User>) this.userService.findAll(hql);
			if (!users.isEmpty()) {
				user = users.get(0);
				
				// 判断用户是否有超级管理员权限  资源中存在/*则为超级管理员
				//mod by hcx
//				boolean supper = false;
//				for(Role role : user.getRoles())
//				{
//					for(Resource resource : role.getResources())
//					{
//						if(StringUtils.equalsIgnoreCase(resource.getType(), "URL")&& StringUtils.equals(resource.getValue(), "/*"))
//						{
//							supper = true;
//							break;
//						}
//					}
//				}
//				if(supper)
//				{
//					List<Role> roles = (List<Role>)roleService.findAll(SQL.Role.FINDALLROLES);
//					
//					if(null != roles && 0 < roles.size())
//					{
//						for(Role role : roles)
//						{
//							user.getRoles().add(role);
//						}
//					}
//				}
//				
//				System.err.println(user.getName() + "    >>>>>>>>>>>>>>>>>>  "
//						+ user.getRolesString());
				//mod by hcx
				
			} else {
				throw new UsernameNotFoundException("用户" + userName + "-不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
