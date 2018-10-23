package com.congoal.cert.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.congoal.cert.pojo.Resource;
import com.congoal.cert.pojo.Role;
import com.congoal.cert.pojo.UnConfig;
import com.congoal.cert.pojo.User;
import com.congoal.cert.pojo.UserVo;
import com.congoal.cert.service.UserService;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.JsonObject;
import com.congoal.cert.utils.SQL;
import com.congoal.cert.utils.SimpleDateFormatUtils;
import com.congoal.cert.utils.UserDetailMap;
import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@SuppressWarnings("all")
public class LoginAction extends BaseAction implements ModelDriven<JsonObject> {
	
	private static final long serialVersionUID = -1422658390562046220L;
	@Autowired
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private User user;
	private JsonObject jsonObj = new JsonObject();
	private String Message;
	
	private List<Map<String,Resource>> menus;
	
	private final static String SUCCESSCODE = "0000000";
	public final static String SESSION_ATTRIBUTE = "user";
	
	

	// @Autowired
	// private SessionRegistry sessionRegistry;
	//
	// public SessionRegistry getSessionRegistry() {
	// return sessionRegistry;
	// }
	//
	// public void setSessionRegistry(SessionRegistry sessionRegistry) {
	// this.sessionRegistry = sessionRegistry;
	// }

	

	
	/**
	 * 把用户设置到session
	 * @param uv
	 * @param loginTime
	 */
	private void deployUser(UserVo uv,String loginTime)
	{
		// 把登陆后的用户放入session中
		List<Map<String, UserVo>> userLists = null;
		Map<String, UserVo> userMap = null;
		if (this.getRequest().getSession().getAttribute(SESSION_ATTRIBUTE) != null) {
			userLists = (List<Map<String, UserVo>>) this.getRequest()
					.getSession().getAttribute(SESSION_ATTRIBUTE);
		} else {
			userLists = new ArrayList<Map<String, UserVo>>();
		}

		userMap = new HashMap<String, UserVo>();
		userMap.put(loginTime, uv);
		userLists.add(userMap);
		
		this.getRequest().getSession().setAttribute(SESSION_ATTRIBUTE, userLists);
		
		
		// 把用户登录次数放入application中
		Map<String, Object> userDetailMap = null;
		List<UserVo> userLoginList = null;

		if (this.getRequest().getSession().getServletContext()
				.getAttribute(UserDetailMap.PREFIX + user.getName()) != null) {

			userDetailMap = (Map<String, Object>) this.getRequest()
					.getSession().getServletContext()
					.getAttribute(UserDetailMap.PREFIX + user.getName());
			
			int userCount = 0;
			if(userDetailMap
					.get(UserDetailMap.USERCOUNT) != null)
			{
				userCount = (Integer) userDetailMap.get(UserDetailMap.USERCOUNT);
			}
			
			userDetailMap.put(UserDetailMap.USERCOUNT, ++userCount);// 用户会话数

			userLoginList = (List<UserVo>) userDetailMap.get(UserDetailMap.LOGINTIME);

		} else {
			userDetailMap = new HashMap<String, Object>();
			userDetailMap.put(UserDetailMap.USERCOUNT, 1);// 用户会话数

			userLoginList = new ArrayList<UserVo>();

		}
		
		if(userLoginList == null)
			userLoginList = new ArrayList<UserVo>();
		userLoginList.add(uv);

		userDetailMap.put(UserDetailMap.LOGINTIME, userLoginList);// 用户登录时间

		this.getRequest()
				.getSession()
				.getServletContext()
				.setAttribute(UserDetailMap.PREFIX + user.getName(),
						userDetailMap);
	}
	
	/**
	 * 检查当前用户会话数
	 * @return
	 */
	private String checkSession(User user){
		String hql = SQL.Config.findConfigByTypeAndName(SQL.Config.TYPE_BASIC, SQL.Config.SESSIONCOUNT);
		
		List<UnConfig> configs = (List<UnConfig>)userService.findAll(hql);
		
		String Message = SUCCESSCODE;
		
		if(null != configs && 0 < configs.size())
		{
			
			UnConfig config = configs.get(0);
			String _sessionLimit = config.getValue();
			int sessionLimit = 1;
			
			try{
				sessionLimit = Integer.parseInt(_sessionLimit);
			}catch(Exception e)
			{
				return Message;
			}
			
			// 把用户登录次数放入application中
			Map<String, Object> userDetailMap = null;

			if (this.getRequest().getSession().getServletContext()
					.getAttribute(UserDetailMap.PREFIX + user.getName()) != null) {

				userDetailMap = (Map<String, Object>) this.getRequest()
						.getSession().getServletContext()
						.getAttribute(UserDetailMap.PREFIX + user.getName());

				int userCount = 0;
				
				if(userDetailMap.get(UserDetailMap.USERCOUNT) != null)
				{
					userCount = (Integer) userDetailMap.get(UserDetailMap.USERCOUNT);
				}
				
				if(userCount+1 > sessionLimit)
				{
					Message = "用户当前会话数已超出限制数";
				}
			} 
		}
		
		return Message;
	}
	
