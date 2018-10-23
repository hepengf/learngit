<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title>资源管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
    <link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		/**
		 * 删除资源
		 **/
		function resourceDel() {
			var resourceId = arguments[0];
			document.location.href="deleteResourceAction.action?resourceId="+resourceId;
		}
	</script>
  </head>
  
  <body>
    <form action="findAllResourceListAction.action" name="myForm">
     <div style="padding-top: 5px">
     </div>
   		 <table style="width: 100%"  class="table table-striped table-bordered">
   		 	<tr>
	   		 	<th style="font-size: 10pt;">序号</th>
<!-- 	   		 	<th style="font-size: 10pt;text-align: left;">类型</th> -->
	   		 	<th style="font-size: 10pt;text-align: left;">资源名称</th>
	   		 	<th style="font-size: 10pt;text-align: left;">首页路径</th>
	   		 	<th style="font-size: 10pt;text-align: left;">描述</th>
	   		 	<th style="font-size: 10pt;text-align: left;">创建时间</th>
	   		 	<th style="font-size: 10pt;text-align: left;">创建人</th>
	   		 	<!-- <th style="font-size: 10pt;">操作</th> -->
   		 	</tr>
   		 	<c:forEach items="${resourceList}" var="resource" varStatus="status">
   		 		<tr>
			   		<td style="font-size: 10pt;text-align: center;">${status.count}</td>
			   		<td style="font-size: 10pt;text-align: left;">${resource.name}</td>
			   		 	<td style="font-size: 10pt;text-align: left;">
			   		 		${resource.value}
			   		 	</td>
			   		 	<td style="font-size: 10pt;text-align: left;">
			   		 		${resource.descval}
			   		 	</td>
			   		 	<td style="font-size: 10pt;text-align: left;">
			   		 		<fmt:formatDate value="${resource.createdate}" pattern="yyyy-MM-dd"/>
			   		 	</td>
			   		 	<td style="font-size: 10pt;text-align: left;">
			   		 		${resource.operator}
			   		 	</td>
			   		 	<%-- <td style="font-size: 10pt;text-align: center;">
				   		 	<a href="#" style="text-decoration: none;" onclick="resourceDel('${resource.id}')">删除</a>&nbsp;&nbsp;
			   		 	</td> --%>
		   		 	</tr>
   		 	</c:forEach>
   		 			   		 	<!-- 
   		 	<tr>
   		 		<td  colspan="9" style="text-align: right;">
   		 			<input value="新增资源" type="button">
   		 		</td>
   		 	</tr>
   		 	-->
   		 </table>
   		 <div>
   		 	<jsp:include page="../include/pager.jsp"/>
   		 </div>
   </form>
  </body>
</html>
