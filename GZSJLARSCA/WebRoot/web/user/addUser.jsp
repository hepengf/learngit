<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.automaxlength.js"></script>
<script type="text/javascript">
if ('${message}' != '') {
	alert('${message}');
}
</script>
</head>

	<body>
  		
		<form action="${basePath}/web/addUserAction.action" method="post" name="userform">
				<div class="widget widget-edit">
				<div class="widget-content">
					<table class="pn-ftable table-bordered" border="0" cellpadding="10">
						<tr>
							<th style="width:150px;text-align: right;font-size: 10pt;color: #004799;">
							<span style="COLOR:RED">*</span> 用户名</th>
							
							<td class="pf-content">
							<input type="text" name="name" maxlength="20" id="name" style="width:100%" value="${newUser.name }" placeholder="请输入6到20位由字母、数字、下划线组成的用户名"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 
							<span style="COLOR:RED">*</span>密  码
							</th>
							<td class="pf-content"><input type="password" name="password" id="password"
							  placeholder="请输入1到50位由字母、数字、下划线组成的密码，若不输入默认密码为:123456" style="width:100%"></td>
						</tr>
							<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 
							<span style="COLOR:RED">*</span> 确认密码
							</th>
							<td class="pf-content"><input type="password" placeholder="请输入确认密码，若不输入采用默认密码" name="cpassword" id="cpassword" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;">
							<span style="COLOR:RED">*</span> 
							 真实姓名</th>
							<td class="pf-content"><input type="text" maxlength="50" value="${newUser.fullname }" name="fullname" id="fullname" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 英文名</th>
							<td class="pf-content"><input type="text" maxlength="60" id="engname" value="${newUser.engname }" name="engname" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 员工编号</th>
							<td class="pf-content"><input type="text" maxlength="20" value="${newUser.staffNo }" id="staffNo" name="staffNo" id="staffNo" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-固话</th>
							<td class="pf-content"><input type="text" value="${newUser.phone }" id="phone" name="phone" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-手机</th>
							<td class="pf-content"><input type="text" value="${newUser.telephone }" id="telephone" name="telephone" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-传真</th>
							<td class="pf-content"><input type="text" value="${newUser.fax }" id="fax" name="fax" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系地址</th>
							<td class="pf-content"><input type="text" maxlength="50" value="${newUser.address }" id="address" name="address" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 邮编</th>
							<td class="pf-content"><input type="text" value="${newUser.zipcode }" id="zipcode" name="zipcode" style="width:100%"></td>
						</tr>
						<tr>
							<th style="text-align: right;font-size: 10pt;color: #004799;"> 所属机构</th>
							<td class="pf-content"><input type="text" maxlength="20" value="${newUser.orgid }" id="orgid" name="orgid" style="width:100%"></td>
						</tr>
					</table>
					<div class="widget-bottom">
						<input type="button" value="提交" onclick="adduer()" class="btn btn-primary pull-right">&nbsp;&nbsp;
						<input type="reset" value="重置" class="btn btn-primary pull-right">&nbsp;&nbsp;       
						<input value="返  回" type="button" onclick="javascript:history.back();" class="btn btn-primary pull-right">
					</div>
				</div>
			</div>
		</form>
	</body>
		<script type="text/javascript">
			$(document).ready(function() {
				
			});
			
			function adduer()
			{ 
				var name = document.getElementById("name").value;
				var cpassword = document.getElementById("cpassword").value;
				var fullname = document.getElementById("fullname").value;
				var password = document.getElementById("password").value;
				var engname = document.getElementById("engname").value;
				var staffNo = document.getElementById("staffNo").value;
				var phone = document.getElementById("phone").value;
				var telephone = document.getElementById("telephone").value;
				var fax = document.getElementById("fax").value;
				var address = document.getElementById("address").value;
				var zipcode = document.getElementById("zipcode").value;
				var orgid = document.getElementById("orgid").value;
				var pwreg=password.match(/^\w{0,50}$/);
				var namereg=name.match(/^\w{6,20}$/);
				var engnamereg=engname.match(/^\w{0,20}$/);
				var staffNoreg=staffNo.match(/^\w{0,20}$/);
				var phonereg=phone.match(/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/);
				var telephonereg=telephone.match(/^(\(\d{3,4}\)|\d{3,4}-)?\d{10,12}$/);
				var zipcodereg=zipcode.match(/^\w{0,10}$/);
				
				if(name==null || name.trim() == "")
				{
					alert("系统提示：用户名不能为空!");
					return ;
				}
				if(!namereg)
				{
					alert("系统提示：用户名格式错误!");
					return;
				}
 				if(!pwreg)
				{
					alert("系统提示：密码格式错误!");
					return;
				}
				if(password.trim() != cpassword.trim())
				{
					alert("系统提示：两次输入的密码不一致!");
					return ;
				}				
				if(fullname==null || fullname.trim() == "")
				{
					alert("系统提示：真实姓名不能为空!");
					return ;
				}
				if(fullname.length>50)
				{
					alert("系统提示：真实姓名长度超出范围!");
					return ;
				}
				if(!engnamereg)
				{
					alert("系统提示：输入的英文名错误!");
					return ;
				}
				if(!staffNoreg)
				{
					alert("系统提示：输入的员工编号错误!");
					return ;
				}
				if(phone!=null && !(phone.trim() == "")&&!phonereg)
				{
					alert("系统提示：输入的固定电话错误!");
					return ;
				}
				if(telephone!=null && !(telephone.trim() == "")&&!telephonereg)
				{
					alert("系统提示：输入的手机号码错误!");
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
				var result = window.confirm("系统提示：确定要提交用户");
				
				if(result)
				{
					document.userform.submit();
				}
			}
		</script>
	
</html>
