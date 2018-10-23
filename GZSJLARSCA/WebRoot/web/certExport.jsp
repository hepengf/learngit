<%@page import="com.congoal.cert.pojo.UnCertAward"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>根证书导入</title>
<link href="<%=request.getContextPath() %>/web/css/common.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/web/js/jquery.min.js" type="text/javascript"></script>
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
	<div style="width:100%;text-align:  center;">
		<form action="subSevCertAppAction.action" method="post" name="form1" enctype="multipart/form-data">
			<table style="width: 100%;" class="imagetable">
				<tr>
					<th style="text-align: center;font-size: 10pt;color: #004799; width: 200px;" colspan="2">根证书文件:</th>
					<td style="text-align: center;">
						<input type="file" name="props" id="props">
					</td>
					<td colspan="2" style="text-align: center;">
						<input type="button" value="导入" onclick="certapp()" class="button gray">
<!-- 						&nbsp;&nbsp;<input type="reset" value="重置" class="button gray">&nbsp;&nbsp; -->
<!-- 						<input type="button" value="返回" onclick="javascript:history.back();"> -->
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	function certapp()
	{	
		var result = window.confirm("系统提示：确定提交证书申请信息?");
		if(result)
			document.form1.submit();
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
</html>
