<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>证书管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript">
	
</script>
</head>

<body>

	<div style="width:100%;text-align:  center;">
		<fieldset title="证书管理" style="width: 400px;">
			<table width="width:350px;">
				<tr>
					<td width="150px" style="text-align: left;">证书申请</td>
					<td>
						<a href="<%=request.getContextPath() %>/web/certApp.jsp">进入</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">证书签发</td>
					<td>
						<a href="<%=request.getContextPath() %>/web/findUnSignCertAction.action">进入</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">证书下载</td>
					<td>
						<a href="#">进入</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">证书签名</td>
					<td>
						<a href="#">进入</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">生成CRL证书吊销文件</td>
					<td>
						<a href="<%=request.getContextPath() %>/web/genCRLAction.action">进入</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">更新证书吊销文件</td>
					<td>
						<a href="#">进入</a>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>
