<%@ page language="java" import="java.util.*,com.congoal.cert.utils.*,com.congoal.cert.pojo.*" pageEncoding="utf-8"%>
<html>
  <head>
    <title>系统用户流量统计</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
    
    <script type="text/javascript">
    	function invalidateUser(obj)
    	{
    		var userid = obj;
    		var result = window.confirm("系统提示：确定要注销选中用户,此操作不能恢复!");
    		
    		if(result)
   			{
    			//提交post请求
    			var url = "<%=request.getContextPath() %>/web/invalidateUserAction.action";
    			
    			$.post(url, { "userid": userid },
    			  function(data){
    			   if(null != data)
    				{
    				   if(data.result == '0000000')
    					{
    					   alert("用户："+userid+"-注销成功");
							
    					   parent.document.getElementById("pageResult").src = '<%=request.getContextPath() %>/web/statistics.jsp';
    					   
    					}else
    					{
    						alert("注销失败，原因："+data.result);
    					}
    				   
    				}else
    				{
    					alert("注销失败，系统内部异常");
    				}
    			  },'json'); 
   			}
    	}
    </script>
    
  </head>
  
  <body>
     <div style="width:100%;text-align:center">
     	<table style="width:100%" border=1 >
     		<tr>
     			<th  style="font-size: 10pt;color: #004799;">序号</th>
     			<th  style="font-size: 10pt;color: #004799;">用户登录名</th>
     			<th  style="font-size: 10pt;color: #004799;">会话数</th>
     			<th  style="font-size: 10pt;color: #004799;">详细信息</th>
     			<th  style="font-size: 10pt;color: #004799;">操作</th>
     		</tr>
     	<%
     		Enumeration e = application.getAttributeNames();
     		int i = 0;
     		while(e.hasMoreElements())
     		{
     			String key = e.nextElement()+"";
     			String _key = "";
     			if(key.startsWith(UserDetailMap.PREFIX)&&key.length() != UserDetailMap.PREFIX.length())
     			{
     				i++;
     				Map<String, Object> userDetailMap =  (Map<String, Object>)application.getAttribute(key);
     				
     				int userCount = 0;
     				if(userDetailMap.get(UserDetailMap.USERCOUNT) == null)
     				{
     					userCount = 0 ;
     				}else
     				{
     				 	userCount = (Integer)userDetailMap.get(UserDetailMap.USERCOUNT);
     				}
     				
     				_key = key.replace(UserDetailMap.PREFIX, "");
     				
     				List<UserVo> userLoginList  =  (List<UserVo>)userDetailMap.get(UserDetailMap.LOGINTIME);
     				%>
     				<tr>
     					<td style="text-align:center;font-size: 10pt;"><%=i %></td>
     					<td style="text-align:center;font-size: 10pt;"><%=_key %></td>
     					<td style="text-align:center;font-size: 10pt;"> <%=userCount %></td>
     					<td style="text-align:left;font-size: 10pt;">
     					<%
     						if(null != userLoginList && 0 < userLoginList.size())
     						{
     							String sessionId = "";
     							String loginTime = "";
     							String logoutTime = "";
     							for(UserVo vo : userLoginList)
     							{
     								sessionId = vo.getSessionId() != null && !"".equals(vo.getSessionId())?vo.getSessionId():"";
     								loginTime = vo.getLoginTime() != null && !"".equals(vo.getLoginTime())?vo.getLoginTime():"";
     								logoutTime = vo.getLogoutTime() != null && !"".equals(vo.getLogoutTime())?vo.getLogoutTime():"";
     								
     								out.println("【SessionID】："+sessionId+",【登入时间】："+loginTime+",【登出时间】："+logoutTime+"<br/>");
     							}
     						}else
     						{
     							out.println("【Session 被销毁】");
     						}
     					%>&nbsp;
     					</td>
     					<td style="text-align:center;">
							<a href="#" style="text-decoration: none;font-size: 10pt;">查看用户详细信息</a>&nbsp;&nbsp;
							<a href="#"  onclick="invalidateUser('<%=_key %>');return false;" style="text-decoration: none;font-size: 10pt;">注销用户</a>&nbsp;
						</td>
     				</tr>
     				
     				
     				<% 
     			}
     			
     		}
     		
     	%>
     	</table>
     </div>
  </body>
</html>