	// SecurityContextHolder.clearContext();
	//而把SecurityContextHolder清空，所以会得到null。
	/**
	 * 登录成功后执行
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {
		// 取得当前用户实例
		// this.user = (User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SecurityContextImpl securityContextImpl = (SecurityContextImpl) this
				.getRequest().getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");

		WebAuthenticationDetails details = (WebAuthenticationDetails) securityContextImpl
				.getAuthentication().getDetails();
		
		//检查用户的sessionId是否为空
		if(StringUtils.isEmpty(details.getSessionId()) || StringUtils.isBlank(details.getSessionId()))
		{
			this.setMessage("sessionId");
			return "timeout";
		}
		
		//取得登录用户
		this.user = (User) securityContextImpl.getAuthentication().getPrincipal();
		
//		UserVo uv = new UserVo();
//		PropertyUtils.copyProperties(uv, user);// 属性复制
//
//		uv.setRemoteAddress(details.getRemoteAddress());//设置访问地址
//		uv.setSessionId(details.getSessionId());//设置sessionId
//		
//		//检查用户会话数
//		String checkSessionResult = this.checkSession(user);
//		
//		if(!SUCCESSCODE.equals(checkSessionResult))
//		{
//			this.setMessage("expired");
//			return "timeout";
//		}
//		//检查用户会话数
		
		this.getRequest().getSession().setAttribute("user", user);
		
//		String loginTime = SimpleDateFormatUtils.formatDateReturnYYYYMMDDHHMMSS(new Date());// 登录时间
//		uv.setLoginTime(loginTime);//设置登录时间
//
//		this.deployUser(uv,loginTime);
		
		// 登录名
		// System.out.println("Username:"
		// + securityContextImpl.getAuthentication().getName());

		// 登录密码，未加密的
		// System.out.println("Credentials:"
		// + securityContextImpl.getAuthentication().getCredentials());

		// WebAuthenticationDetails details = (WebAuthenticationDetails)
		// securityContextImpl
		// .getAuthentication().getDetails();

		// 获得访问地址
		// System.out.println("RemoteAddress:" + details.getRemoteAddress());

		// 获得sessionid
		// System.out.println("SessionId:" + details.getSessionId());

		// 获得当前用户所拥有的权限
		// List<GrantedAuthority> authorities = (List<GrantedAuthority>)
		// securityContextImpl
		// .getAuthentication().getAuthorities();

		// for (GrantedAuthority grantedAuthority : authorities) {
		// System.out.println("Authority:" + grantedAuthority.getAuthority());
		// }
		
		//生成左侧菜单
		this.getRequest().setAttribute("menus", this.createLeftMenu(user));
		
		//把登录时间回传至界面
//		this.getRequest().setAttribute("loginTime", loginTime);

		return SUCCESS;
	}
	
	/**
	 * 生成页面左侧表格
	 * @param maps
	 * @return
	 */
	/**private Map<Integer,String> checkPosition(List<Map<String,Resource>> maps)
	{
		if(maps == null || maps.size() == 0 )
		{
			return null;
		}
		Map<String,Resource> rs = null;
		Set<Entry<String,Resource>> rsSet = null;
		String pid = "";
		int position = 0;
		Map<Integer,String> pMap = new TreeMap<Integer,String>();//key为位置  value为 是否显示 0-不显示 1-显示
		
		List<String> filter =  new ArrayList<String>();
		for(int i = 0; i < maps.size();i++)
		{
			rs = maps.get(i);
			rsSet = rs.entrySet();
			for(Entry e : rsSet)
			{
				pid = e.getKey()+"";//父ID
				
				if("-1".equals(pid))
				{
					pMap.put(position,"0");
				}else
				{
					if("1".equals(pMap.get(position-1)))
						pMap.put(position,"0");
					else
						pMap.put(position,"1");
				}
				
				position++;
			}
		}
		return null;
	}
	*/
	/**
	 * 根据用户所据有的访问权限生成左侧菜单
	 * @param user
	 * @return
	 */
	private Map<Integer,List<Map<String,Resource>>>  createLeftMenu(User user)
	{
		Map<Integer,List<Map<String,Resource>>> groupMap = null;
		//取得登录用户下的所有角色
		Set<Role> roles = user.getRoles();
		
		if(null != roles && 0 < roles.size())
		{
			List<Resource> resources =  new ArrayList<Resource>();//查询出所有一级目录
			
			Set<Resource> sets = null;
			//?未考虑公共资源，即没有与角色绑定的资源
			for(Role role : roles)
			{
				sets = role.getResources();
				
				for(Resource rc : sets)
				{
					if("LEFT".equals(rc.getType())&&null != rc.getPid()&&-1 == (int)rc.getPid()&&role.getStatus()==(long)0)
					{
						resources.add(rc);
					}
				}
			}
			
			if(null != resources && 0 < resources.size())
			{
				

				Resource resource = null;
				Map<String,Resource> compMap = new HashMap<String,Resource>();
				//过滤因不同角色，但相同的资源
				for(int k = 0; k < resources.size();k++)
				{
					resource = resources.get(k);
					
					if(!compMap.containsKey(resource.getValue()))
					{
						compMap.put(resource.getId()+"", resource);
					}
				}
				
				Collection<Resource> compResources = compMap.values();
				Iterator<Resource> iterators = compResources.iterator();
				List<Resource> rootMenus =new ArrayList<Resource>();
				
				while(iterators.hasNext())
				{
					resource = iterators.next();//取出第一级目录
//					System.out.println("name: "+resource.getName());
					rootMenus.add(resource);
				}
				
				//排序
				Collections.sort(rootMenus, new Comparator<Resource>() {
					public int compare(Resource r0, Resource r1) {
						Integer i0 = 1;
						Integer i1 = 1;
						
						if(r0.getOrdernum()!=null)
							i0 = r0.getOrdernum();
						
						if(r1.getOrdernum()!=null)
							i1 = r1.getOrdernum();	
						
						return i0.compareTo(i1);
					 }
				});
				
				
				Map<String,Resource> maps = null;
				groupMap = new HashMap<Integer,List<Map<String,Resource>>>();
				
				int groupNo = 1;
				for(Resource rootMenu :rootMenus)
				{
					List<Map<String,Resource>> allList  = new ArrayList<Map<String,Resource>>();
					
					maps = new HashMap<String,Resource>();
					maps.put("-1", rootMenu);
					allList.add(maps);
					
					allList = this.findAllMenu(allList, rootMenu.getId()+"");
					
					groupMap.put(groupNo,allList);
					
					groupNo++;
				}
				
//				Set<Entry<Integer,List<Map<String,Resource>>>> groupSet = groupMap.entrySet();
//				String key = "";
//				List<Map<String,Resource>> mlist = null;
//				for(Entry<Integer,List<Map<String,Resource>>> entry:groupSet)
//				{
//					key = entry.getKey()+"";
//					mlist = entry.getValue();
//					System.out.println("groupNo: "+key);
//					
//					for(Map<String,Resource> rs : mlist)
//					{
//						Set<Entry<String,Resource>> s = rs.entrySet();
//						
//						for(Entry<String,Resource> ee: s)
//						{
//							System.err.println(ee.getKey()+" ==== "+ee.getValue().getName());
//						}
//					}
//				}
				
				
				
			}
			
		}
		
		return groupMap;
	}
	
