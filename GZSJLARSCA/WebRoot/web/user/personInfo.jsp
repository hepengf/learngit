<%@ page language="java" import="java.text.*,java.util.*,com.congoal.cert.pojo.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>个人信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.automaxlength.js"></script>
<script type="text/javascript">
	function aler()
	{
		var errMsg=$("#resultMsg").val();
		if(errMsg!="")
		{		
			alert(errMsg);
		}
	}
	window.onload=aler;	
</script>
<script type="text/javascript">
function check(){
	var password = document.getElementById("password").value;
	var pwreg=password.match(/^\w{6,50}$/);
	var telephone = document.getElementById("telephone").value;
	var telephonereg=telephone.match(/^(\(\d{3,4}\)|\d{3,4}-)?\d{10,12}$/);
	var fax = document.getElementById("fax").value;
	var address = document.getElementById("address").value;
	var engname = document.getElementById("engname").value;
	var engnamereg=engname.match(/^\w{0,20}$/);
	var zipcode = document.getElementById("zipcode").value;
	var zipcodereg=zipcode.match(/^\w{0,10}$/);
	var orgid = document.getElementById("orgid").value;
	var staffNo = document.getElementById("staffNo").value;
	var staffNoreg=staffNo.match(/^\w{0,20}$/);
	var phone = document.getElementById("phone").value;
	var phonereg=phone.match(/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/);
	if($('#password').val() != ""){
		 if(!pwreg)
			{
				alert("系统提示：密码格式错误!");
				return;
			}
	}
	if($("#cpassword").val() != $("#password").val()){
		alert("两次密码不一致！");
		return false;
	}
	if(telephone!=null && !(telephone.trim() == "")&&!telephonereg)
	{
		alert("系统提示：输入的手机号码错误!");
		return ;
	}
	if(phone!=null && !(phone.trim() == "")&&!phonereg)
	{
		alert("系统提示：输入的固定电话错误!");
		return ;
	}
	if(fax.length>15)
	{
		alert("系统提示：输入的传真错误!");
		return ;
	}
	if(address.length>50)
	{
		alert("系统提示：地址长度超出范围!");
		return ;
	}
	if(!engnamereg)
	{
		alert("系统提示：输入的英文名错误!");
		return ;
	}
	if(!zipcodereg)
	{
		alert("系统提示：邮编错误!");
		return ;
	}
	if(orgid.length>20)
	{
		alert("系统提示：机构名字长度超出范围!");
		return ;
	}
	if(!staffNoreg)
	{
		alert("系统提示：输入的员工编号错误!");
		return ;
	}
	$("#myForm").submit();
}
</script>
</head>
		
<body> 
		<form method="post" id="myForm" action="${basePath}/web/modUserAction.action">
			<div class="widget widget-edit">
				<div class="widget-content">
					<table class="pn-ftable table-bordered" border="0" cellpadding="10">
						<tbody>
							<tr>
								<th style="width:30%;text-align: center;font-size: 10pt;color: #004799;">登&nbsp;录&nbsp;名: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" readonly="readonly" name="name" value="${user.name}" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">真实姓名: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" name="fullname" value="${user.fullname}" readonly="readonly" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">密&nbsp;&nbsp;&nbsp;&nbsp;码: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="password" placeholder="请输入1到50位由字母、数字、下划线组成的密码" name="password" id="password" style="width: 360px;"/>(若不修改则为空)
								</td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">重复密码: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="password" placeholder="请输入重复密码" name="cpassword" id="cpassword" style="width: 360px;"/>(若不修改则为空)
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">手机号码: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" id="telephone" name="telephone" value="${user.telephone}" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">固定电话: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" id="phone" name="phone" value="${user.phone}" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">传&nbsp;&nbsp;&nbsp;&nbsp;真:&nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" value="${user.fax}" id="fax" name="fax" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">地&nbsp;&nbsp;&nbsp;&nbsp;址: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" maxlength="50" name="address" id="address" value="${user.address}" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">英文名: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" maxlength="60" name="engname" id="engname" value="${user.engname}" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">邮&nbsp;&nbsp;&nbsp;&nbsp;编: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" value="${user.zipcode}" id="zipcode" name="zipcode" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;">机&nbsp;&nbsp;&nbsp;&nbsp;构: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" value="${user.orgid}" id="orgid" name="orgid" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
							<tr>
								<th style="text-align: center;font-size: 10pt;color: #004799;" >员工编号: &nbsp;</th>
								<td class="pn-fcontent">
									<input type="text" maxlength="20" value="${user.staffNo}" id="staffNo" name="staffNo" style="width: 360px;"/>
								</td>
								<td class="pn-info"></td>
							</tr>
						</tbody>
					</table>
					
					<div class="widget-bottom">
						<input type="hidden" name="MsgName" id="resultMsg" value="${resultMsg }" />
						<input type="hidden" name="id" value="${user.id}"/>
						<a onclick="check()" href="javascript:void(0);"
							class="btn btn-primary pull-right">保 存</a>
					</div>
				</div>
			</div>
		</form>
</body>
</html>
