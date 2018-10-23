<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>空中充值基础支撑系统自建CA认证平台</title>
<link href="${basePath}/web/css/index.css" rel="stylesheet" type="text/css">
<link href="${basePath}/web/css/index_left.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${basePath}/web/js/jquery.js"></script>
<script type="text/javascript" src="${basePath}/web/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="${basePath}/web/js/jquery.easing.js"></script>
<script type="text/javascript" src="${basePath}/web/js/jquery.accordion.js"></script>
</head>
<%
	String loginTime = request.getAttribute("loginTime")+"";
%>
<body  >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td colspan="3" valign="top" height="100" align="left" width="100%">
<table width="100%" border="0" cellspacing="0" cellpadding="0"  id="index_content">
  <tr>
    <td id="title">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        	<td width="100%" >
        	<span style="font-family:'Times New Roman';color: white;font-weight: bold;width: 20%;">
        	&nbsp;CA认证
        	</span>
        	</td>
        </tr>
    </table>
	</td>
  </tr>
  
<!--   <tr> -->
<!--     <td height="3" bgcolor="#044aa0"></td> -->
<!--   </tr> -->
  
  <tr>
    <td height="27" >
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="27" background="web/img/index_title_nav_bg.gif" width="450px">
			　　<span class="userNameStyle" style="font-family:'Times New Roman'">
				<%
					User user = (User)session.getAttribute("user");
				    if(null == user)
				    {
				    	response.sendRedirect(request.getContextPath()+"/login.jsp");
				    }
				%>
				<%=user.getFullname() %>&nbsp;&nbsp;欢迎您!</span>
			　　<span class="telStyle" style="font-family:'Times New Roman'">支持热线(020-81000000)</span>　
				<span class="helpStyle" style="font-family:'Times New Roman'">使用帮助</span>　
			    <td width="37"><img src="web/img/index_title_nav_but.jpg" width="37" height="27"></td>
			    <td  background="web/img/index_title_nav_bg2.gif" class="index_title_right">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>&nbsp;&nbsp;<img src="web/img/index_nav1.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> &nbsp;工作台 &nbsp;| </span>
				<span>&nbsp;&nbsp;<img src="web/img/index_nav2.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> &nbsp;我的消息(0) &nbsp;|</span>
				<span>&nbsp;&nbsp;<img src="web/img/index_nav3.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> &nbsp;我的账户 &nbsp;| </span>
				<span>&nbsp;&nbsp;<img src="web/img/index_nav5.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> &nbsp;帮助&nbsp; |   </span>
				<span>&nbsp;&nbsp;<img src="web/img/index_nav6.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> &nbsp;关于&nbsp;|</span>
				<span>&nbsp;&nbsp;<img src="web/img/index_nav4.gif" width="15" height="14" align="absmiddle" style="padding-bottom: 2px;"> 
				<a href="#" style="text-decoration: none;color:#ffffff;" onclick="logout('${bathPath}/j_spring_security_logout');return false;">&nbsp;退出&nbsp; </a></span>
			</td>
		  </tr>
		</table>
	</td>
  </tr>
  
</table>
</td>
</tr>


  <tr>
    <td  id="leftT"  style="text-align: left;vertical-align: top;padding: 0px;">
    	<jsp:include page="/index_left.jsp" flush="true"/>
	</td>
	
    <td bgcolor="#89abd0" style="cursor:pointer;" width="8" align="left" id="center_control">
    	<img src="web/img/index_center_control.jpg" align="absmiddle" height="113" id="center_control_img" onclick="closeLeftTable(this);">
    </td>
    
    <td  align="left" valign="top" id="index_main_td">
	<table  border="0" cellspacing="0" cellpadding="0" id="index_main_table">
      <tr>
        <td height="26" width="100%">
	        <img src="web/img/index_nav1.gif" align="absmiddle" style="margin-left:5px;"> 
	        <span style="color:#002450; font-size:12px; font-weight:bold;font-family:'Times New Roman'" id="rightTitle">工作台</span>
        </td>
      </tr>
      
      <tr>
      	<td>
      	    <iframe name="pageResult" 
      	    			id="pageResult" 
      	    			frameborder="0" 
						width="100%" 
						height="100%"
						scrolling="no"
						onload="this.height=pageResult.document.body.scrollHeight" src="blank.jsp" style="background-color: ffffff">
			</iframe>
      	</td>
      </tr>
      
    </table>
	</td>
  </tr>