	private List<Map<String,Resource>> findAllMenu(List<Map<String,Resource>> allList,String pid)
	{
		String hql = SQL.Resource.findLeftMenu("SUBLEFT", Integer.parseInt(pid));
		
		List<Resource> resources = (List<Resource>)userService.findAll(hql);
		
		Map<String,Resource> maps = null;
		if(null != resources && 0 < resources.size())
		{
			for(Resource resource : resources)
			{
				maps = new HashMap<String,Resource>();
				maps.put(pid, resource);
				allList.add(maps);
				
				allList = this.findAllMenu(allList, resource.getId()+"");//递归添加到集合中
			}
			
		}
		return allList;
	}

	public void logout() throws Exception {

		// 把用户登录次数放入application中
		Map<String, Object> userDetailMap = null;
		List<UserVo> userLogoutList = null;
		String loginTime = this.getRequest().getParameter("loginTime");
		List<Map<String, UserVo>> userList = (List<Map<String, UserVo>>) this.getSession().get(SESSION_ATTRIBUTE);

		// 从session中取出登录的用户
		UserVo user = null;

		if (null != userList && 0 < userList.size()) {
			for (Map<String, UserVo> userDetail : userList) {
				if (userDetail.get(loginTime) != null) {
					user = userDetail.get(loginTime);
					break;
				}
			}
		}

		if (user == null) {
			throw new Exception("用户非法进入系统");
		}

		int userCount = 0;
		if (this.getRequest().getSession().getServletContext()
				.getAttribute(UserDetailMap.PREFIX + user.getName()) != null) {

			userDetailMap = (Map<String, Object>) this.getRequest()
					.getSession().getServletContext()
					.getAttribute(UserDetailMap.PREFIX + user.getName());

			// 取出用户现有会话数
			userCount = (Integer) userDetailMap.get(UserDetailMap.USERCOUNT);

			--userCount;

			userDetailMap.put(UserDetailMap.USERCOUNT, userCount);

			if (userDetailMap.get(UserDetailMap.LOGINTIME) == null) {
				throw new Exception("系统异常");
			} else {
				userLogoutList = (List<UserVo>) userDetailMap
						.get(UserDetailMap.LOGINTIME);
			}

			// 设置用户登出时间
			for (UserVo uv : userLogoutList) {
				if (uv.getSessionId().equals(user.getSessionId())
						&& uv.getLoginTime().equals(user.getLoginTime())
						&& user.getLogoutTime() == null) {
					
					uv.setLogoutTime(SimpleDateFormatUtils
							.formatDateReturnYYYYMMDDHHMMSS(new Date()));
					
					break;
				}
			}

			userDetailMap.put(UserDetailMap.LOGINTIME, userLogoutList);
			// userDetailMap.put(UserDetailMap.LOGOUTTIME, userLogoutList);

			this.getRequest().getSession().getServletContext()
					.setAttribute(UserDetailMap.PREFIX, userDetailMap);

		} else {
			throw new Exception("用户非法进入系统");
		}

		// 把登陆后的用户放入session中
		if (userCount == 0) {
			this.getRequest().getSession().removeAttribute(SESSION_ATTRIBUTE);
			// 使得session失效
			this.getRequest().getSession().invalidate();
		}
		else
		{
			for (Map<String, UserVo> userDetail : userList) {
				if (userDetail.get(loginTime) != null) {
					userList.remove(userDetail);
					break;
				}
			}
			
			this.getRequest().getSession().setAttribute(SESSION_ATTRIBUTE, userList);
			
		}
		// return SUCCESS;
		
		return;
	}

