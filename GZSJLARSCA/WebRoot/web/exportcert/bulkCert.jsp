<%@page import="com.congoal.cert.pojo.UnCertAward"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>证书申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript">
if ('${message}' != '') {
	alert('${message}');
}
function check(){
	var filename = jQuery.trim($("#props").val());
	$("#propsFileName").val(filename);
	var ftype = jQuery.trim($("#ftype").val());
	if(filename == ""){
		alert("请选择需导入的公钥文件 ");
		return;
	}
	var position = filename.lastIndexOf("/");
	if(position == -1){
	   position = filename.lastIndexOf("\\");
	}
	filename = filename.substr(position +1);
	var extension = filename.substr(filename.lastIndexOf(".")+1);
	var name = filename.substring(position,filename.lastIndexOf(".")-1);
	var flag = filename.split("_");
	extension=extension.toLowerCase();
	if(!(("xls"==extension)||("xlsx"==extension)))
	{
		alert("导入的不是Excel文件，请重新选择！");
		return;
	} 
	if(flag.length!=3){
		alert("公钥文件格式有误，请重新选择！");
		return;
	}
	document.myForm.submit();
}
</script>
</head>
<body>
	<form action="subSevCertAppAction.action?ftype=<%=request.getAttribute("ftype") %>" method="post" name="myForm" enctype="multipart/form-data">
					<input type="hidden"  value="${ftype}" name="ftype" id="ftype"/>
		<div class="widget widget-table">
			<div class="widget-content">
				<table class="pn-ftable" border="0" cellpadding="10">
					<tbody>
						<tr>
							<th>
								<c:if test="${ftype==1}">
									终端公钥文件
								</c:if>
								<c:if test="${ftype==2}">
									服务器公钥文件
								</c:if>
							</th>
							<td class="pn-fcontent" width="600px" height="25px">
								<input style="width: 480px; float:left; font-size: 13px; height: 25px" type="file" name="props" id="props"/>
								&nbsp;&nbsp;<label style="color: red; font-family: cursive;">请选择&nbsp;EXCEL&nbsp;文件</label>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="widget-bottom">
					<input type="button" value="导入" class="btn btn-s-md btn-primary pull-right" 	onclick="check();">
				</div>
			</div>
		</div>
	</form>
</body>
</html>
