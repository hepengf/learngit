<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>

		<title>添加用户</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/web/js/common.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/web/css/common.css"/>
	</head>

	<body>
  		
		<form action="<%=request.getContextPath() %>/web/addUserAction.action" method="post" name="userform">
				<table style="width: 450px;" class="imagetable" >
					<tr>
						<th style="width:150px;text-align: right;font-size: 10pt;color: #004799;">
						<span style="COLOR:RED">*</span> 用户名</th>
						
						<td>
						<input type="text" name="name" id="name" style="width:100%" /></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 
						<span style="COLOR:RED">*</span>密  码
						</th>
						<td><input type="password" name="password" id="password"  style="width:100%" value="123456"/></td>
					</tr>
						<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 
						<span style="COLOR:RED">*</span> 确认密码
						</th>
						<td><input type="password" name="cpassword" id="cpassword" style="width:100%" value="123456"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;">
						<span style="COLOR:RED">*</span> 
						 真实姓名</th>
						<td><input type="text" name="fullname" id="fullname" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 英文名</th>
						<td><input type="text" name="engname" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 员工编号</th>
						<td><input type="text" name="staffNo" id="staffNo" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-固话</th>
						<td><input type="text" name="phone" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-手机</th>
						<td><input type="text" name="telephone" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系方式-传真</th>
						<td><input type="text" name="fax" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 联系地址</th>
						<td><input type="text" name="address" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 邮编</th>
						<td><input type="text" name="zipcode" style="width:100%"/></td>
					</tr>
					<tr>
						<th style="text-align: right;font-size: 10pt;color: #004799;"> 所属机构</th>
						<td><input type="text" name="orgid" style="width:100%"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="提交" onclick="adduer()" class="button gray"/>&nbsp;&nbsp;
							<input type="reset" value="重置" class="button gray"/>&nbsp;&nbsp;       
							<input value="返  回" type="button" onclick="javascript:history.back();" class="button gray"/>
						</td>
					</tr>
				</table>
		</form>
	</body>
		<script type="text/javascript">
			$(document).ready(function() {
				
			});
			
			function adduer()
			{
				var name = document.getElementById("name").value;
				var password = document.getElementById("password").value;
				var cpassword = document.getElementById("cpassword").value;
				var fullname = document.getElementById("fullname").value;
				if(name==null || name.trim() == "")
				{
					alert("系统提示：登陆名不能为空!");
					return ;
				}
				if(fullname==null || fullname.trim() == "")
				{
					alert("系统提示：真实姓名不能为空!");
					return ;
				}
				if(password==null || password.trim() == "")
				{
					alert("系统提示：密码不能为空!");
					return ;
				}
				if(cpassword==null || cpassword.trim() == "")
				{
					alert("系统提示：确认密码不能为空!");
					return ;
				}else
				{
					if(password.trim() != cpassword.trim())
					{
						alert("系统提示：两次输入的密码不一致!");
						return ;
					}
				}
				
				var result = window.confirm("系统提示：确定要提交用户");
				if(result)
				{
					document.userform.submit();
				}
			}
		</script>
	
</html>
