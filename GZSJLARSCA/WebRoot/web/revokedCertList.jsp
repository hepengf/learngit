<%@ page language="java"
	import="java.text.*,java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>注销证书列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/web/js/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/web/js/dateUtil.js"></script>
<script type="text/javascript">
	function back()
	{
		window.location.href="certCrlCheckAction.action";
	}
</script>
</head>

<body>
	<form action="revokedCertListAction.action" method="post" name="myForm">
	<input type="hidden" name="id" value="${id}"/>
		<div class="widget widget-table">
			<div class="widget-content">
				<table style="width: 100%;" class="pn-ftable" border="0" cellpadding="10">
				<tr>
					<th style="text-align: center;font-size: 10pt;">注销时间:</th>
					<td  class="pn-fcontent">
						<input type="text"
								id="startDate"
								name="startDate" readonly="readonly"
								value='${startDate}'
								class="Wdate"
								onclick="startDateClk($(this))"
								/>
					</td>
					<th style="text-align: center;font-size: 10pt;">至</th>
					<td  class="pn-fcontent">
						<input type="text"
								id="endDate"
								name="endDate" readonly="readonly"
								value='${endDate}'
								class="Wdate"
								onclick="endDateClk($(this))"
								/>
					</td>
				</tr>
<!-- 				<tr>
					<th style="text-align: center;font-size: 10pt;">吊销人:</th>
					<td  class="pn-fcontent" colspan="3">
						<input type="text" name="user" style="width: 200px;"/>
					</td>
				</tr> -->
			</table>
			<div class="widget-bottom">
					<input type="submit" class="btn btn-s-md btn-primary pull-right"
						value="搜索"/>
					<input type="button" class="btn btn-s-md btn-primary pull-right"
						value="返回" onclick="back()"/>
			</div>
			</div>
		</div>
		<div class="separator line"></div>
		<div class="widget widget-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h5>注销文件详细列表</h5>
			</div>
			<div class="widget-content widget-list">
				<table style="width: 100%;" class=" table table-striped table-bordered">
					<tr>
						<th style="width: 35px;font-size: 10pt;">序号</th>
						<th style="font-size: 10pt;text-align: center;">序列号</th>
						<th style="font-size: 10pt;ctext-align: center;">使用者</th>
						<th style="font-size: 10pt;text-align: center;">注销时间</th>
					</tr>
		
					<c:forEach items="${certs}" var="item" varStatus="status">
		
						<tr>
							<td style="text-align: center;font-size:9pt">${status.count}</td>
							<td style="text-align: center;font-size:9pt">${item.serialnumber}</td>
							<td style="text-align: left;font-size:9pt">${item.subjectdn}</td>
							<td style="text-align: center;font-size:9pt"><fmt:formatDate
									value="${item.createCrlTime}" type="both" /></td>
		
						</tr>
		
					</c:forEach>
				</table>
				<div class="widget-bottom">
		   		 	<jsp:include page="include/pager.jsp" />
		   		</div>
		   	</div>
		</div>
	</form>
</body>
</html>
