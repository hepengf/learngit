<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>待审核根证书申请列表</title>

<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
	<%
		List<UnCertApp> certapps = (List<UnCertApp>) request
				.getAttribute("UnSignCertList");
	%>
	<div style="width:100%;text-align: center; padding-top: 2%">
		<fieldset title="待审核根证书申请列表">
			<%
				if (null != certapps && 0 < certapps.size()) {
					for (int i = 0; i < certapps.size(); i++) {
						UnCertApp ca = certapps.get(i);
			%>
			<table style="width: 100%;"  class="imagetable">
				<tr>
					<th style="width: 55px;font-size: 10pt;color: #004799;font-family:'Times New Roman'">序号</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">证书类型</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">申请人</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">名称</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">国家</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">省份</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">城市</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">单位</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">部门</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">申请日期</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">用途</th>
					<th style="font-size: 10pt;color: #004799;font-family:'Times New Roman'">操作</th>
				</tr>
				<tr>
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					%>
					<td style="text-align: center;font-size:9pt"><%=i + 1%></td>
					<td style="text-align: center;font-size:9pt"><%
						if(ca.getCertType()!=null){
							if((int)ca.getCertType()==1)
							{
					%>
							根证书
					<%		
							}else{%>
								子证书
					<%		}
						}
					%>&nbsp;</td>
					<td style="text-align: center;font-size:9pt"><%=ca.getCreateOperators()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getCn()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getC()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getSt()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getL()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getOu()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getO()%></td>
					<td style="text-align: center;font-size:9pt"><%=ca.getCreatedate() != null ? sdf.format(ca.getCreatedate()):""%>&nbsp;</td>
					<td style="text-align: center;font-size:9pt"><%=ca.getEffect() != null ? ca.getEffect() : ""%>&nbsp;</td>
					<td style="text-align: center;font-size:9pt">
						<a href="#" onclick="javascript:check(<%=ca.getId()%>);return false;" style="text-decoration: none;">审核</a>
						&nbsp;
						<a href="#" onclick="javascript:alert('完善中');return false;" style="text-decoration: none;">明细</a>
					</td>
				</tr>
			</table>
			<%
				}
				} else {
			%>
			<span>当前没有可审核的根证书请求</span>
			<%
				}
			%>

		</fieldset>
	</div>
</body>

<script type="text/javascript">
		function signCert()
		{
			var id = arguments[0];
			var certType = arguments[1];
			
			var result = window.confirm("系统提示：确定签发该用户证书？");
			//alert("签发证书-"+id);
			if(result){
				location.href="signCertAction.action?id="+id+" and certType="+certType;
		
			};
		}
		
		function check(obj)
		{
			var id = obj;
			location.href="certAppDetailAction.action?id="+id+"&type=check";
		}
	</script>
</html>
