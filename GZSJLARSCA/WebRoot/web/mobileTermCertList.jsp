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

<title>终端证书查询列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/web/js/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/web/js/dateUtil.js"></script>
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
	/**
     * 冻结证书
	 */
	function lockCert() {
		if (arguments.length != 2) {
			alert("参数错误!");
			return;
		}
		
		var certid = arguments[0];
		var cname = arguments[1];
		var result = window.confirm("确定要冻结证书 "+cname+" 吗?");
		if (result) {
			location.href="certLockAction.action?cid="+certid+"&type=mobile";
		}
		
	}
	/**
	 * 启用证书
	 */
	function unlockCert() {
		if (arguments.length != 2) {
			alert("参数错误!");
			return;
		}
		
		var certid = arguments[0];
		var cname = arguments[1];
		var result = window.confirm("确定要启用证书 "+cname+" 吗?");
		if (result) {
			location.href="certunLockAction.action?cid="+certid+"&type=mobile";
		}
	}
	/**
	 * 注销证书
	 */
	function disableCert() {
		if (arguments.length != 2) {
			alert("参数错误!");
			return;
		}
		
		var certid = arguments[0];
		var cname = arguments[1];
		var result = window.confirm("证书注销后不能恢复，确定要注销证书 "+cname+" 吗?");
		if (result) {
			location.href="disabledCertAppAction.action?cid="+certid+"&type=mobile";
		}
	}
	
	/**
	*cer证书下载
	*/
	function cerFileDownload(obj)
	{
		var id = obj;
		//alert('下载');
		location.href="cerDownloadAction.action?id="+id;
	};
</script>
</head>

<body>

	<form action="findDevSignCertAction.action" method="post" name="myForm">
		<div class="widget widget-table">
			<div class="widget-content">
				<table style="width: 100%;" class="pn-ftable" border="0" cellpadding="10">
				<tr>
					<th style="text-align: center;font-size: 10pt;">创建时间:</th>
					<td  class="pn-fcontent">
						<input type="text"
								id="startDate"
								name="startDate" readonly="readonly"
								value='${startDate}'
								class="Wdate"
								onclick="startDateClk($(this))"
								/>
					</td>
					<th style="text-align: center;font-size: 10pt;">至</th>
					<td  class="pn-fcontent">
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
					<th style="text-align: center;font-size: 10pt;">证书状态:</th>
					<td  class="pn-fcontent" colspan="3">
						<select name="status" style="width:200px;">
							<option value="">请选择</option>
							<option value="1" <c:if test="${status==1}">selected="selected"</c:if>>启用</option>
							<option value="2" <c:if test="${status==2}">selected="selected"</c:if>>注销</option>
							<option value="3" <c:if test="${status==3}">selected="selected"</c:if>>冻结</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="widget-bottom">
					<input type="submit" class="btn btn-s-md btn-primary pull-right"
						value="搜索"/>

			</div>
			</div>
		</div>
		<div class="separator line"></div>
		<div class="widget widget-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h5>终端证书列表</h5>
			</div>
				<div class="widget-content widget-list">
					<table class="table table-striped table-bordered">
					<tr>
						<th style="width: 55px;font-size: 10pt;">序号</th>
						<th style="font-size: 10pt;">序列号</th>
						<th style="font-size: 10pt;">证书类型</th>
						<th style="font-size: 10pt;">证书文件名</th>
						<th style="font-size: 10pt;">状态</th>
						<th style="font-size: 10pt;">创建时间</th>
						<th style="font-size: 10pt;">创建人</th>
						<th style="font-size: 10pt;">操作</th>
					</tr>
					
					<c:forEach items="${SignCertList}" var="ua" varStatus="status">
						<tr>
							<td style="text-align: center;font-size:9pt">${status.count}</td>
							<td style="text-align: center;font-size:9pt">
								${fn:replace(ua.serialnumber, " ", "")}
							</td>
							<td style="text-align: center;font-size:9pt">
								<c:if test="${ua.ctype==4}">
									终端证书
								</c:if>
								<c:if test="${ua.ctype==5}">
									服务器证书
								</c:if>
							</td>
							<td style="text-align: center;font-size:9pt">${ua.fname}</td>
							<td style="text-align: center;font-size:9pt;">
								<c:choose>
									<c:when test="${ua.status==1}">
										启用
									</c:when>
									<c:when test="${ua.status==2}">
										注销
									</c:when>
									<c:otherwise>
										冻结
									</c:otherwise>
								</c:choose>
							</td>
							<td style="text-align: center;font-size:9pt">
								<fmt:formatDate value="${ua.awardDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td style="text-align: center;font-size:9pt">${ua.signOperators}</td>
							<td style="text-align: center;font-size:9pt">
								<a href="#" onclick="javascript:cerFileDownload('${ua.id}');return false;" style="text-decoration: none;">下载</a>&nbsp;
								<c:if test="${ua.status==1}">
									<a href="#" onclick="javascript:lockCert('${ua.id}', '${ua.fname}');return false;" style="text-decoration: none;">冻结</a>&nbsp;
									<a href="#" onclick="javascript:disableCert('${ua.id}', '${ua.fname}');return false;" style="text-decoration: none;">注销</a>
								</c:if>
								<c:if test="${ua.status==3}">
									<a href="#" onclick="javascript:unlockCert(${ua.id}, '${ua.fname}');return false;" style="text-decoration: none;">启用</a>&nbsp;
									<a href="#" onclick="javascript:disableCert('${ua.id}', '${ua.fname}');return false;" style="text-decoration: none;">注销</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					</table>
					<div class="widget-bottom">
   		 				<jsp:include page="include/pager.jsp" />
   		 			</div>
   		 		</div>
		</div>
	</form>
</body>
</html>
