<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title>角色管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 <script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
    <link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
    
    <script type="text/javascript">
    function errMeg()
    {
    	if('${errMeg}' != '')
    	{
    		alert('${errMeg}');
    	}
    }
    	window.onload=errMeg;
    </script>
	<script type="text/javascript">
		function addRole() {
			document.location.href="roleAddAction.action";
		}
		
		function deleteRole(obj) {
    		var roleid = obj;
    		var message = "系统提示：确定删除该角色吗?";
    		var url = "deleteroleAction.action?roleid="+roleid;
    		if (window.confirm(message)) {
    			$.post(url, {},function(data){
    						if(data.result == 'isUse')
						    {
						    	 alert("系统提示：删除失败，此角色已分配使用！");
						    }
						    if(data.result == 'true')
						    {
						    	 alert("系统提示删除成功！");
						    	 $('#myFormID').submit();
						    }
						    if(data.result == 'false')
						    {
						    	alert("系统提示：用户删除失败！");
						    }
						  },'json'); 
			}
    	}
    	
    	function disableRole(roleid,objthis) {
    		var roleId = roleid;
    		var self = objthis;
    		var text = self.innerHTML;
    		var message = "系统提示：";
    		var status;
    		if(text == "停用")
    		{
    			message = message + "确定要停用该角色?";
    			/* url = url+"&&flag=disable"; */
    			status=1;
    		}else
    		{
    			message = message + "确定要启用该角色?";
    			/* url = url+"&flag=enable"; */
    			status=0;
    		}
    		var url = "disableroleAction.action?roleid="+roleId;
    		if (window.confirm(message)) {
    			$.post(url, {status:status},function(data){
						    if(data.result == 'success')
						    {
						    	 if(text == "启用")
						    		{
						    			//启用
						    			self.innerHTML="停用";
						    			document.getElementById("status"+roleId).innerHTML="启用";
						    		}else
						    		{
						    			self.innerHTML="启用";
						    			document.getElementById("status"+roleId).innerHTML="停用";
						    		}
						    }
						  },'json'); 
			}
    	}
	</script>
  </head>
  
  <body>
  		<form action="findAllRoleListAction.action" method="post" id="myFormID" name="myForm">
  			<div class="widget widget-table">
				<div class="widget-content">
			  		<table  class="pn-ftable" border="0" cellpadding="10">
							<tr>
								<th style="text-align: center;font-size: 10pt;">角色名</th>
								<td class="pn-fcontent">
									<input type="text" name="name" value="${name}" id="name"/>
								</td>
								<th style="text-align: center;font-size: 10pt;">状态</th>
								<td class="pn-fcontent">
									<select name="status" id="status">
										<option selected="selected" value="-1">请选择</option>	
										<option value="0" <c:if test="${status==0}">selected="selected"</c:if>>启用</option>	
										<option value="1"  <c:if test="${status==1}">selected="selected"</c:if>>停用</option>				
									</select>
								</td>
							
							</tr>
						</table>
						<div class="widget-bottom">
							<input type="submit" class="btn btn-s-md btn-primary pull-right"
								value="搜索"/>&nbsp;
							<input class="btn btn-s-md btn-primary pull-right" value="新增角色" type="button" 
								onclick="javascript:addRole();"/>
						</div>
					</div>
				</div>		
			   <div class="separator line"></div>
					<div class="widget widget-table">
						<div class="widget-header">
							<i class="icon-th-list"></i>
							<h5>角色权限列表</h5>
						</div>
			    	<div class="widget-content widget-list" style="overflow: scroll;">
			   		 <table style="width: 100%"  class="table table-striped table-bordered">
			   		 	<tr>
				   		 	<th style="font-size: 10pt;">序号</th>
				   		 	<th style="font-size: 10pt;">角色名</th>
				   		 	<th style="font-size: 10pt;">状态</th>
				   		 	<th style="font-size: 10pt;">描述</th>
				   		 	<th style="font-size: 10pt;">创建日期</th>
				   		 	<th style="font-size: 10pt;">创建人</th>
				   		 	<th style="font-size: 10pt;">操作</th>
				   		</tr>
				   		<c:forEach items="${roleList}" var="role" varStatus="status">
				   			<tr>
						   		<td style="font-size: 10pt;text-align: center;">${status.count}</td>
						   		<td style="font-size: 10pt;">${role.name}</td>
						   		<td style="font-size: 10pt;text-align: center;">
						   		<span id="status${role.id}">
						   		 	<c:choose>
						   		 		<c:when test="${role.status==0}">
						   		 			启用
						   		 		</c:when>
						   		 		<c:otherwise>
						   		 			停用
						   		 		</c:otherwise>
						   		 	</c:choose>
					   		 	 </span>
						   		</td>
						   		<td style="font-size: 10pt;">
						   			${role.description}
						   		</td>
						   		<td style="font-size: 10pt;">
						   			<fmt:formatDate value="${role.createdate}" pattern="yyyy-MM-dd"/>
						   		</td>
						   		<td style="font-size: 10pt;">${role.operator}</td>
						   		<td style="font-size: 10pt;">
						   								   		 	<c:choose>
					   		 		<c:when test="${role.status==0}">
					   		 			<a id="disableA${role.id}" href="#" style="text-decoration: none;" 
					   		 				onclick="disableRole('${role.id}',this);">停用</a>&nbsp;&nbsp;
					   		 		</c:when>
					   		 		<c:otherwise>
					   		 			<a id="disableA${role.id}" href="#" style="text-decoration: none;" 
					   		 				onclick="disableRole('${role.id}',this);">启用</a>&nbsp;&nbsp;
					   		 		</c:otherwise>
					   		 	</c:choose>
							  		<a href="roleSetResourceCheckAction.action?roleId=${role.id}" style="text-decoration: none;">修改资源分配</a>&nbsp;&nbsp;
							  		<a href="#" style="text-decoration: none;"onclick="deleteRole('${role.id}');">删除</a>
						   		</td>
					   		 </tr>	
				   		</c:forEach>
			   		 </table>
			   		 <div class="widget-bottom">
			   		 	<jsp:include page="../include/pager.jsp"/>
			   		 </div>
			   	</div>
   		</div>
   	</form>
  </body>
</html>
