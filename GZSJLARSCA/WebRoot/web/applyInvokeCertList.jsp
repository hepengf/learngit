<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>申请作废</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/web/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#allcheck").bind("click",function(){
			if (this.checked) {
				$("[name = ckitem]").prop("checked", this.checked);
			}else{
				$("[name = ckitem]").prop("checked", this.checked);
			}
		});
	});
	
</script>
</head>

<body>
		<table style="width: 100%;" class="gridtable">
			<tr>
				<td colspan="6">
					<div style="float: right;">
						<input class="button gray" value="" type="button" onclick="javascript:multiFileDownLoad();">    
					</div>
				</td>
			</tr>	
		</table>
		<div style="overflow: scroll;">
			<c:choose>
				<c:when test="${!empty certs}">
					<table style="width: 100%;" class="imagetable" >
					<tr>
						<th style="width: 55px;font-size: 10pt;color: #004799;">
						<input type="checkbox" id="allcheck" name="allcheck">全选
						</th>
						<th style="width: 55px;font-size: 10pt;color: #004799;">序号</th>
						<th style="font-size: 10pt;color: #004799;">序列号</th>
	 					<th style="font-size: 10pt;color: #004799;">批次号</th>
						<th style="font-size: 10pt;color: #004799;">证书类型</th>
						<th style="font-size: 10pt;color: #004799;">使用人</th>
						<th style="font-size: 10pt;color: #004799;">启用日期</th>
						<th style="font-size: 10pt;color: #004799;">截止日期</th>
						<th style="font-size: 10pt;color: #004799;">文件名</th>
						<th style="font-size: 10pt;color: #004799;display: none;">存放路径</th>
						<th style="font-size: 10pt;color: #004799;">签发日期</th>
						<th style="font-size: 10pt;color: #004799;">签发人</th>
						<th style="font-size: 10pt;color: #004799;">操作</th>
					</tr>
				<c:forEach items="${certs}" var="cert" varStatus="status">
					<tr>
						<td style="text-align: center;font-size:9pt">
							<input type="checkbox" name="subcheck">
						</td>
						<td style="text-align: center;font-size:9pt">${status.count}</td>
						<td style="text-align: center;font-size:9pt">${cert[8]}</td>
						<td style="text-align: center;font-size:9pt">${cert[5]}</td>
						<td style="text-align: center;font-size:9pt">
							<c:if test="${cert[12]==1}">
								证书
							</c:if>
							<c:if test="${cert[12]==2}">
								秘钥库
							</c:if>
							<c:if test="${cert[12]==3}">
								私钥
							</c:if>
							<c:if test="${cert[12]==4}">
								终端证书
							</c:if>
							<c:if test="${cert[12]==5}">
								服务器证书
							</c:if>
						</td>
						<td style="text-align: center;font-size:9pt">${cert[16]}</td>
						<td style="text-align: center;font-size:9pt">${cert[10]}</td>
						<td style="text-align: center;font-size:9pt">${cert[11]}</td>
						<td style="text-align: center;font-size:9pt">${cert[4]}</td>
						<td style="text-align: center;font-size:9pt">
							<fmt:formatDate value="${cert[3]}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td style="text-align: center;font-size:9pt">${cert[2]}</td>
						<td style="text-align: center;font-size:9pt">
							<a href="#" onclick="javascript:certdisabledapp('${cert[0]}','${cert[7]}','${cert[8]}');">申请作废</a>
						</td>
					</tr>			
				</c:forEach>
			</table>
			</c:when>
			<c:otherwise>
			<span>当前没有可以作废的证书</span>
			</c:otherwise>	
		</c:choose>
	</div>
</body>
<script type="text/javascript">
function certdisabledapp()
{
	if(arguments.length != 3)
		return;
	var uaid = arguments[0];
	
	var cid = arguments[1];
	
	var serialnumber = arguments[2];
	var result = window.confirm("系统提示：确定要作废当前选择的证书?");
	if(result)
	{
		location.href="disabledCertAppAction.action?uaid="+uaid+"&cid="+cid+"&serialnumber="+serialnumber;
	}
}
</script>
</html>
