<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>黔通卡空中充值自建CA认证系统</title>
<link href="web/css/login.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="web/js/common.js"></script>
<script type="text/javascript">
	function formSubmit() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;

		if (null == username || "" == username.trim()) {
			alert("系统提示：登录名不能为空!");
			return false;
		}

		if (null == password || "" == password.trim()) {
			alert("系统提示：登录密码不能为空!");
			return false;
		}

		return true;

	}
	//判断当前页面是否存在iframe之内,是则跳出iframe重定向至登录页
	function checkInIframe(){
		var inFlag = false;
		if(self!=top   &&   parent.document.body.tagName   ==   "FRAMESET")     //普通框架 
		{ 
			inFlag = true;
		} 

		if(self!=top   &&   parent.document.body.tagName   ==   "BODY")     //Iframe框架 
		{ 
			inFlag = true;
		} 
		
		if(inFlag)
		{
			parent.document.location = "<%=request.getContextPath() %>/login.jsp";
		}

	}
</script>

</head>

<body onload="checkInIframe()">
<div class="center" onload="javascript:document.getElementById('username').focus();">
	<div class="header">
		<div class="lg1"></div>
		<div class="lg2"></div>
	</div>
	<div class="content">
		<div class="lg3">
			<div class="lg3_1"></div>
			<div class="lg3_2"></div>
		</div>
		<div class="lg4">
			<div class="con_login1">
			</div>
			<div class="con_login2">
				<div class="left_login2"></div>
				<div class="righg_login2">
				<div style="min-height: 27px;">
					<%
						if (null != request.getParameter("error")) {
							String message = request.getParameter("error");
							String desc = "";
							if("expired".equals(message))
							{
								desc = "用户会话数已超出限制!";
							}
							if("invalid".equals(message))
							{	
								desc = "会话超时!";
							}
							if("sessionId".equals(message))
							{	
								desc = "SessionId为空,请重新登录!";
							}
							
							if(!"".equals(desc)){
					%>
								<label style="font-size: 11px;">登录失败,请重试...</label><br/><label style="font-size: 11px;">错误原因:<%=desc %></label>
					<%
							}else
							{
					%>
								<label style="font-size: 11px">登录失败,请重试...</label>
								<br/><label style="font-size: 11px">错误原因:${SPRING_SECURITY_LAST_EXCEPTION.message}</label>
					<%		}
						}
					%>
					</div>
					<form action="j_spring_security_check" method="post" onsubmit="return formSubmit()">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td width="63" height="27"><div align="right">用户名：</div></td>
							<td><input type="text" name="username" id="username" class="lg_input" /></td>
						  </tr>
						  <tr>
							<td width="63" height="27"><div align="right">密&nbsp;&nbsp;&nbsp;码：</div></td>
							<td><input type="password" name="password" id="password" class="lg_input" /></td>
						  </tr>
						  <tr>
							<td width="63" height="15">&nbsp;</td>
							<td>
							  <button type="submit" name="Submit" style="width: 85px" class="lg_buttom">&nbsp;密码登录</button>
							  <button type="reset" name="Submit2" style="width: 85px" class="lg_buttom1">重置</button>
						    </td>
						  </tr>
					  </table>

					</form>
				</div>
			</div>
			<div class="con_login3"></div>
		</div>
		<div class="lg5">
			<div class="lg5_1"></div>
			<div class="lg5_2"></div>
	</div>
	<div class="footer">
		<div class="foot_1"></div>
		<div class="foot_2"></div>
	</div>
</div>
</body>
</html>
