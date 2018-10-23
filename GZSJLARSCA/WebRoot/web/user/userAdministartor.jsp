<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
    <link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
 	<script type="text/javascript"> 
 		if('${message}'!= "")
 		{
 			alert('${message}');
 		}
 	
    	function disableUser(id,obj)
    	{
    		var userid = id;
    		var self = obj;
    		var text = self.innerHTML;
    		var url = "disableUserAction.action?id="+userid;
    		var message = "系统提示：";
    		var flag;
    		if(text == "停用")
    		{
    			message = message + "确定要停用该用户?";
    			flag="disable";
    		}else
    		{
    			message = message + "确定要启用该用户?";
    			flag="enable";
    		}
    		if(window.confirm(message))
    		{
    			//提交完成后 把ocvalue、ocdesc清空
				$.post(url,{flag:flag},function(data){
						     if(data.result == 'true')
						    {
						    	    alert("系统提示：操作成功");
						    		if(text == "启用")
						    		{
						    			//启用
						    			self.innerHTML="停用";
						    			document.getElementById("status"+userid).innerHTML="启用";
						    		}else
						    		{
						    			self.innerHTML="启用";
						    			document.getElementById("status"+userid).innerHTML="停用";
						    		}
						    }
						    if(data.result == 'false')
						    {
						    	alert("系统提示：参数更新失败");
						    } 
						  },'json'); 
    			
    		}
    		
    	}
    	
    	function deleteUser(obj) {
    		var userid = obj;
    		var message = "系统提示：确定删除该用户吗?";
    		var url = "userDeleteAction.action?id="+userid;
    		if (window.confirm(message)) {
    			$.post(url, {},function(data){
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
    </script>
  </head>
  
  <body>
  	<form action="findAllUserListAction.action" method="post" id="myFormID" name="myForm">
  		<div class="widget widget-table">
			<div class="widget-content">
  				<table  style="width: 100%;" class="pn-ftable" border="0" cellpadding="10">
						<tr>
							<th style="text-align: center;font-size: 10pt;">登陆名</th>
							<td class="pn-fcontent">
								<input type="text" style="width: 50%;" name="fname" value="${name}"/>
							</td>
							<th style="text-align: center;font-size: 10pt;">真实姓名</th>
							<td class="pn-fcontent">
								<input type="text" style="width: 50%;" name="ffullname" value="${fullname}"/>
							</td>
						</tr>
						<tr>
							<th style="text-align: center;font-size: 10pt;">状态</th>
							<td class="pn-fcontent" colspan="3">
								<select style="width: 210px;" name="disabled">
									<option selected="selected" value="-1">请选择</option>	
									<option value="1" <c:if test="${disabled==1}">selected="selected"</c:if>>启用</option>	
									<option value="0" <c:if test="${disabled==0}">selected="selected"</c:if>>停用</option>				
								</select>
							</td>
						</tr>
					</table>
					<div class="widget-bottom">
						<input type="submit" class="btn btn-s-md btn-primary pull-right"
							value="搜索"/>&nbsp;
						<input class="btn btn-s-md btn-primary pull-right" value="新增用户" type="button" 
							onclick="javascript:document.location='${basePath}/web/addUserPageAction.action'"/>
					</div>
				</div>
			</div>
			<div class="separator line"></div>
			<div class="widget widget-table">
				<div class="widget-header">
					<i class="icon-th-list"></i>
					<h5>用户列表</h5>
				</div>
		    	<div class="widget-content widget-list" style="overflow: scroll;">
		   		 <table style="width: 100%" class="table table-striped table-bordered">
		   		 	<tr>
		 	   		 	<th style="font-size: 10pt;">序号</th> 
			   		 	<th style="font-size: 10pt;">登陆名</th>
			   		 	<th style="font-size: 10pt;">状态</th>
			   		 	<th style="font-size: 10pt;">真实姓名</th>
			   		 	<th style="font-size: 10pt;">英文名</th>
			   		 	<th style="font-size: 10pt;">创建日期</th>
			   		 	<th style="font-size: 10pt;">联系电话</th>
			   		 	<th style="font-size: 10pt;">手机</th>
			   		 	<th style="font-size: 10pt;">邮编</th>
			   		 	<th style="font-size: 10pt;">地址</th>
			   		 	<th style="font-size: 10pt;">传真</th>
			   		 	<th style="font-size: 10pt;">操作</th>
		   		 	</tr>
		   		 	<c:forEach items="${userList}" var="user" varStatus="status">
		   		 		<tr>
					   		 	<td style="text-align:center;">${status.count}</td>
					   		 	<td style="font-size: 10pt; width: 50px" title="${user.name}">${user.name}</td>
					   		 	<td style="font-size: 10pt;text-align: center;"/>
					   		 	<span id="status${user.id}">
						   		 	<c:choose>
						   		 		<c:when test="${user.disabled==1}">
						   		 			启用
						   		 		</c:when>
						   		 		<c:otherwise>
						   		 			停用
						   		 		</c:otherwise>
						   		 	</c:choose>
					   		 	 </span>
					   		 	<td style="font-size: 10pt;" title="${user.fullname}">${user.fullname}</td>
					   		 	<td style="font-size: 10pt;">${user.engname}</td>
					   		 	<td style="font-size: 10pt;">
					   		 		<fmt:formatDate value="${user.createdate}" pattern="yyyy-MM-dd"/>
					   		 	</td>
					   		 	<td style="font-size: 10pt;">${user.phone}</td>
					   		 	<td style="font-size: 10pt;">${user.telephone}</td>
					   		 	<td style="font-size: 10pt;">${user.zipcode}</td>
					   		 	<td style="font-size: 10pt;" title="${user.address}">${user.address}</td>
					   		 	<td style="font-size: 10pt;">${user.fax}</td>
					   		 	<td style="font-size: 10pt;text-align: center;">
					   		 	<c:if test="${admin.id !=user.id }">
					   		 	<c:choose>
					   		 		<c:when test="${user.disabled==1}">
					   		 			<a id="disableA${user.id}" href="#" style="text-decoration: none;" 
					   		 				onclick="disableUser('${user.id}',this);">停用</a>&nbsp;&nbsp;
					   		 		</c:when>
					   		 		<c:otherwise>
					   		 			<a id="disableA${user.id}" href="#" style="text-decoration: none;" 
					   		 				onclick="disableUser('${user.id}',this);">启用</a>&nbsp;&nbsp;
					   		 		</c:otherwise>
					   		 	</c:choose>
					   		 	</c:if>
					   		 	<a href="userSetRoleCheckAction.action?id=${user.id}&flag=update" 
					   		 		style="text-decoration: none;">修改</a>&nbsp;&nbsp;
								<a href="#" style="text-decoration: none;" 
									onclick="deleteUser('${user.id}');">删除</a>
					   		 	</td>
				   		 	</tr>
		   		 	</c:forEach>
		   		 </table>
		   		 <div class="widget-bottom">
		   		 	<jsp:include page="../include/pager.jsp" />
		   		 </div>
		   	</div>
		</div>
   	 </form>
  </body>
</html>
