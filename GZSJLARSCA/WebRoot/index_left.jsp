<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
   	
<table  border="0" cellspacing="0" cellpadding="0"  id="lefttable" style="margin: 0px;">
       <tr>
			<td height="28" width="100%" align="center" valign="middle" background="web/img/left_title2.gif" >
				<span class="left_title_text" style="font-family:'Times New Roman'">操作菜单</span>
			</td>
	   </tr>
	   <tr>
        <td>
		<div id="navigationDiv">
			<ul id="navigation" style="height:100%;background-color: #e4edf8">
				
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
							<li>
								<%
									rs = e.getValue();
									if(rs != null)
									{
										size = rs.size();
										for(int i = 0 ; i < size; i ++)
										{
											menuMap = rs.get(i);
											menuSet = menuMap.entrySet();
											
											//System.err.println("=======size======="+size+"=========="+count);
											for(java.util.Map.Entry<String,Resource> menu : menuSet)
											{
												pid = menu.getKey();
												resource = menu.getValue();
												if("-1".equals(pid))
												{
													%>
														<a  class="head" href="#" style="vertical-align: middle;">
															<span style="vertical-align:middle;font-family:'Times New Roman';">
																<img src="<%=resource.getImgUrl() %>" style="border: 0px;position: relative;top:5px;" width="13" height="12">&nbsp;
																<span style="position: relative;top:3px;"><%=resource.getName() %></span>
															</span>
														</a>
													
													<% 
													
												}
												else
												{	
													if(count == 2)
													{
													int height=(size-1)*32;
													%>
														<ul style="text-align: left; max-height: <%=height%>px">
															<li style="text-align: left;height:32px;display:block;">
																<a name="left" style="height:100%" href="#"  onclick="redirect('<%=request.getContextPath()+resource.getValue() %>',this);return false;" resource="<%=resource.getName() %>">
																	<span style="vertical-align:middle;font-family:'Times New Roman';">
																		&nbsp;&nbsp;<img src="<%=resource.getImgUrl() %>" width="13" height="12" style="border: 0px;position: relative;top:5px;">
																		&nbsp;
																		<span style="position: relative;top:3px;"><%=resource.getName() %></span>
																	</span>
																</a>
															</li>
													<%	
														if(count == size)
														{
													%>	
														</ul>
													<% 
														}
													}else{
													%>
														<li style="text-align: left;height:32px;display:block;">
															<a name="left" style="height:100%" href="#" onclick="redirect('<%=request.getContextPath()+resource.getValue() %>',this);return false;" resource="<%=resource.getName() %>">
																<span style="vertical-align:middle;font-family:'Times New Roman';">
																	&nbsp;&nbsp;<img src="<%=resource.getImgUrl() %>" width="13" height="12" style="border: 0px;position: relative;top:5px;">
																	&nbsp;
																	<span style="position: relative;top:3px;"><%=resource.getName() %></span>
																</span>
															</a>
														</li>
														<%
														if(count == size)
														{
														%>
															</ul> 
														<%	
														}
														%>
													<%}
													
												}
												count++;
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