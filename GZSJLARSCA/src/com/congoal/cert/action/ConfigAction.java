package com.congoal.cert.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.congoal.cert.pojo.UnConfig;
import com.congoal.cert.service.ConfigService;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.SQL;
@SuppressWarnings("all" )
public class ConfigAction extends BasicAction{
	@Autowired
	private ConfigService configService;
	
	private String path;
	
	
	public String findAllConfig()
	{
		String hql = SQL.Config.FINDALLCONFIGS;
		
		List<UnConfig> configs = (List<UnConfig>)configService.findAll(hql);
		
		this.getRequest().setAttribute("configsList", configs);
		
		return SUCCESS;
	}
	
	/**
	 * 流量监控
	 * @return
	 */
	public String statistics()
	{
		this.setPath("/web/statistics.jsp");
		return SUCCESS;
	}
	
	/**
	 * 参数更新
	 * @return
	 * @throws IOException 
	 */
	public void updateConfig() throws IOException
	{
		String cvalue = this.getRequest().getParameter("cvalue");
		String cdesc = this.getRequest().getParameter("cdesc");
		String  cid = this.getRequest().getParameter("cid");
		
		String hql = "from UnConfig where id = "+cid;
		List<UnConfig> ls = (List<UnConfig>)configService.findAll(hql);
		String result = "0000000";
		HttpServletResponse rs = this.getResponse();
		PrintWriter pw = rs.getWriter();
		
		if(null != ls && ls.size() == 1)
		{
			UnConfig c = ls.get(0);
			
			c.setValue(cvalue);
			c.setDescription(cdesc);
			
			configService.updateObj(c);
			
		}else
		{
			result = "1111111";
		}
		pw.write("{result:'"+result+"'}");
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
