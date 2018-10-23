<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>证书吊销审核</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
</head>
		
<body> 
	<div style="width:100%;text-align: center;">
		<form  method="post" name="myform">
		
			<table style="width: 100%;" class="imagetable">
				<tr style="height: 25px">
					<th style="width:18%;text-align: right;font-size: 10pt;color: #004799;">使用者: &nbsp;</th>
					<td style="font-size: 10pt;">${certDetail[11]}</td>
				</tr>
				<tr style="height: 25px">
					<th style="width:10%;text-align: right;font-size: 10pt;color: #004799;">签发者: &nbsp;</th>
					<td style="font-size: 10pt;">${certDetail[13]}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书序列号: &nbsp;</th>
					<td style="font-size: 10pt;">
						${certDetail[10]}
					</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书有效起始日期: &nbsp;</th>
					<td style="font-size: 10pt;">
						<fmt:formatDate value="${certDetail[8]}" type="both"/>
					</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书有效截止日期: &nbsp;</th>
					<td style="font-size: 10pt;">
						<fmt:formatDate value="${certDetail[9]}" type="both"/>
					</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">申请人: &nbsp;</th>
					<td style="font-size: 10pt;">${certDetail[3]}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">申请时间: &nbsp;</th>
					<td style="font-size: 10pt;"><fmt:formatDate value="${certDetail[2]}" type="both"/></td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">审核描述: &nbsp;</th>
					<td style="font-size: 10pt;">
						<textarea rows="5" cols="30" name="checkResult" id="checkResult"  ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:rigt" >
						<input class="button gray" type="button" value="审核通过" onclick="checkApprove('${certDetail[5]}');"> 
							&nbsp;
						<input class="button gray" type="button" value="审核不通过" onclick="checkDeny('${certDetail[5]}');">
						&nbsp;
						<input class="button gray" type="button" value="返 回" onclick="javascript:history.back();"> 
					</td>
				</tr>
			</table>
			<input type="hidden" name="revokeId" id="revokeId" value="${certDetail[0]}">
			<input type="hidden" name="cid" id="cid" value="${certDetail[5]}">
			<input type="hidden" name="uid" id="uid" value="${certDetail[15]}"/>
		</form>
	</div>
</body>
<script type="text/javascript">
	
	function checkApprove()
	{
		
		var str = arguments[0];
		
		var action = "disableCertCheckAction.action?result=approve";
		
		var checkResult = document.getElementById("checkResult").value;
		
		if(checkResult == null || checkResult=="")
		{
			alert("系统提示：请先填写审核结果!");
			return;
		}
		
		var next = '使用者: '+str+'\n'+'确认审核通过?';
				
		var result = window.confirm(next);
		
		if(result)
		{
			document.myform.action=action;
			document.myform.submit();
		}
	}
	function checkDeny()
	{
		alert("系统提示：即将实现...");
	}
		
</script>
</html>
