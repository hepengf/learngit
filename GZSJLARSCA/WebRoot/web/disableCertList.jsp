<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>作废证书列表</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
	<%
		List<Object[]> certs = (List<Object[]>) request
				.getAttribute("CertLists");
	%>
	<div style="width:100%;text-align: center;">
			<table style="width: 100%;" class="gridtable">
				<tr>
					<th style="width: 16%;">证书类型</th>
					<td style="width: 17%;">
						<select style="width: 100%;">
							<option selected="selected" value="-1">请选择</option>	
							<option value="1">根证书</option>	
							<option value="2">子证书</option>				
						</select>
					</td>
					<th style="width: 16%;">序列号</th>
					<td style="width: 17%;">
						<input type="text" style="width: 100%;">
					</td>
					<th style="width: 16%;">文件名</th>
					<td style="width: 17%;">
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
			<%
				if (null != certs && 0 < certs.size()) {
			%>
					<table style="width: 100%;" class="imagetable">
					<tr>
						<th style="width: 35px;font-size: 10pt;color: #004799;">序号</th>
<!-- 						<th style="font-size: 10pt;color: #004799;text-align: center;">申请人</th> -->
						<th style="font-size: 10pt;color: #004799;text-align: center;">使用者</th>
						<th style="font-size: 10pt;color: #004799;text-align: center;">证书类型</th>
						<th style="font-size: 10pt;color: #004799;text-align: center;">文件名</th>
<!-- 						<th style="font-size: 10pt;color: #004799;text-align: center;">证书类型</th> -->
<!-- 						<th style="font-size: 10pt;color: #004799;text-align: center;">版本</th> -->
						<th style="font-size: 10pt;color: #004799;text-align: center;">序列号</th>
						<th style="font-size: 10pt;color: #004799;text-align: center;">启用日期</th>
						<th style="font-size: 10pt;color: #004799;text-align: center;">截止日期</th>
						
						<th style="font-size: 10pt;color: #004799;">操作</th>
					</tr>
			<%		
					for (int i = 0; i < certs.size(); i++) {
						Object[] cert = certs.get(i);
			%>
			
				<tr>
					<%
						String ctype = cert[6]+"";
						
						if("1".equals(ctype))
						{
							ctype  = "根证书";
						}else if("2".equals(ctype))
						{
							ctype = "子证书";
						}else
						{
							ctype = "未知";
						}
					%>
					<td style="text-align: center;font-size:9pt"><%=i + 1%></td>
					<td style="text-align: left;font-size:9pt"><%=cert[14]%></td>
<!-- 					<td style="text-align: left;font-size:9pt"><%=cert[1]%></td> -->
					<td style="text-align: left;font-size:9pt"><%=ctype%></td>
					<td style="text-align: left;font-size:9pt"><%=cert[4]%></td>
<!-- 					<td style="text-align: center;font-size:9pt"><%=cert[12]%></td> -->
<!-- 					<td style="text-align: center;font-size:9pt"><%=cert[13]%></td> -->
					<td style="text-align: left;font-size:9pt"><%=cert[8]%></td>
					<td style="text-align: center;font-size:9pt"><%=SimpleDateFormatUtils.formatDateReturnYYYMMDD((Date)cert[10])%></td>
					<td style="text-align: center;font-size:9pt">
						<%=SimpleDateFormatUtils.formatDateReturnYYYMMDD((Date)cert[11])%>
					</td>
					
					
					<td style="text-align: left;font-size:9pt">
						<%if(cert[15] !=null && ("2").equals(cert[15]+"")) {%>
							<a href="#" onclick="javascript:certdisabledapp('<%=cert[0]%>','<%=cert[7]%>','<%=cert[8]%>');return false;" style="text-decoration: none;">作 废</a>&nbsp;
						<%} %>
						<a href="#" onclick="javascript:alert('完善中');return false;" style="text-decoration: none;">明 细</a>&nbsp;&nbsp;
					</td>
				</tr>
				
			<%
				}
			%>
		</table>
			<%
				} else {
			%>
			<span>当前没有可作废的证书</span>
			<%
				}
			%>

	</div>
	
</body>

<script type="text/javascript">


function certdisabledapp()
{
	if(arguments.length != 3)
		return;
	var uaid = arguments[0];
	
	var cid = arguments[1];
	alert(cid);
	var serialnumber = arguments[2];
	
	var result = window.confirm("系统提示：确定要作废当前选择的证书?");
	if(result)
	{
		location.href="disabledCertAppAction.action?uaid="+uaid+"&cid="+cid+"&serialnumber="+serialnumber;
	}
	//alert(uaid+"  "+cid);
}

function certdisabled()
{
	if(arguments.length != 2)
		return;
	var uaid = arguments[0];
	var cid = arguments[1];
	
	var result = window.confirm("系统提示：确定要作废当前选择的证书?");
	if(result)
	{
		location.href="disabledCertAction.action?uaid="+uaid+"&cid="+cid;
	}
	//alert(uaid+"  "+cid);
}
</script>
</html>
