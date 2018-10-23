<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>根证书详细信息</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
</head>
		
<body> 
	<div style="width:100%;text-align: center; padding-top: 2%">
		<form method="post" name="myform">
			<table style="width: 100%;" class="imagetable">
				<tr style="height: 25px">
					<th style="width:10%;text-align: right;font-size: 10pt;color: #004799;">版本: &nbsp;</th>
					<td style="font-size: 10pt;"><%-- ${version} --%>V3</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">真实姓名: &nbsp;</th>
					<td style="font-size: 10pt;">
						<c:choose>
							<c:when test="${app.certType eq 1}">
								根证书
							</c:when>
							<c:otherwise>
								子证书
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">申请人: &nbsp;</th>
					<td style="font-size: 10pt;">${app.ou}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">申请时间: &nbsp;</th>
					<td style="font-size: 10pt;"><fmt:formatDate value="${app.createdate}" type="both"/></td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">签名算法: &nbsp;</th>
					<td style="font-size: 10pt;">${app.signAlgorithm}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">创建新密钥: &nbsp;</th>
					<td style="font-size: 10pt;">
						<c:choose>
							<c:when test="${app.newKeyPair eq 1}">
								是
							</c:when>
							<c:otherwise>
								否
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">密钥长度: &nbsp;</th>
					<td style="font-size: 10pt;">${app.keySize}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书有限期: &nbsp;</th>
					<td style="font-size: 10pt;">${app.dueTime}&nbsp;年 </td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">网站: &nbsp;</th>
					<td style="font-size: 10pt;">${app.net}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">邮件: &nbsp;</th>
					<td style="font-size: 10pt;">${app.email}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">用途: &nbsp;</th>
					<td style="font-size: 10pt;">${app.effect}</td>
				</tr>
				<c:if test="${type eq 'detail'}">
					<tr style="height: 25px">
						<th style="text-align: right;font-size: 10pt;color: #004799;">审核时间: &nbsp;</th>
						<td style="font-size: 10pt;">
							<fmt:formatDate value="${app.checkdate}" type="both"/>
						</td>
					</tr>
				</c:if>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">备注: &nbsp;</th>
					<td style="font-size: 10pt;">${app.remark}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;"><span style="color: red;">*</span>审核结果: &nbsp;</th>
					<c:if test="${type eq 'check'}">
					<td style="font-size: 10pt;">
						<textarea rows="5" cols="30" maxlength="50" name="checkResult" id="checkResult"></textarea>
					</td>
					</c:if>
					<c:if test="${type eq 'detail'}">
					<td style="font-size: 10pt;">
						<textarea rows="5" cols="30" maxlength="50" disabled="disabled" name="checkResult" id="checkResult">${app.checkResult}</textarea>
					</td>
					</c:if>
				</tr>
				<tr>
					<td colspan="2" style="text-align:rigt" >
						
						<c:if test="${type eq 'check'}">
							<input class="button gray" type="button" value="审核通过" onclick="checkApprove('CN=${app.cn},C=${app.c},ST=${app.st},L=${app.l},OU=${app.ou},O=${app.o}');"> 
								&nbsp;
							<input class="button gray" type="button" value="审核不通过" onclick="checkDeny('CN=${app.cn},C=${app.c},ST=${app.st},L=${app.l},OU=${app.ou},O=${app.o}');">
						</c:if>
						&nbsp;
						<input class="button gray" type="button" value="返 回" onclick="javascript:history.back();"> 
					</td>
				</tr>
			</table>
			<input type="hidden" name="certType" value="${app.certType}">
			<input type="hidden" name="id" value="${app.id}">
		</form>
	</div>
</body>
<script type="text/javascript">
	
	function checkApprove()
	{
		
		var str = arguments[0];
		var checkResult = document.getElementById("checkResult").value;
		
		if(checkResult == null || checkResult == "")
		{
			alert("系统提示：请先填入审核结果!");
			return;
		}
		
		var next = '使用者: '+str+'\n'+'确认审核通过?';
				
		var result = window.confirm(next);
		
		if(result)
		{
			document.myform.action = "certCheckAction.action?result=approve";
			document.myform.submit();
		}
	}
	function checkDeny()
	{
		alert("系统提示：即将实现...");
		/*var action = "/web/certCheckAction.action?certType=1";
		var str = arguments[0];
		var next = 'DN='+str+'\n'+'确认审核不通过?';
				
		var result = window.confirm(next);
		
		if(result)
		{
			location.href=action;
		}*/
	}
		
</script>
</html>
