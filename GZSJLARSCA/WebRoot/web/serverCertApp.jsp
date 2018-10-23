<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>服务器证书申请</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/province.js"></script>
</head>

<body onload="setup()" style="margin-top: 10px;">
	<!-- 
	CN（Common Name - 名字与姓氏） 
	OU（Organization Unit - 组织单位名称） 
	O（Organization - 组织名称） 
	L（Locality - 城市或区域名称） 
	ST（State - 州或省份名称） 
	C（Country - 国家名称）
	 -->
	<div style="width:100%;text-align:  left;">
	<%
		Object message = request.getAttribute("message"); 
		if(message != null)
		{
			out.write(message+"");
		}
	%>
	<form action="signServerCertAppAction.action" method="post" name="form1">
		<div style="padding-top: 2%">
			<table style="width: 100%;" class="imagetable">
				<tr>
					<th style="width:150px;text-align: right;font-size: 10pt;">
					<span style="color: red;">*</span>
					 <span style="color: #004799;">IP:</span></th>
					<td>
						<input type="text" name="IP" style="width:90%" id="IP" ><span style="COLOR:RED">*</span>
					</td>
				</tr>
				<tr>
					<th width="100px" style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>国家(C):</th>
					<td>
<!-- 						<input type="text" name="C" style="width:90%"> -->
						<select name="C" style="width:90%">
							<option value="CN"  >中国</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>省份(ST):</th>
					<td>
<!-- 					<input type="text" name="ST" style="width:90%"> -->
						<select id="ST" name="ST" style="width:90%"></select> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>城市(L):</th>
					<td>
<!-- 					<input type="text" name="L" style="width:90%"> -->
						<select id="L" name="L" style="width:90%"></select> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>公司(OU):</th>
					<td><input type="text" id="OU" name="OU" style="width:90%"></td>
				</tr>
				<tr>
                    <th style="text-align: right;font-size: 10pt;color: #004799;">部门(O):</th>
                    <td><input type="text" id="O" name="O" style="width:90%"></td>
                </tr>
				<tr>
                    <th style="text-align: right;font-size: 10pt;color: #004799;">密钥索引:</th>
                    <td><input type="keyIndex" id="keyIndex" name="keyIndex" style="width:90%"></td>
                </tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;">证书期限:</th>
					<td>
						<select name="dueTime" style="width:90%">
							<option value="1" >一年</option>
							<option value="2">二年</option>
							<option value="3" >三年</option>
							<option value="10" selected="selected">十年</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<input type="button" value="提交" onclick="certapp()" class="button gray">
<!-- 						&nbsp;&nbsp;<input type="reset" value="重置">&nbsp;&nbsp; -->
					</td>
				</tr>
			</table>
			</div>
	</form>
	</div>
</body>
	<script type="text/javascript">
				
		function certapp()
		{
			var IP = document.getElementById("IP").value;
			if(null == IP || "" == IP)
			{
				alert("系统提示：IP不能为空!");
				return;
			}
			
			var OU = document.getElementById("OU").value;
			if(null == OU || "" == OU)
			{
				alert("系统提示：公司名称不能为空!");
				return;
			}
			
			var keyIndex = document.getElementById("keyIndex").value;
            if(null == keyIndex || "" == keyIndex)
            {
                alert("系统提示：密钥索引不能为空!");
                return;
            }
			
			var ST = document.getElementById("ST").value;
			var L = document.getElementById("L").value;
			
			if(ST == "省份")
			{
				alert("系统提示：请先选择省份信息!");
				return;
			}
			
			if(L == "城市")
			{
				alert("系统提示：请先选择城市信息!");
				return;
			}
			
			var result = window.confirm("系统提示：确定提交服务器证书申请信息?");
			if(result)
				document.form1.submit();
		}
	</script>
</html>
