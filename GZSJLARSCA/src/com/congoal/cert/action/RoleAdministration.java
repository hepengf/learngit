package com.congoal.cert.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.congoal.cert.pojo.Resource;
import com.congoal.cert.pojo.Role;
import com.congoal.cert.pojo.RoleResource;
import com.congoal.cert.pojo.User;
import com.congoal.cert.service.ModuleService;
import com.congoal.cert.service.ResourceService;
import com.congoal.cert.service.RoleResourceService;
import com.congoal.cert.service.RoleService;
import com.congoal.cert.service.RolesService;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.SQL;

import common.Logger;
@SuppressWarnings("all" )
public class RoleAdministration extends BaseAction {

	private Logger logger = Logger.getLogger(RoleAdministration.class);
	
	@javax.annotation.Resource
	private RolesService rolesService;
//	private RoleService roleService;
	
	@javax.annotation.Resource
	private ModuleService moduleService;
	
	@javax.annotation.Resource
	private RoleResourceService roleResourceService;
//	private ResourceService resourceService;

//	public void setRoleService(RoleService roleService) {
//		this.roleService = roleService;
//	}
//	
//	public void setResourceService(ResourceService resourceService) {
//		this.resourceService = resourceService;
//	}

	private List<Resource> checkResourceList;// 专门用于判断是否有子节点的resource列表（包含所有resource的列表）

	public List<Resource> getCheckResourceList() {
		return checkResourceList;
	}

	public void setCheckResourceList(List<Resource> checkResourceList) {
		this.checkResourceList = checkResourceList;
	}
	
	private List<Resource> roleOwnResourceList;// 角色拥有的资源列表

	public List<Resource> getRoleOwnResourceList() {
		return roleOwnResourceList;
	}

	public void setRoleOwnResourceList(List<Resource> roleOwnResourceList) {
		this.roleOwnResourceList = roleOwnResourceList;
	}
	/**
	 * 用户列表跳转
	 * 
	 * @return
	 */
	public String findAllRoleList() {
		String hql = SQL.Role.FINDALLROLE;
		HttpServletRequest request = this.getRequest();
		
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		
		if (null!=name && !name.equals("")) {
			hql += " and name like '%"+name+"%'";
		}
		if (null!=status && !status.equals("") && !status.equals("-1")) {
			hql += " and status ="+status;
		}
		int totalSize = rolesService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		hql+=" order by CREATEDATE desc";
		List<Role> roles = (List<Role>) rolesService.findAllRoles(hql, pager);
		
		request.setAttribute("name", name);
		request.setAttribute("status", status);
		request.setAttribute("roleList", roles);

		return SUCCESS;
	}
	
	/**
	 * 删除角色
	 * @throws IOException
	 */
	public void deleteRole() throws IOException {
		String hqlRole = SQL.Role.FINDROLEBYID;
		HttpServletRequest request = this.getRequest();
		String roleid = request.getParameter("roleid");
		hqlRole+=roleid;
		System.out.println(hqlRole);
		List<Role> roles = (List<Role>) rolesService.findAll(hqlRole);
		Role role = roles.get(0);
		List<RoleResource> rr = this.roleResourceService.findWithSql("select * from s_role_resource rr where rr.role_id="+role.getId());
		PrintWriter writer = this.getResponse().getWriter();
		logger.info("rr.size: "+rr.size());
		String result = "";
		if(rr.size()>0)
		{
			result="isUse";
		}else {			
			try {
				this.rolesService.delete(role);
				result = "true";
			} catch (Exception e) {
				e.printStackTrace();
				result = "false";
			}
		}
		writer.write("{\"result\":"+"\""+result+"\"}");
		writer.flush();
		writer.close();
		return ;
	}
	
	/**
	 * 启用或停用角色
	 * @throws IOException
	 */
	public void disablerole() throws IOException {
		String hql = SQL.Role.FINDROLEBYID;
		HttpServletRequest request = this.getRequest();
		String roleid = request.getParameter("roleid");
		String status = request.getParameter("status");
		hql+=roleid;
		List<Role> roles = (List<Role>) rolesService.findAll(hql);
		Role role = roles.get(0);
		PrintWriter writer = this.getResponse().getWriter();
		String result = "";
		try {
			if(status!=null&&"0".equals(status))
			{
				role.setStatus((long)0);
			}
			else if(status!=null&&"1".equals(status))
			{
				role.setStatus((long)1);
			}
			result = "success";
			rolesService.update(role);
		} catch (Exception e) {
			result = "false";
		}
		writer.write("{\"result\":"+"\""+result+"\"}");
		writer.flush();
		writer.close();
		return ;
	}
	/**
	 * 角色设置资源
	 * @return
	 */
	public String roleSetResourceCheck(){
		String roleId = this.getRequest().getParameter("roleId");
		String hql = SQL.Role.FINDROLEBYID;
		hql+=roleId;
		List<Role> roles = (List<Role>) rolesService.findAll(hql);
		String errMeg="";
		if(roles.get(0).getStatus()==1)
		{
			this.getRequest().setAttribute("errMeg", errMeg+"该角色已停用！");
			return ERROR;
		}
		this.getRequest().setAttribute("roleId", roleId);
		this.getRequest().setAttribute("errMeg", errMeg);
		return SUCCESS;
	}

