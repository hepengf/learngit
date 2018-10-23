<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>系统首页</title>
<link href="<%=request.getContextPath() %>/web/css/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
#navigation a.head{
	margin-left: 150px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
	
<script type="text/javascript">
		var isHidden = false;
		$(function(){
		//用这种方法判断是否该项存在
			//if($("#aadfsdf").html() == null){
			//	alert("this element innerhtml is null");
			//}
			
			for(var i = 1; i <=6; i++){
				
				$("#treeControl" + i).attr("src","web/img/left_tree_open.gif");
				$("#left_tree_innerTable" + i + " td").bind("mouseover",function(){
					$(this).removeClass("mouseOutClass");
					$(this).addClass("mouseOverClass");
				});
				$("#left_tree_innerTable" + i + " td").bind("mouseout",function(){
					$(this).removeClass("mouseOverClass");
					$(this).addClass("mouseOutClass");
				});
				
				$("#treeControl" + i).bind("click",function(){
					var indexNum = $(this).attr("id").substring($(this).attr("id").length-1,$(this).attr("id").length);
					
					if($(this).attr("src").indexOf("open") != -1){
						closeAllTree();
						$(this).attr("src","web/img/left_tree_close.gif");
						$("#left_tree_innerTable"+indexNum).show();
					}else{
						$("#left_tree_innerTable"+indexNum).hide();
						$(this).attr("src","web/img/left_tree_open.gif");
					}
				});
			}
			closeAllTree();
			$("#left_tree_innerTable1").show();
			$("#treeControl1").attr("src","web/img/left_tree_close.gif");
			
			$("#center_control").bind("click",function(){
				if(!isHidden){
					$("#leftT").hide();
					isHidden = true;
					$("#center_control_img").attr("src","web/img/index_center_control1.jpg");
				}else{
					$("#leftT").show();
					isHidden = false;
					$("#center_control_img").attr("src","web/img/index_center_control.jpg");
				}
			});
		});
		
		
		function closeAllTree(){
			for(var i = 1; i <=6; i++){
				$("#left_tree_innerTable"+i).hide();
				$("#treeControl" + i).attr("src","web/img/left_tree_open.gif");
			}
		}
	</script>
	
	<script type="text/javascript">
	
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
				
				var date=new Date();
				document.getElementById('showtime').innerHTML=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		}); 
		
	</script>
	
	<script type="text/javascript">
		function redirect(path)
		{
			document.getElementById("pageResult").src = path;
		}
		
		function invalidateSession(loginTime)
		{
			//alert(loginTime);
			//提交post请求
			var url = "logoutAction.action";
			$.post(url, { "loginTime": loginTime },
			  function(data){
			    //alert("Data Loaded: " + data);
			  },'json'); 
		}
		
		//js屏蔽浏览器（IE和FireFox）的刷新功能 
		document.onkeydown=function()
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
		};
	</script>
</head>
<%
	String loginTime = request.getAttribute("loginTime")+"";