</table>


<script type="text/javascript">
	
		var isOpen = true;
		function closeLeftTable()
		{
			var self = arguments[0];
			if(isOpen){
				$("#leftT").css("display","none");
				$("#lefttable").css("width","100%");
				self.src = "web/img/index_center_control1.jpg";
				isOpen = false;
			}else
			{
				$("#leftT").css("display","");
				self.src = "web/img/index_center_control.jpg";
				isOpen = true;
			}
			
			
		}
	
		//调整菜单样式
		$(document).ready(function(){
				$("#leftT").css("width","200px");
				$("#lefttable").css("width","100%");
				
				$("#treeControl1").css({"marginLeft":"80px","cursor":"pointer"});
				$("#treeControl2").css({"marginLeft":"80px","cursor":"pointer"});
				$("#treeControl3").css({"marginLeft":"80px","cursor":"pointer"});
				$("#treeControl4").css({"marginLeft":"80px","cursor":"pointer"});
				$("#treeControl5").css({"marginLeft":"80px","cursor":"pointer"});
				$("#treeControl6").css({"marginLeft":"80px","cursor":"pointer"});
				
				var bodyWidth = document.body.clientWidth;
				var leftTWidth = document.getElementById("leftT").style.width;
				
				leftTWidth = leftTWidth.replace("px","");
				var index_main_width = bodyWidth-leftTWidth;
				
				$("#index_main_td").css("width",index_main_width);
				$("#index_main_table").css("width","100%");
				
				//var date=new Date();
				//document.getElementById('showtime').innerHTML=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		}); 
		
	</script>
	
	<script type="text/javascript">
		var lefts;
		
		function redirect(path,self)
		{
			//document.getElementById("rightTitle").innerHTML = self.getAttribute('resource');
			document.getElementById("pageResult").src = path;
			
			if(lefts != null)
			{
				for(var i = 0; i < lefts.length;i++)
				{
					lefts[i].style.backgroundColor="#F7FFFF";
				}
			}
			self.style.backgroundColor="#FFFF99";
		}
		
		var invalidateUser = false;
		function invalidateSession(loginTime)
		{
			
			var url = "logoutAction.action";
			//var   n   =   window.event.screenX   -   window.screenLeft;        
		    //var   b   =   n   >   document.documentElement.scrollWidth-20;        
		    //b   &&   window.event.clientY   <   0 
		    var inner_loginTime = loginTime;
		    alert(inner_loginTime);
	        if(event.clientX > document.body.clientWidth && event.clientY <0 || event.altKey)       
	        {        
	         	 alert("关闭浏览器");     
				 $.post(url+"?loginTime="+inner_loginTime, { },
					  function(data){
					  },'json'); 
	        }
	        else
	        {
	        	//点击界面退出按钮
	        	if(invalidateUser)
	        	{
	        		alert("点击退出"); 
	        		$.post(url, { "loginTime": inner_loginTime },
	      				  function(data){
	      				  },'json'); 
	        	}else
	        	{
	        		alert("F5刷新"); 
	        	}
	        }
		}
		
		//js屏蔽浏览器（IE和FireFox）的刷新功能 
		/*document.onkeydown=function()
		{
		  if ((window.event.keyCode==116)|| //屏蔽 F5
		      (window.event.keyCode==122)|| //屏蔽 F11
		      (window.event.shiftKey && window.event.keyCode==121) //shift+F10
		     )
		     { 
		          window.event.keyCode=0;
		          window.event.returnValue=false;
		     } 
		  if ((window.event.altKey)&&(window.event.keyCode==115))
		     { 
		         //屏蔽Alt+F4
		         window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px");
		         return false;
		     }  
		};*/
		
		jQuery().ready(function() {
			//界面左侧的jquery折叠树菜单
			jQuery('#navigation').accordion({
				active : false,
				header : '.head',
				navigation : true,
				event : 'click',
				fillSpace: false,
				animated: 'easeslide',
				autoHeight: false
			});
			
			lefts = document.getElementsByName("left");

		});
		
		//退出
		function logout(path)
		{
			invalidateUser = true;
			location.href = path;
		}
	 //调整IFrame高度
	  function loadFrameH(hg)
	  {	
	  	var fr = document.getElementById("pageResult");
	  	fr.height=pageResult.document.body.scrollHeight;
	  }
	</script>
</body>
</html>