	// public String statistics()
	// {
	// List<Object> users = sessionRegistry.getAllPrincipals();
	// return SUCCESS;
	// }
	
	/**
	 * 注销用户
	 * @return
	 * @throws IOException 
	 */
	public void invalidateUser() throws IOException
	{
		String userid = this.getRequest().getParameter("userid");//用户登录名
		String result = "";
		if(StringUtils.isEmpty(userid))
		{
			result = "传入参数-用户名为空";
			
		}else
		{
			Map<String, Object> userDetailMap = (Map<String, Object>)this.getRequest().getSession().getServletContext().getAttribute(UserDetailMap.PREFIX+userid.trim());
			
			 userDetailMap.remove(UserDetailMap.USERCOUNT); 
			 userDetailMap.remove(UserDetailMap.LOGINTIME);
			 
			 this.getRequest().getSession().getServletContext().setAttribute(UserDetailMap.PREFIX+userid.trim(),userDetailMap);
			
			result = SUCCESSCODE;
		}
		
		PrintWriter writer = this.getResponse().getWriter();
		
		writer.write("{result:\""+result+"\"}");
	}

	
	public JsonObject getModel() {
		return this.jsonObj;
	}
	
	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public JsonObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JsonObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Map<String, Resource>> getMenus() {
		return menus;
	}

	public void setMenus(List<Map<String, Resource>> menus) {
		this.menus = menus;
	}
	
	public static void main(String[] args) {
		
//		List<Resource> resources =  new ArrayList<Resource>();//查询出所有一级目录
//		
//		Resource resource1 = new Resource();
//		resource1.setOrdernum(1);
//		resource1.setName("resource1");
//		
//		Resource resource2 = new Resource();
//		resource2.setOrdernum(2);
//		resource2.setName("resource2");
//		
//		Resource resource3 = new Resource();
//		resource3.setOrdernum(3);
//		resource3.setName("resource3");
//		
//		resources.add(resource3);
//		resources.add(resource2);
//		resources.add(resource1);
//		
//		
//		Collections.sort(resources, new Comparator<Resource>() {
//			public int compare(Resource r0, Resource r1) {
//				Integer i0 = 1;
//				Integer i1 = 1;
//				
//				if(r0.getOrdernum()!=null)
//					i0 = r0.getOrdernum();
//				
//				if(r1.getOrdernum()!=null)
//					i1 = r1.getOrdernum();	
//				
//				return i0.compareTo(i1);
//			 }
//		});
//		
//		for(Resource resource:resources)
//		{
//			System.err.println(resource.getOrdernum() +"========="+resource.getName());
//		}
		
	}
	
}