%>
<body  onunload="invalidateSession('<%=loginTime%>')">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td colspan="3" valign="top" height="100" align="left" width="100%">
<table width="100%" border="0" cellspacing="0" cellpadding="0"  id="index_content">
  <tr>
    <td id="title">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" ><span style="color: white;font-weight: bold;width: 20%;text-align: center;">CA认证中心</span></td>
        </tr>
    </table>
	</td>
  </tr>
  
  <tr>
    <td height="3" bgcolor="#044aa0"></td>
  </tr>
  
  <tr>
    <td height="27" >
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="27" background="web/img/index_title_nav_bg.gif" width="450">
			　　<span class="userNameStyle">
				<%
					List<Map<String, UserVo>> userList = (List<Map<String, UserVo>>)session.getAttribute("user");
				    if(null == userList||0==userList.size())
				    {
				    	response.sendRedirect(request.getContextPath()+"/login.jsp");
				    }else
				    {
				    	for(Map<String,UserVo> userMap : userList)
				    	{
				    		if(userMap.get(loginTime) != null)
				    		{
				    			UserVo uv = userMap.get(loginTime);
				    			out.print(uv.getName());		
				    			break;
				    		}
				    	}
				    	
				    }
				%>
				&nbsp;&nbsp;欢迎您!</span>
			　　<span class="telStyle">支持热线(010-00000000)</span>　<span class="helpStyle">使用帮助</span>　
				<span class="telStyle">联系我们</span></td>
			    <td width="37"><img src="web/img/index_title_nav_but.jpg" width="37" height="27"></td>
			    <td  background="web/img/index_title_nav_bg2.gif" class="index_title_right">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="web/img/index_nav1.gif" width="16" height="15" align="absmiddle"> 工作台 &nbsp;&nbsp;| &nbsp;&nbsp;
				<img src="web/img/index_nav2.gif" align="absmiddle"> 我的消息(0) &nbsp;&nbsp;| &nbsp;&nbsp;
				<img src="web/img/index_nav3.gif" align="absmiddle"> 我的账户 &nbsp;&nbsp;| &nbsp;&nbsp;
				<img src="web/img/index_nav4.gif" align="absmiddle"> <a href="<%=request.getContextPath() %>/j_spring_security_logout" style="text-decoration: none;">退出&nbsp;&nbsp; </a>| &nbsp;&nbsp;
				<img src="web/img/index_nav5.gif" align="absmiddle"> 帮助&nbsp;&nbsp; |   &nbsp;&nbsp;
				<img src="web/img/index_nav6.gif" align="absmiddle"> 关于
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="showtime"></span>
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
    
		<table  border="0" cellspacing="0" cellpadding="0"  id="lefttable" style="margin: 0px;" >
		  <tr>
			<td height="28" width="100%" align="center" valign="middle" background="web/img/left_title2.gif" >
				<span class="left_title_text">操作菜单</span>
			</td>
		  </tr>
		  
		  <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			    <img src="web/img/left_tree1.gif" width="17" height="17"> 证书管理
			    <img src="web/img/left_tree_close.gif" id="treeControl1" width="15" height="15"  >
		    </td>
          </tr>
          
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable1">
				  <tr>
					<td width="100%"><img src="web/img/left_tree1_1.gif" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/CertAppRequestAction.action');return false;" style="text-decoration: none;">
							证书申请
						</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree1_2.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findUnSignCertAction.action');return false;" style="text-decoration: none;">
							证书签发
					</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree1_3.gif" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findSignCertAction.action');return false;" style="text-decoration: none;">
							证书下载
						</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree1_4.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/disabledCertCheckAction.action');return false;" style="text-decoration: none;">
						证书作废
					</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree2_5.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="" style="text-decoration: none;">
						证书查询
					</a>
					</td>
				  </tr>
				   <tr>
					<td><img src="web/img/left_tree2_6.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="" style="text-decoration: none;">
						证书签名
					</a>
					</td>
				  </tr>
				</table>			
			</td>
	      </tr>
	      
		  <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			    <img src="web/img/left_tree2.gif" width="17" height="17" > CRL管理 
			    <img src="web/img/left_tree_close.gif"  id="treeControl2" width="15" height="15" >
		    </td>
          </tr>
          
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable2">
				  <tr>
					<td><img src="web/img/left_tree2_1.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/genCRLAction.action');return false;" style="text-decoration: none;">
						生成CRL
					</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree2_2.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="" style="text-decoration: none;">
						更新CRL
					</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree2_3.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="" style="text-decoration: none;">
						CRL文件下载
					</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree2_4.gif" align="absmiddle" style="margin-right:5px;">
					<a href="#" onclick="" style="text-decoration: none;">
						CRL文件作废
					</a>
					</td>
				  </tr>
				</table>			
			</td>
	      </tr>
	      
		   <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			    <img src="web/img/left_tree3.gif" width="17" height="17" > 权限管理
			    <img src="web/img/left_tree_close.gif" id="treeControl3" width="15" height="15" >
		    </td>
          </tr>
          
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable3">
				  <tr>
					<td>
						<img src="web/img/left_tree3_1.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findAllUserListAction.action');return false;" style="text-decoration: none;">
							用户管理
						</a>
					</td>
				  </tr>
				  <tr>
					<td>
						<img src="web/img/left_tree3_2.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findAllRoleListAction.action');return false;" style="text-decoration: none;">
							角色管理
						</a>
					</td>
				  </tr>
				  <tr>
					<td>
						<img src="web/img/left_tree3_3.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findAllResourceListAction.action');return false;" style="text-decoration: none;">
							资源管理
						</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree3_4.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">权限分配</td>
				  </tr>
				</table>			
			</td>
	      </tr>
	      
		   <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			     <img src="web/img/left_tree4.gif" width="17" height="17" > 个人办公
			     <img src="web/img/left_tree_close.gif" id="treeControl4" width="15" height="15" >
		     </td>
          </tr>
          
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable4">
				  <tr>
					<td><img src="web/img/left_tree4_1.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">文档中心</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree4_2.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">我的消息</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree4_3.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">日程管理</td>
				  </tr>
				</table>			
			</td>
	      </tr>
	      
		  <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			    <img src="web/img/left_tree5.gif" width="17" height="17" > 系统设置
			     <img src="web/img/left_tree_close.gif" id="treeControl5" width="15" height="15">
			</td>
          </tr>
          
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable5">
				  <tr>
					<td><img src="web/img/left_tree5_1.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/findAllConfigAction.action');return false;" style="text-decoration: none;">
							参数设置
						</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_2.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">
						<a href="#" onclick="redirect('<%=request.getContextPath() %>/web/statistics.jsp');return false;" style="text-decoration: none;">
							系统用户监控
						</a>
					</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_3.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">操作权限组</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_4.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">查询权限组</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_5.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">数据备份</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_6.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">数据还原</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_7.gif" align="absmiddle" style="margin-right:5px;">辅助信息</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_8.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">省份资料</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_9.gif" align="absmiddle" style="margin-right:5px;">城市资料</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_10.gif" width="17" height="17" align="absmiddle" style="margin-right:5px;">新闻公告</td>
				  </tr>
				 
				  <tr>
					<td><img src="web/img/left_tree5_12.gif" align="absmiddle" style="margin-right:5px;">数据字典</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_13.gif" align="absmiddle" style="margin-right:5px;">客户及联系人导入</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_14.gif" align="absmiddle" style="margin-right:5px;">商品资料及类别导入</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree5_15.gif" align="absmiddle" style="margin-right:5px;">联系记录导入</td>
				  </tr>
				</table>			
			</td>
	      </tr>
	      
		  <tr>
		    <td height="22" bgcolor="#e4edf8"  style="font-size:12px;">　
			    <img src="web/img/left_tree6.gif" width="17" height="17" > 报表分析 
			    <img src="web/img/left_tree_open.gif" id="treeControl6" width="15" height="15" >
		   </td>
		  </tr>
		  
		  <tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="left_tree_innerTable6">
				  <tr>
					<td><img src="web/img/left_tree4_1.gif" align="absmiddle" style="margin-right:5px;">文档中心</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree4_2.gif" align="absmiddle" style="margin-right:5px;">我的消息</td>
				  </tr>
				  <tr>
					<td><img src="web/img/left_tree4_3.gif" align="absmiddle" style="margin-right:5px;">日程管理</td>
				  </tr>
				</table>			
			</td>
          </tr>
          
   	</table>
	</td>
	
    <td bgcolor="#89abd0" style="cursor:pointer;" width="8" align="left" id="center_control">
    	<img src="web/img/index_center_control.jpg" align="absmiddle" height="113" id="center_control_img">
    </td>
    
    <td  align="left" valign="top" id="index_main_td">
	<table  border="0" cellspacing="0" cellpadding="0" id="index_main_table">
      <tr>
        <td height="26" width="100%">
        <img src="web/img/index_nav1.gif" align="absmiddle" style="margin-left:5px;"> 
        <span style="color:#002450; font-size:14px; font-weight:bold;">工作台</span></td>
      	
      </tr>
      <tr>
      	<td>
      	    <iframe name="pageResult" id="pageResult" frameborder="0" 
						width="100%" scrolling="auto"
						onload="this.height=pageResult.document.body.scrollHeight">
			</iframe>
      	</td>
      </tr>
      
    </table>
	</td>
  </tr>
</table>



</body>
</html>
