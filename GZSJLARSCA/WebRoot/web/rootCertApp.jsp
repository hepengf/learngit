<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>根证书申请</title>
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
	<form action="rootCertAppAction.action" method="post" name="form1">
		<div style="padding-top: 2%">
			<table style="width: 100%;" class="imagetable">
				<tr>
					<th style="width:150px;text-align: right;font-size: 10pt;">
					<span style="color: red;">*</span>
					 <span style="color: #004799;">名称(CU):</span></th>
					<td>
						<input type="text" name="CN" style="width:90%" id="CN" placeholder="IP OR HOSTNAME" ><span style="COLOR:RED">*</span>
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
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 部门(O):</th>
					<td><input type="text" name="O" style="width:90%"></td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 邮件:</th>
					<td><input type="text" name="EMAIL" style="width:90%"></td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 网址:</th>
					<td><input type="text" name="NET" style="width:90%"></td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"><span style="color: red;">*</span>创建新密钥对:</th>
					<td>
						<select name="newKeyPair" id="newKeyPair" style="width:90%" onchange="chooseSecretKey(this)">
							<option value="1" selected="selected">是</option>
							<option value="0" >否</option>
						</select>
					</td>
				</tr>
				<tr  id="keySizeTr">
					<th style="text-align: right;font-size: 10pt;color: #004799;">密钥长度:</th>
					<td>
						<select name="keySize" id="keySize" style="width:90%">
							<option value="1024" selected="selected">1024</option>
							<option value="2048">2048</option>
							<option value="4096">4096</option>
						</select>
					</td>
				</tr>
<!-- 				<tr style="display: none" id="importPrivateKeyTr">
					<th style="text-align: right;font-size: 10pt;color: #004799;">导入密钥对文件:</th>
					<td>
						<input type="file" name="importName" >
					</td>
				</tr> -->
				<tr id="secretTr">
					<th style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>密码:</th>
					<td><input type="password" id="secret" name="secret" style="width:90%"></td>
				</tr>
				<tr id="resecretTr">
					<th style="text-align: right;font-size: 10pt;color: #004799;"> <span style="color: red;">*</span>确认密码:</th>
					<td><input type="password" id="resecret" name="resecret" style="width:90%"></td>
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
					<th style="text-align: right;font-size: 10pt;color: #004799;">签名算法:</th>
					<td>
						<select name="signAlgorithm" style="width:90%">
							<option value="SHA1WithRSA" selected="selected">SHA1WithRSA</option>
							<option value="SHA224withRSA">SHA224withRSA</option>
							<option value="SHA256WithRSA">SHA256WithRSA</option>
							<option value="SHA384withRSA">SHA384withRSA</option>
							<option value="SHA512withRSA">SHA512withRSA</option>
							<option value="MD2withRSA">MD2withRSA</option>
							<option value="MD5withRSA">MD5withRSA</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;" > 用途:</th>
					<td><input type="text" name="effect" style="width:90%"  maxlength="20"></td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 备注:</th>
					<td><input type="text" name="REMARK" style="width:90%" maxlength="100" ></td>
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
		function chooseSecretKey()
		{
			var obj = arguments[0];
			
			var val = obj.value;
			document.getElementById("keySizeTr").style.display="none";
			document.getElementById("secretTr").style.display="none";
			document.getElementById("resecretTr").style.display="none";
			
			if(val == 1)
			{
				document.getElementById("keySizeTr").style.display="";
				document.getElementById("secretTr").style.display="";
				document.getElementById("resecretTr").style.display="";
			}
		}
		
		function certapp()
		{
			var CN = document.getElementById("CN").value;
			if(null == CN || "" == CN)
			{
				alert("系统提示：名称不能为空!");
				return;
			}
			var newKeyPair = document.getElementById("newKeyPair").value;
			if(null == newKeyPair || "" == newKeyPair)
			{
				alert("系统提示：是否创建密钥对不能为空!");
				return;
			}
			
			if(newKeyPair == "1"){
				var keySize = document.getElementById("keySize").value;
				if(null == keySize || "" == keySize)
				{
					alert("系统提示：密钥长度不能为空!");
					return;
				}
			}
			var OU = document.getElementById("OU").value;
			if(null == OU || "" == OU)
			{
				alert("系统提示：公司名称不能为空!");
				return;
			}
			if(newKeyPair == "1"){
				var resecret = document.getElementById("resecret").value;
				var secret = document.getElementById("secret").value;
				
				if(null == secret || "" == secret)
				{
					alert("系统提示：密钥密码不能为空!");
					return;
				}
				
				if(null == resecret || "" == resecret)
				{
					alert("系统提示：密钥确认密码不能为空!");
					return;
				}
				
				if(secret != resecret)
				{
					alert("系统提示：两次输入的密钥密码不一致!");
					return;
				}
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
			
			var result = window.confirm("系统提示：确定提交根证书申请信息?");
			if(result)
				document.form1.submit();
		}
	</script>
</html>
