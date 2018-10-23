<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>根证书信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/web/js/jquery.automaxlength.js"></script>
<script type="text/javascript">		
	function download(){
		document.location.href="rootDownloadAction.action?web=1";
	}
</script>
</head>
		
<body> 
	<div style="width:100%;text-align: center;">
		<form method="post" name="myform" action="${basePath}/web/modUserAction.action">
			<div class="widget widget-edit">
				<div class="widget-content">
					<table class="pn-ftable table-bordered" border="0" cellpadding="10">
						<tbody>
							<tr>
								<th style="text-align: center;color: #004799;">证书版本号: &nbsp;</th>
								<td class="pn-fcontent">
									V${version}
								</td>
								<td class="pn-info"></td>
							</tr>
<%-- 							<tr>
								<th style="text-align: center;color: #004799;">证书类型: &nbsp;</th>
								<td class="pn-fcontent">
									${type}
								</td>
								<td class="pn-info"></td>
							</tr> --%>
							<tr>
								<th style="text-align: center;color: #004799;">证书颁发者: &nbsp;</th>
								<td class="pn-fcontent">
									${issureDN}
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;color: #004799;">证书主体: &nbsp;</th>
								<td class="pn-fcontent">
									${subjectDN}
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;color: #004799;">序列号: &nbsp;</th>
								<td class="pn-fcontent">
									${serialNumber}
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;color: #004799;">签名算法: &nbsp;</th>
								<td class="pn-fcontent">
									${sigAlgName}
								</td>
								<td class="pn-info"></td>
							</tr>
<%-- 							<tr>
								<th style="text-align: center;color: #004799;">签名算法ID: &nbsp;</th>
								<td class="pn-fcontent">
									${sigAlgOID}
								</td>
								<td class="pn-info"></td>
							</tr> --%>
							<tr>
								<th style="text-align: center;color: #004799;">证书生效时间: &nbsp;</th>
								<td class="pn-fcontent">
									<fmt:formatDate value="${notBefore}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;color: #004799;">证书截止时间: &nbsp;</th>
								<td class="pn-fcontent">
									<fmt:formatDate value="${notAfter}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;color: #004799;">有效期: &nbsp;</th>
								<td class="pn-fcontent">
									${duetime}年
								</td>
								<td class="pn-info"></td>
							</tr>
						</tbody>
					</table>
					
					<div class="widget-bottom">
						<input type="button" onclick="download();" 
							class="btn btn-primary pull-right" value="下载"/>
				</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
