<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>已签发记录列表</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/web/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	if('${message}' != "")
	{
		alert('${message}');
	}

	$(function(){
		$("#allcheck").bind("click",function(){
			if (this.checked) {
				$("[name = ckitem]").prop("checked", this.checked);
			}else{
				$("[name = ckitem]").prop("checked", this.checked);
			}
		});
	});
	
	function multiFileDownLoad() {
		appIds = [];
		var allcheck = $("#allcheck");
		var allck = allcheck.prop("checked");
		//下载所有文件
		$("input[name = ckitem]:checkbox").each(function(){
			var flag = $(this).prop("checked");
			if (flag) {
				appIds.push($(this).val());
			}
		});
		var params = appIds.join(",");
		location.href="cerDownloadAction.action?id=-1000&multiId="+params;
	}
</script>
</head>

<body>
<div style="padding-top: 2%">
<!-- 	<table style="width: 100%; height: 30px" class="gridtable">
				<tr>
					<td colspan="6">
						<div style="float: right;">
							<input class="button gray" value="批量下载" type="button" onclick="javascript:multiFileDownLoad();">    
						</div>
					</td>
				</tr>
	</table> -->
	<%
		List<UnCertAward> certapps = (List<UnCertAward>) request
				.getAttribute("SignCertList");
	%>
	<div style="width:100%;text-align: center;">
			<%
				if (null != certapps && 0 < certapps.size()) {
			%>		
					<table style="width: 100%;" class="imagetable">
					<tr>
<!-- 						<th style="width: 55px;font-size: 10pt;color: #004799;">
						<input type="checkbox" id="allcheck" name="allcheck">全选
						</th> -->
						<th style="width: 55px;font-size: 10pt;color: #004799;">序号</th>
						<th style="font-size: 10pt;color: #004799;">标识</th>
						<th style="font-size: 10pt;color: #004799;">序列号</th>
<!-- 						<th style="font-size: 10pt;color: #004799;">批次号</th> -->
						<th style="font-size: 10pt;color: #004799;display: none;">证书类型</th>
						<th style="font-size: 10pt;color: #004799;">文件名</th>
						<th style="font-size: 10pt;color: #004799;display: none;">存放路径</th>
						<th style="font-size: 10pt;color: #004799;">签发日期</th>
						<th style="font-size: 10pt;color: #004799;">签发人</th>
						<th style="font-size: 10pt;color: #004799;">用途</th>
						<th style="font-size: 10pt;color: #004799;">操作</th>
					</tr>
			<%		
					for (int i = 0; i < certapps.size(); i++) {
						UnCertAward ca = certapps.get(i);
			%>
				<tr>
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String ctype = ca.getCtype()+"";
						
						if("1".equals(ctype))
						{
							ctype  = "证书";
						}else if("2".equals(ctype))
						{
							ctype = "密钥库";
						}else if("3".equals(ctype))
						{
							ctype = "私钥";
						}else
						{
							ctype = "未知";
						}
					%>
					<%-- <td><input type="checkbox" name="ckitem" value="<%=ca.getId()%>"></td> --%>
					<td style="text-align: center;font-size:9pt"><%=i + 1%></td>
					<td style="text-align: center;font-size:9pt"><%=(ca.getCertType() != null && ca.getCertType()==1) ?"根证书":"子证书"%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getSerialnumber() %></td>
<!-- 					<td style="text-align: center;font-size:9pt"><%=ca.getBatch()%></td> -->
					<td style="text-align: center;font-size:9pt;display: none;"><%=ctype%></td>
					<td style="text-align: left;font-size:9pt;"><%=ca.getFname()%></td>
					<td style="text-align: left;font-size:9pt;display: none;"><%=ca.getPath()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getAwardDate() !=null ?sdf.format(ca.getAwardDate()):null%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getSignOperator() != null ? ca.getSignOperators() : ""%>&nbsp;</td>
					<td style="text-align: left;font-size:9pt"><%=ca.getEffect() != null ? ca.getEffect() : ""%>&nbsp;</td>
					<td style="text-align: center;font-size:9pt">
					<%
					if("2".equals(ca.getCtype()+"") && !"CER".equalsIgnoreCase(ca.getFileType())){
					%>	
						<%-- <a href="#" onclick="javascript:certDownload(<%=ca.getId()%>);return false;" style="text-decoration: none;">下载密钥库</a>&nbsp; --%>
					<%} %>
					<%
					if("2".equals(ca.getCtype()+"")&&0 == (int)ca.getIsExportCert()){
					%>	
						<a href="#" onclick="javascript:exportCert(<%=ca.getId()%>);return false;" style="text-decoration: none;">导出证书</a>&nbsp;
					<%
					} else if("2".equals(ca.getCtype()+"")&&1 == (int)ca.getIsExportCert()){
					%>	
						<a href="#" onclick="javascript:cerFileDownload(<%=ca.getId()%>);return false;" style="text-decoration: none;">下载证书</a>&nbsp;
					<%
					}
					%>
				</tr>
			
			<%
					}%>
			</table>
			<%	} else {
			%>
			<span>当前没有已签发的证书</span>
			<%
				}
			%>

	</div>
	</div>
</body>
<script type="text/javascript">
	/**
	*密钥库下载
	*/
	function certDownload(obj)
	{
		var id = obj;
		//alert('下载');
		location.href="certDownloadAction.action?id="+id;
	};
	
	/**
	*cer证书下载
	*/
	function cerFileDownload(obj)
	{
		var id = obj;
		//alert('下载');
		location.href="cerDownloadAction.action?id="+id;
	};
	
	/**
	*
	*导出证书
	*/
	function exportCert(obj,type)
	{
		var id = obj;
		var certType = type;
		var result = window.confirm("系统提示：确定要从当前密钥库导出证书?");
		if(result)
			location.href="exportCertAction.action?id="+id+"&type="+certType;
	};
</script>
</html>
