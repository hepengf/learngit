package com.congoal.cert.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.congoal.cert.pojo.Resource;
import com.congoal.cert.pojo.RoleResource;
import com.congoal.cert.service.ModuleService;
import com.congoal.cert.service.ResourceService;
import com.congoal.cert.service.RoleResourceService;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.SQL;

import common.Logger;
@SuppressWarnings("all" )
public class ResourceAdministration extends BaseAction {
	
	private final Logger logger = Logger.getLogger(ResourceAdministration.class);
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private RoleResourceService roleResourceService;
//	private ResourceService resourceService;

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setRoleResourceService(RoleResourceService roleResourceService) {
		this.roleResourceService = roleResourceService;
	}
//	public void setResourceService(ResourceService resourceService) {
//		this.resourceService = resourceService;
//	}



	/**
	 * 资源列表跳转
	 * 
	 * @return
	 */
	public String findAllResourceList() {
		String hql = SQL.Resource.FINDALLRESOURCES;
		List<Resource> resources = (List<Resource>) moduleService.findAllResources(hql, pager);

		this.getRequest().setAttribute("resourceList", resources);

		return SUCCESS;
	}
	/**
	 * 删除资源
	 * @return
	 */
	public String deleteResource() {
		HttpServletRequest request = this.getRequest();
		String resourceId = request.getParameter("resourceId");
		
		logger.info("resourceId: "+resourceId);
		String hql = SQL.Resource.findResourceById(resourceId);
		
		List<Resource> res = (List<Resource>) moduleService.findAll(hql);
		if (null != res && res.size() > 0) {
			Resource resource = res.get(0);
			
			//干掉与该资源关联的role_resource表中的数据
			String sql = "from s_role_resource where resource_id="+resource.getId();
			List<RoleResource> rr = (List<RoleResource>) roleResourceService.findAll(sql);
			if (null!=rr && rr.size() > 0) {
				for (int i = 0; i < rr.size(); i++) {
					RoleResource rr0 = rr.get(0);
					logger.info("del---->rs_id"+rr0.getResource_id());
					moduleService.delete(rr0);
				}
			}
			
			
			//如果有子节点，干掉子节点
			hql = SQL.Resource.findMenu(resource.getId());
			List<Resource> subRes = (List<Resource>) moduleService.findAll(hql);
			if (null != subRes && subRes.size() > 0) {//如果该节点有子节点，先干掉子节点
				for (int i = 0; i < subRes.size(); i++) {
					Resource res0 = subRes.get(i);
					sql = "from s_role_resource where resource_id="+res0.getId();
					List<RoleResource> rr1 = (List<RoleResource>) roleResourceService.findAll(sql);
					if (null!=rr1 && rr1.size() > 0) {
						for (int j = 0; j < rr1.size(); j++) {
							logger.info("subRes-->role_id"+rr1.get(i).getRole_id());
//							resourceService.delObj(rr1.get(i));
							moduleService.delete(rr1.get(i));
						}
					}
//					resourceService.delObj(res0);
					moduleService.delete(res0);
				}
			}
			
//			resourceService.delObj(resource);
			moduleService.delete(resource);
		}
		
		return SUCCESS;
	}

}
