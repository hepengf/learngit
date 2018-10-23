<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>证书注销列表</title>
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
/**
*注销列表下载
*/
function crlDownload(obj)
{
	var id = obj;
	//alert('下载');
	location.href="crlDownloadAction.action?id="+id;
};
function startDateClkyd(obj){
		var dateStr = "%y-%M-%d";
		if(jQuery.trim(obj.val()) != ""){
			dateStr = obj.val();
		}
		WdatePicker({startDate:dateStr,alwaysUseStartDate:true,readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'});
	}
function endDateClkyd(obj){
		var dateStr = "%y-%M-%d";
		if(jQuery.trim(obj.val()) != ""){
			dateStr = obj.val();
		}
		WdatePicker({startDate:dateStr,alwaysUseStartDate:true,readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'});
	}
</script>
</head>

<body>
	<form method="post" action="certCrlCheckAction" name="myForm">
		<div class="widget widget-table">
			<div class="widget-content">
				<table style="width: 100%;" class="pn-ftable" border="0" cellpadding="10">
					<tr>
						<th style="text-align: center;font-size: 10pt;">注销日期</th>
						<td  class="pn-fcontent">
						<input type="text"
								id="startDate"
								name="startDate" readonly="readonly"
								value='${startDate}'
								class="Wdate"
								onclick="startDateClkyd($(this))"
								/>
						</td>
						<th style="text-align: center;font-size: 10pt;">至</th>
						<td  class="pn-fcontent">
							<input type="text"
									id="endDate"
									name="endDate" readonly="readonly"
									value='${endDate}'
									class="Wdate"
									onclick="endDateClkyd($(this))"
									/>
						</td>
					</tr>
				</table>
				<div class="widget-bottom">
					<input type="submit" class="btn btn-s-md btn-primary pull-right"
						value="搜索"/>
				</div>
			</div>
			<div class="separator line"></div>
				<div class="widget widget-table">
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h5>注销文件列表</h5>
					</div>
					<div class="widget-content widget-list">	
						<table style="width: 100%;" class="table table-striped table-bordered">
							<tr>
								<th style="width: 35px;font-size: 10pt;">序号</th>
								<th style="font-size: 10pt;text-align: center;">签发者</th>
								<th style="font-size: 10pt;text-align: center;">文件名</th>
								<th style="font-size: 10pt;text-align: center;">注销日期</th>
								<th style="font-size: 10pt;text-align: center;">下次更新日期</th>
								<th style="font-size: 10pt;text-align: center;">状态</th>
								<th style="font-size: 10pt;text-align: center;">操作</th>
							</tr>
									
							<c:forEach items="${crls}" var="item" varStatus="status">
							
								<tr>
									<td style="text-align: center;font-size:9pt">${status.count}</td>
									<td style="text-align: left;font-size:9pt">${item.issuredn}</td>
									<td style="text-align: center;font-size:9pt">${item.fname}</td>
									<td style="text-align: center;font-size:9pt"><fmt:formatDate value="${item.awardDate}" type="date"/></td>
									<td style="text-align: center;font-size:9pt"><fmt:formatDate value="${item.nextUpdateTime}" type="date"/></td>
									<td style="text-align: center;font-size:9pt">
										<c:choose>
											<c:when test="${item.status == 1}">
												通过
											</c:when>
											<c:when test="${item.status == 2}">
												拒绝
											</c:when>
											<c:otherwise>
												待审核
											</c:otherwise>
										</c:choose>
									</td>
									<td style="text-align: center;font-size:9pt">
										<c:if test="${item.status == 1}">
											<a href="#" onclick="javascript:crlDownload('${item.id}');return false;" style="text-decoration: none;">下 载</a>
										</c:if>
										&nbsp;
										<c:if test="${item.status == 1}">
											<a href="#" onclick="javascript:location.href='revokedCertListAction.action?id=${item.id}';return false;" style="text-decoration: none;">注销证书明细</a>
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
   		</div>
	</form>
</body>
</html>
