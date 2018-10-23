<%@page import="com.congoal.cert.pojo.UnCertAward"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>批量生成证书</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/web/js/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/web/js/dateUtil.js"></script>
</head>

<body onload="setup()">
	<form action="bulkImportAction.action" method="post" name="myForm">
		<div class="widget widget-table">
			<div class="widget-content">
				<table style="width: 100%;" class="pn-ftable" border="0" cellpadding="10">
				<tr  id="importPublicTr">
					<th style="text-align: center;font-size: 10pt;">创建时间:</th>
					<td class="pn-fcontent">
						<input type="text"
								id="startDate"
								name="startDate" readonly="readonly"
								value='${startDate}'
								class="Wdate"
								onclick="startDateClk($(this))"
								/>
					</td>
					<th style="text-align: center;font-size: 10pt;">至</th>
					<td class="pn-fcontent">
						<input type="text"
								id="endDate"
								name="endDate" readonly="readonly"
								value='${endDate}'
								class="Wdate"
								onclick="endDateClk($(this))"
								/>
					</td>
				</tr>
				<tr>
					<th style="text-align: center;font-size: 10pt;">证书类型:</th>
					<td class="pn-fcontent">
						<select name="ftype" style="width:200px;">
							<option value="">请选择</option>
							<option value="1" <c:if test="${ftype==1}">selected="selected"</c:if>>终端证书</option>
							<option value="2" <c:if test="${ftype==2}">selected="selected"</c:if>>服务器证书</option>
						</select>
					</td>
					<th style="text-align: center;font-size: 10pt;">创建人:</th>
					<td class="pn-fcontent">
						<input type="text" name="createOperators" value="${createOperators }" class="input"/>
					</td>
				</tr>
			</table>
			<div class="widget-bottom">
					<input type="submit" class="btn btn-s-md btn-primary pull-right"
						value="搜索"/>
					<input type="button" value="导入终端公钥" onclick="certapp('1');" 
						class="btn btn-s-md btn-primary pull-right">
					<input type="button" value="导入服务器公钥" onclick="certapp('2');" 
						class="btn btn-s-md btn-primary pull-right"/>
			</div>
		</div>
		</div>
		<div class="separator line"></div>
		<div class="widget widget-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h5>批量证书列表</h5>
			</div>
			<div class="widget-content widget-list">
					<table class="table table-striped table-bordered" width="100%">
					<tr>
						<th style="width: 55px;font-size: 10pt;">序号</th>
						<th style="font-size: 10pt;">创建时间</th>
						<th style="font-size: 10pt;">证书类型</th>
						<th style="font-size: 10pt;">公钥文件名称</th>
						<th style="font-size: 10pt;">压缩包名称</th>
						<th style="font-size: 10pt;">证书文件数</th>
						<th style="font-size: 10pt;">创建人</th>
						<th style="font-size: 10pt;">操作</th>
					</tr>
					
					<c:forEach items="${certs}" var="cert" varStatus="status">
						<tr>
							<td style="text-align: center;font-size:9pt">${status.count}</td>
							<td style="text-align: center;font-size:9pt">
								<fmt:formatDate value="${cert.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td style="text-align: center;font-size:9pt">
								<c:if test="${cert.ftype==1}">
									终端证书
								</c:if>
								<c:if test="${cert.ftype==2}">
									服务器证书
								</c:if>
							</td>
							<td style="text-align: center;font-size:9pt" width="100%">${cert.fname}</td>
							<td style="text-align: center;font-size:9pt;">
								${cert.downFname}
							</td>
							<td style="text-align: center;font-size:9pt">
								${cert.count}
							</td>
							<td style="text-align: center;font-size:9pt">${cert.createOperators}</td>
							<td style="text-align: center;font-size:9pt">
								<a href="#" onclick="javascript:cerFileDownload('${cert.id}');return false;" style="text-decoration: none;">下载</a>&nbsp;
							</td>
						</tr>
					</c:forEach>
					<tr>
					</table>
					<div class="widget-bottom">
   		 				<jsp:include page="../include/pager.jsp" />
   		 			</div>
				</div>
			</div>
	</form>
</body>
<script type="text/javascript">
	/**
	 * 导入公钥
	 * arguments[0]
	 */
	function certapp()
	{	
		if (arguments.length != 1) {
			alert("参数错误!");
			return;
		}
		var flag = arguments[0];
		location.href="exportBulkFileAction.action?ftype="+flag;
	}
	
	/**
	*cer证书下载
	*/
	function cerFileDownload(obj)
	{
		var id = obj;
		//alert('下载');
		location.href="cerDownloadAction.action?bulk=true&id="+id;
	};
</script>
</html>
