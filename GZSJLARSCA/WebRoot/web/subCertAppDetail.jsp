<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<title>签发子证书</title>

</head>
		
<body> 
<form method="post" action="signCertAction.action" name="myForm">
	<div style="width:100%;text-align: center;">
			<table style="width: 100%;" class="imagetable">
				<tr style="height: 25px">
					<th style="width:18%;text-align: right;font-size: 10pt;color: #004799;">使用者: &nbsp;</th>
					<td style="font-size: 10pt;">CN=${app.cn},C=${app.c},ST=${app.st},L=${app.l},OU=${app.ou},O=${app.o}</td>
				</tr>
				<tr style="height: 25px">
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书种类: &nbsp;</th>
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
					<td style="font-size: 10pt;">${app.userid}</td>
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
				<tr> 
 					<th style="text-align: right;font-size: 10pt;color: #004799;">签发的根证书:</th>
					<td> 
						<select id="rootCert" name="rootCert" style="width:90%"> 
							<c:forEach items="${certs}" var="item" varStatus="status"> 
								<option value="${item.id}" >${item.issuredn}</option> 
							</c:forEach> 
 						</select> 
 					</td> 
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
				<tr>
					<td colspan="2" style="text-align:rigt" >
						<input class="button gray" type="button" value="签 发" onclick="javascript:signCert('${app.id}','${app.certType}');return false;" > 
						&nbsp;
						<input class="button gray" type="button" value="返 回" onclick="javascript:history.back();"> 
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${app.id}" />
			<input type="hidden" name="certType" value="${app.certType}" />
	</div>
</form>
</body>
<script type="text/javascript">
	function signCert()
	{
		var id = arguments[0];
		var certType = arguments[1];
		
		var result = window.confirm("系统提示：确定签发该用户证书?");
		//alert("签发证书-"+id);
		if(result){
			//location.href="signCertAction.action?id="+id+" and certType="+certType;
			document.myForm.submit();
	
		};
	};
		
</script>
</html>
