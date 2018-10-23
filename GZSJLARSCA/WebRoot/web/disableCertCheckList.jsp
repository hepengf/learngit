<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>吊销证书审核列表</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div style="width:100%;text-align: center;">
			<c:choose>
				<c:when test="${!empty CertLists}">
					<table style="width: 100%;" class="imagetable">
						<tr>
							<th style="width: 35px;font-size: 10pt;color: #004799;">序号</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">使用者</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">序列号</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">证书有效期</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">申请人</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">申请时间</th>
							<th style="font-size: 10pt;color: #004799;text-align: center;">申请原因</th>
							<th style="font-size: 10pt;color: #004799;">操作</th>
						</tr>
						<c:forEach items="${CertLists}" var="cert" varStatus="status">
							<tr>
								<td style="text-align: center;font-size:9pt">${status.count}</td>
								<td style="text-align: left;font-size:9pt">${cert[5]}</td>
								<td style="text-align: left;font-size:9pt">${cert[7]}</td>
								<td style="text-align: center;font-size:9pt">
									<fmt:formatDate value="${cert[6]}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td style="text-align: center;font-size:9pt">${cert[3]}</td>
								<td style="text-align: center;font-size:9pt">
									<fmt:formatDate value="${cert[2]}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td style="text-align: center;font-size:9pt">
									<c:if test="${!empty cert[1]}">${cert[1]}</c:if>
								</td>
								<td style="text-align: left;font-size:9pt">
									<a href="#" onclick="javascript:certDisabledSubmit('${cert[0]}', '${uaid}', '${cert[4]}');return false;" style="text-decoration: none;">审 核</a>
								</td>
						</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<span>当前没有可审核的吊销证书</span>
				</c:otherwise>
			</c:choose>

	</div>
	
</body>

<script type="text/javascript">
	function certDisabledSubmit()
	{
		if(arguments.length != 3)
			return;
		var revokeId = arguments[0];
		var uaid = arguments[1];
		var cid = arguments[2];
		
		
		//alert(revokeId+" - "+cid);
		//return;
		location.href="redirectDisabledCertCheckAction.action?revokeId="+revokeId+"&cid="+cid+"&uaid="+uaid;
	}
</script>
</html>