	/**
	 * 加载资源xml，用于界面层tree的显示
	 * 
	 * @return
	 */
	public String getResourceXML() throws Exception {
		String roleId = this.getRequest().getParameter("roleId");
		if (null != roleId) {
			//获得role所拥有的resource列表
			//select * from ls_resource where id in (select resource_id from ls_role_resource where role_id=#{role_id})
			String hql = SQL.Role.findRoleById(roleId);
			List<Role> roles =  (List<Role>)this.rolesService.findAll(hql);
			if(null != roles && roles.size()>0)
			{
				this.roleOwnResourceList = new ArrayList<Resource>(roles.get(0).getResources());
				// dom4j
				Document document = DocumentHelper.createDocument();
				// 创建总根节点
				Element root = document.addElement("tree");
				root.addAttribute("id", "0");

				// 取得数据库中resource并通过递归生成树
				
				//select * from ls_resource where pid=#{pid}
				// 先取得所有resource的根
				hql = SQL.Resource.findLeftMenu("LEFT", -1);
				List<Resource> rootResourceList = (List<Resource>)this.moduleService.findAll(hql);
				
				// 遍历所有resource根节点通过递归填充每一个根节点的子节点
				for (Resource resource : rootResourceList) {
					Element item = root.addElement("item");
					
					item.addAttribute("text", resource.getName());// text表示显示的文本
					item.addAttribute("id", String.valueOf(resource.getId())); // 当前节点的id(值)
					// 节点在页面中显示时需要的图片
					item.addAttribute("im0", "folderClosed.gif");
					item.addAttribute("im1", "folderOpen.gif");
					item.addAttribute("im2", "folderClosed.gif");
					
					//只有role拥有的并且本身节点不包含子节点的节点才能够被选中
					if(noHasSubResource(resource) && isOwnResource(resource)){
						item.addAttribute("checked", "checked");
					}
					
					item = getSubElement(roleId, item, resource);
				}
				// 创建根节点

				String content = document.asXML();// 通过asXML方法返回xml字符串
				//System.out.println(content);
				// 把xml传送到客户端
				HttpServletResponse response = this.getResponse();
				// 声明输出的字符串是以xml格式进行输出的
				response.setContentType("text/xml;charset=UTF-8");
				response.setHeader("cache-Control", "no-cache");
				PrintWriter pw = response.getWriter();
				pw.write(content);
				pw.flush();
				pw.close();
				// 由于直接输出的是xml格式的数据流，所以无需返回任何页面，留在原界面即可,所以返回null
			}
		} else {
			// dom4j
			Document document = DocumentHelper.createDocument();
			// 创建总根节点
			Element root = document.addElement("tree");
			root.addAttribute("id", "0");

			// 取得数据库中resource并通过递归生成树
			
			//select * from ls_resource where pid=#{pid}
			// 先取得所有resource的根
			String hql = SQL.Resource.findLeftMenu("LEFT", -1);
			List<Resource> rootResourceList = (List<Resource>)this.moduleService.findAll(hql);
			
			// 遍历所有resource根节点通过递归填充每一个根节点的子节点
			for (Resource resource : rootResourceList) {
				Element item = root.addElement("item");
				
				item.addAttribute("text", resource.getName());// text表示显示的文本
				item.addAttribute("id", String.valueOf(resource.getId())); // 当前节点的id(值)
				// 节点在页面中显示时需要的图片
				item.addAttribute("im0", "folderClosed.gif");
				item.addAttribute("im1", "folderOpen.gif");
				item.addAttribute("im2", "folderClosed.gif");
				
				//只有role拥有的并且本身节点不包含子节点的节点才能够被选中
				item = getSubElement(roleId, item, resource);
			}
			// 创建根节点

			String content = document.asXML();// 通过asXML方法返回xml字符串
			//System.out.println(content);
			// 把xml传送到客户端
			HttpServletResponse response = this.getResponse();
			// 声明输出的字符串是以xml格式进行输出的
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.write(content);
			pw.flush();
			pw.close();
		}
		
		return null;
	}
	
