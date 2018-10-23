<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>证书申请</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/province.js"></script>
</head>

<body onload="setup()">
	<!-- 
	CN（Common Name - 名字与姓氏） 
	OU（Organization Unit - 组织单位名称） 
	O（Organization - 组织名称） 
	L（Locality - 城市或区域名称） 
	ST（State - 州或省份名称） 
	C（Country - 国家名称）
	 -->
	<div style="width:100%;text-align:  left;">
	<form action="certAppAction.action" method="post" name="form1" enctype="multipart/form-data">
			<table style="width: 500px;" class="imagetable">
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 国家(C):</th>
					<td>
<!-- 					<input type="text" name="C" style="width:90%"> -->
						<select name="C" style="width:90%">
							<option value="CN"  >中国</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 省份(ST):</th>
					<td>
<!-- 					<input type="text" name="ST" style="width:90%"> -->
						<select id="ST" name="ST" style="width:90%"></select> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 城市(L):</th>
					<td>
<!-- 					<input type="text" name="L" style="width:90%"> -->
						<select id="L" name="L" style="width:90%"></select> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 公司(OU):</th>
					<td><input type="text" name="OU" style="width:90%"></td>
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
				<tr  id="importPublicTr">
					<th style="text-align: right;font-size: 10pt;color: #004799;">导入公钥文件:</th>
					<td>
						<input type="file" name="props" id="props">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;font-size: 10pt;color: #004799;"> 证书期限:</th>
					<td>
						<select name="dueTime" style="width:90%">
							<option value="1" selected="selected">一年</option>
							<option value="2">二年</option>
							<option value="3">三年</option>
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
				
<!-- 				<tr> -->
<!-- 					<th style="text-align: right;font-size: 10pt;color: #004799;">签发的根证书:</th> -->
<!-- 					<td> -->
<!-- 						<select id="rootCert" name="rootCert" style="width:90%"> -->
<!-- 							<c:forEach items="${certs}" var="item" varStatus="status"> -->
<!-- 								<option value="${item.id}" >${item.issuredn}</option> -->
<!-- 							</c:forEach> -->
<!-- 						</select> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				
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
<!-- 						&nbsp;&nbsp;<input type="reset" value="重置" class="button gray">&nbsp;&nbsp; -->
<!-- 						<input type="button" value="返回" onclick="javascript:history.back();"> -->
					</td>
				</tr>
			</table>
	</form>
	</div>
</body>
<script>
	var keySizeTr = document.getElementById("keySizeTr");
	var choosePublicKeyTr = document.getElementById("choosePublicKeyTr");
	var importPublicTr= document.getElementById("importPublicTr");
	var inputPublicKeyTr= document.getElementById("inputPublicKeyTr");
	
	function chooseSecretKey()
	{
		var obj = arguments[0];
		var val = obj.value;
		choosePublicKeyVal="none";
		keySizeTr.style.display="none";
		choosePublicKeyTr.style.display="none";
		
		if(val == 1)
		{
			keySizeTr.style.display="";
			
		}
		else
		{
			choosePublicKeyTr.style.display="";
			inputPublicKeyTr.style.display="";
			choosePublicKeyVal = "input";
		}
	}
	
	function chooseCreatePublicKey()
	{
		var obj = arguments[0];
		var val = obj.value;
		
		importPublicTr.style.display="none";
		inputPublicKeyTr.style.display="none";
		
		if(val == "import")
		{
			importPublicTr.style.display="";
			choosePublicKeyVal = "import";
		}else
		{
			inputPublicKeyTr.style.display="";
			choosePublicKeyVal = "input";
		}
	}
</script>
<script type="text/javascript">
	var choosePublicKeyVal="none";
	function certapp()
	{
		
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
		
		var result = window.confirm("系统提示：确定提交证书申请信息?");
		if(result)
			document.form1.submit();
	}
</script>
</html>
