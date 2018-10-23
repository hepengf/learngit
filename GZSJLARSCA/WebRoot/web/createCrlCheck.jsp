<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>作废证书列表</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
</head>

<body>
	<div style="width:100%;text-align: center;">
		<table style="width: 50%;" class="gridtable">
				<tr>
					<th style="width: 20%;">序列号</th>
					<td style="width: 30%;">
						<input type="text" style="width: 100%;">
					</td>
					<th style="width: 20%;">使用者</th>
					<td >
						<input type="text" style="width: 100%;">
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<input type="button" value="查 询" class="button gray">
					</td>
				</tr>
			</table>
			<br/>
			
		<form method="post" action="createCertCrlAction.action" name="myform">
	
			<table style="width: 100%;" class="imagetable">
			<tr>
				<th style="width: 35px;font-size: 10pt;color: #004799;">全选<input type="checkbox" id="allcerts" name="allcerts"></th>
				<th style="width: 35px;font-size: 10pt;color: #004799;">序号</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">使用者</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">序列号</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">签名算法</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">启用日期</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">截止日期</th>
				<th style="font-size: 10pt;color: #004799;text-align: center;">创建时间</th>
			</tr>
					
			<c:forEach items="${certs}" var="item" varStatus="status">
			
				<tr>
					<td style="text-align: center;"><input type="checkbox" name="certs" value="${item.id}"></td>
					<td style="text-align: center;font-size:9pt">${status.count}</td>
					<td style="text-align: left;font-size:9pt">${item.subjectdn}</td>
					<td style="text-align: center;font-size:9pt">${item.serialnumber}</td>
					<td style="text-align: center;font-size:9pt">${item.sigalgname}</td>
					<td style="text-align: center;font-size:9pt"><fmt:formatDate value="${item.begindate}" type="date"/></td>
					<td style="text-align: center;font-size:9pt"><fmt:formatDate value="${item.enddate}" type="date"/></td>
					<td style="text-align: center;font-size:9pt"><fmt:formatDate value="${item.createTime}" type="both"/></td>
					
				</tr>
				
			</c:forEach>
			<tr>
				<td colspan="8"> <input type="button" value="生成CRL" onclick="createCrl()" class="button gray"/></td>
			</tr>
		</table>
	</form>
	</div>
	
</body>

<script type="text/javascript">
	$("#allcerts").click(function(){
	     $("input[name='certs']").attr("checked",$(this).attr("checked"));
	});
	
	function createCrl(){
		var len = $("input[name='certs']:checked").length;
		if(len == 0)
		{
			alert("系统提示：请先选择已作废的证书!");
			return;
		}
		
		var result = window.confirm("系统提示：确认要更新CRL列表?");
		if(result)
			document.myform.submit();
		
	}
</script>
</html>