	/**
	 * 用于递归resource子节点的方法
	 * 
	 * @param item
	 * @param resource
	 *            参数中的resource第一次调用都是根节点
	 * @return
	 */
	public Element getSubElement(String roleId, Element item, Resource resource) {
		// 取得resource的所有子节点
		String hql = SQL.Resource.findMenu(resource.getId());
		List<Resource> subList = (List<Resource>)this.moduleService.findAll(hql);
		
		// 如果有子节点
		if (subList != null && subList.size() > 0) {
			for (Resource subResource : subList) {
				Element subItem = item.addElement("item");
				subItem.addAttribute("text", subResource.getName());// text表示显示的文本
				subItem.addAttribute("id", String.valueOf(subResource.getId())); // 当前节点的id(值)
				// 节点在页面中显示时需要的图片
				subItem.addAttribute("im0", "folderClosed.gif");
				subItem.addAttribute("im1", "folderOpen.gif");
				subItem.addAttribute("im2", "folderClosed.gif");
				
				if (null != roleId) {
					if(noHasSubResource(subResource) && isOwnResource(subResource)){
						subItem.addAttribute("checked", "checked");
					}
				}
				
				subItem = getSubElement(roleId, subItem, subResource);
			}
		}

		return item;
	}
	
	/**
	 * 检测当前节点是否有子节点
	 * 
	 * @param resource
	 * @return
	 */
	public boolean noHasSubResource(Resource resource) {
		String hql = SQL.Resource.findMenu(resource.getId());
		List<Resource> resources = (List<Resource>)this.moduleService.findAll(hql);
		
		if(null == resources || 0== resources.size())
		{
			return true;
		}else
		{
			return false;
		}
		
	}

	/**
	 * 判断传递进来的resource是不是role拥有的
	 * 
	 * @param resource
	 * @return
	 */
	public boolean isOwnResource(Resource resource) {
		
		//验证是否是拥有的resource
		for(Resource r : roleOwnResourceList){
			if((int)r.getId() == (int)resource.getId()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 更新权限资源
	 * @return
	 */
	public String roleSetCheckedResourceChecked() {
		HttpServletRequest request = this.getRequest();
		String roleId = request.getParameter("roleId");
		logger.info("roleId------->"+roleId);
		String resourceValues = request.getParameter("resourceValues");
		logger.info("resources: "+resourceValues);
		String hql = SQL.Resource.findResourceByIds(resourceValues);
		String role_hql = SQL.Role.findRoleById(roleId);
		
		Set<Resource> resources = new HashSet<Resource>();
		List<Resource> lresource = (List<Resource>) this.moduleService.findAll(hql);
		logger.info("lresouce.size: "+lresource.size());
		for (Resource rs : lresource) {
			resources.add(rs);
		}
		
		List<Role> roles = (List<Role>)this.rolesService.findAll(role_hql);
		
		if (null != roles && roles.size() > 0) {
			Role role = roles.get(0);
			role.setResources(resources);
			this.rolesService.saveOrUpdate(role);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转到系统角色页面
	 * @return
	 */
	public String addRole() {
		return SUCCESS;
	}
	/**
	 * 新增系统角色
	 * @return
	 */
	public String addSystemRole() {
		HttpServletRequest request = this.getRequest();
		String name = request.getParameter("rolename");
		String description = request.getParameter("description");
		String resourceValues = request.getParameter("resourceValues");
		String hql = SQL.Resource.findResourcesByIds(resourceValues);
		
		String rhql=SQL.Role.FINDALLROLE+" and NAME="+"'"+name+"'";
		User user = (User) request.getSession().getAttribute("user");
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		role.setOperator(user.getName());
		role.setStatus(0l);
		role.setCreatedate(new Date());
		List<Role> roles = (List<Role>) rolesService.findAllRoles(rhql, pager);
		//存储role
		if(roles.size()>0)
		{
			this.message="已存在角色名！";
			this.request.setAttribute("rolename", name);
			this.request.setAttribute("description", description);
			return "error";
		}
		this.rolesService.save(role);
		
		if (!hql.equals("")) {
			List<Resource> rs = (List<Resource>) this.moduleService.findAll(hql);
			Set<Resource> resources = new HashSet<Resource>();
			for (Resource r : rs) {
				resources.add(r);
			}
			
			//设置角色拥有的资源权限
			role.setResources(resources);
			//保存到数据库中
			this.rolesService.update(role);
		}
		
		return SUCCESS;
	}
}
