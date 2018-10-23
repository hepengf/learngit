package com.congoal.cert.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.RegexUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.congoal.cert.pojo.Resource;
import com.congoal.cert.pojo.Role;
import com.congoal.cert.service.ResourceService;
import com.congoal.cert.utils.SQL;

/**
 * 1.加载权限与资源的对应关系
 *  2.根据url取得对应的权限
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource,InitializingBean  {
	private Logger logger = Logger.getLogger(MySecurityMetadataSource.class);
	@Autowired
	private ResourceService resourceService;
	
	private UrlMatcher urlMatcher;   
    private boolean useAntPath = true;   
    public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	public void setLowercaseComparisons(boolean lowercaseComparisons) {
		this.lowercaseComparisons = lowercaseComparisons;
	}

	private boolean lowercaseComparisons = true;   

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
		
	}

	// 存放资源与对应权限的，Map<资源(url),角色集合>
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MySecurityMetadataSource() {

	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		loadResource();
		return null;
	}

	// 系统启动时加载资源以及资源与权限的关系
	public void loadResource() {
		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MySecurityMetadataSource loadResource>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			//String hql = "from Resource";
			String hql = SQL.Resource.FINDALLRESOURCES;
			
			List<Resource> resourceList = (List<Resource>) this.resourceService
					.findAll(hql);
			for (Resource resource : resourceList) {
				// 给资源配置权限
				// resource.setRoles(resourceMapper.resourceFillRole(resource.getId()));
				Set<Role> roleList = resource.getRoles();
				System.out.println(resource.getValue()+"  >>>>>>>>>>>>>>>>  "+resource.getRoleString());
				
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				for (Role role : roleList) {
					ConfigAttribute configAttribute = new SecurityConfig(role.getName());
					configAttributes.add(configAttribute);
				}
				resourceMap.put(resource.getValue(), configAttributes);
			}

		}
	}

//	// 返回要进行验证的资源所拥有的权限集合
//	public Collection<ConfigAttribute> getAttributes(Object obj)
//			throws IllegalArgumentException {
//		// 用户所请求访问的链接
//		String requestUrl = ((FilterInvocation) obj).getRequestUrl();
//		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MySecurityMetadataSource requestUrl is >>>>>>>>>>>>>>>> " + requestUrl);  
//		
//		if (resourceMap == null) {
//			loadResource();
//		}
//
//		return resourceMap.get(requestUrl);
//	}
	
	// 返回要进行验证的资源所拥有的权限集合
	public Collection<ConfigAttribute> getAttributes(Object obj)
	{
		FilterInvocation filterInvocation = (FilterInvocation) obj;   
        String requestURI = filterInvocation.getRequestUrl();   
  
		// 用户所请求访问的链接
		String requestUrl = ((FilterInvocation) obj).getRequestUrl();
//		System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<MySecurityMetadataSource requestUrl is >>>>>>>>>>>>>>>> " + requestUrl);  
	
        if (resourceMap == null) {
			loadResource();
		}
        	
        Iterator<String> ite = resourceMap.keySet().iterator();   
        while (ite.hasNext()) {   
            String resURL = ite.next();   
  
            // 比较资源定义中的URL和当前请求的URL   
            // resURL 资源定义中的URL   
            // requestURL 当前请求的URL   
            if (urlMatcher.pathMatchesUrl(resURL, requestURI)) {   
            	Collection<ConfigAttribute> atts = resourceMap.get(resURL);
                return atts;   
            }   
        }   
  
        return null;   
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.urlMatcher = new RegexUrlPathMatcher();

		if (useAntPath) {
			this.urlMatcher = new AntUrlPathMatcher();
		}
		//System.out.println("this.urlMatcher" + this.urlMatcher);
		if ("true".equals(lowercaseComparisons)) {
			if (!this.useAntPath) {
				((RegexUrlPathMatcher) this.urlMatcher)
						.setRequiresLowerCaseUrl(true);
			}
		} else if ("false".equals(lowercaseComparisons)) {
			if (this.useAntPath) {
				((AntUrlPathMatcher) this.urlMatcher)
						.setRequiresLowerCaseUrl(false);
			}
		}

	}

}
