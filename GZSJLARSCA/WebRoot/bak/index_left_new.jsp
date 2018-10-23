<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
   	
<table  border="0" cellspacing="0" cellpadding="0"  id="lefttable" style="margin: 0px;">
       <tr>
			<td height="28" width="100%" align="center" valign="middle" background="web/img/left_title2.gif" >
				<span class="left_title_text">操作菜单</span>
			</td>
	   </tr>
	   <tr>
        <td bgcolor="#e4edf8">
		<div id="navigationDiv">
			<ul id="navigation" style="background-color: #e4edf8">
				
				<%
					Map<Integer,List<Map<String,Resource>>> groupMap = (Map<Integer,List<Map<String,Resource>>>)request.getAttribute("menus");
					Set<java.util.Map.Entry<Integer,List<Map<String,Resource>>>> sets = null;
				
					if(null != groupMap && groupMap.size() > 0)
					{
						List<Map<String,Resource>> rs = null;
						Map<String,Resource> menuMap = null;
						Set<java.util.Map.Entry<String,Resource>> menuSet = null;
						sets = groupMap.entrySet();
						String pid = "";
						Resource resource = null;
						int size = 0;
						int count = 1;
						for(java.util.Map.Entry<Integer,List<Map<String,Resource>>> e :sets)
						{
				%>
							<li >
								<%
									rs = e.getValue();
									if(rs != null)
									{
										size = rs.size();
										for(int i = 0 ; i < size; i ++)
										{
											menuMap = rs.get(i);
											menuSet = menuMap.entrySet();
											
											for(java.util.Map.Entry<String,Resource> menu : menuSet)
											{
												pid = menu.getKey();
												resource = menu.getValue();
											
											%>
														<ul style="width:100%;height: 35px;">
															<li style="text-align: left;width:100%;text-decoration: underline;">
																<a href="#"  onclick="redirect('<%=request.getContextPath()+resource.getValue() %>',this);return false;" resource="<%=resource.getName() %>">
																	<%=resource.getName() %>
																</a>
															</li>
														</ul>
											<% 
													
											}
										}
									}
								
								%>
							</li>
				<%		
							count=1;
						}
					}
					
					List<Map<String,Resource>> rs = null;
					Map<String,Resource> menuMap = null;
					Set<java.util.Map.Entry<String,Resource>> menuSet = null;
				%>
				
				</ul>
			</div>
		</td>
      </tr>
</table>