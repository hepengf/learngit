<%@ page language="java" import="java.util.*,com.congoal.cert.pojo.*,com.congoal.cert.utils.*;" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>参数管理</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
	<script type="text/javascript">
		var ocvalue,cdesc;
		
		function updateConfig(cid,self)
		{
			var id = cid;
			var obj = self;
			
			var tr = $("#tr"+id);
			
			var cvalue = tr.find("input[name='cvalue']");
			
			cvalue.attr("readOnly",false);//设置只读属性为false
			cvalue.css("borderWidth","1px"); 
			cvalue.css("borderStyle","solid"); 
			ocvalue = cvalue.val();
			
			var cdesc = tr.find("input[name='cdesc']");
			
			cdesc.attr("readOnly",false);//设置只读属性为false
			cdesc.css("borderWidth","1px"); 
			cdesc.css("borderStyle","solid"); 
			ocdesc = cdesc.val();
			
			var submit = $(obj).siblings("a");
			
			submit.css("display","");
			
			obj.style.display = "none";
			
			parent.loadFrameH();
			
		}
		
		function updateCommit(cid,self)
		{
			var id = cid;
			var obj = self;
			
			var tr = $("#tr"+id);
			
			var cvalue = tr.find("input[name='cvalue']");
			
			if(cvalue.val() == null || cvalue.val() == '')
			{
				alert("系统提示：参数值不能为空");
				
				
				return;
			}
			
			
			cvalue.attr("readOnly",true);//设置只读属性为false
			cvalue.css("borderWidth","0px"); 
			//cvalue.css("borderStyle","solid"); 
			
			var cdesc = tr.find("input[name='cdesc']");
			
			cdesc.attr("readOnly",true);//设置只读属性为false
			cdesc.css("borderWidth","0px"); 
			//cdesc.css("borderStyle","solid"); 
			
			var check = $(obj).siblings("a");
			
			check.css("display","");
			
			obj.style.display = "none";
			
			
			//判断内容是否有变更
			if(cvalue.val() == ocvalue && cdesc.val() == ocdesc)
			{
				//没有变更则不提交
			}else
			{
				//提交完成后 把ocvalue、ocdesc清空
				$.post("updateConfigAction.action", { cid:id,cvalue: cvalue.val(), cdesc: cdesc.val() },
						  function(data){
						   
						    if(data.result == '0000000')
						    {
						    	alert("系统提示：参数更新成功");
						    }
						    if(data.result == '1111111')
						    {
						    	alert("系统提示：参数更新失败");
						    }
						    ocvalue=null;
						    ocdesc = null;
						    
						  },'json'); 
			}
			
			//提交到后台
			parent.loadFrameH();
		}
	</script>
  </head>
  
  <body >
    <%
    	List<UnConfig> configs = (List<UnConfig>)request.getAttribute("configsList");
    %>	
   		 <table style="width: 100%" >
   		 	<tr>
	   		 	<th style="font-size: 10pt;color: #004799;">全选</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">参数类型</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">参数名</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">参数值</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">状态</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">参数说明</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">创建日期</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: left;">创建人</th>
	   		 	<th style="font-size: 10pt;color: #004799;text-align: center;">操作</th>
   		 	</tr>
    <%	
    	if(null == configs || configs.size() == 0)
    	{
    		out.write("当前没有可用的系统参数");
    	}
    	else
    	{
    %>		
   		 
   		 	<%
   		 		for(int i =0 ; i < configs.size();i++)
   		 		{
   		 			UnConfig c = configs.get(i);
   		 	%>	
   		 			<tr id="tr<%=c.getId()%>">
			   		 	<th style="font-size:10pt;"><input type="checkbox" name="checkbox">&nbsp;&nbsp;<%=i+1 %></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=c.getType() %>" name="ctype" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=c.getName() %>" name="cname" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=SimpleStringUtil.formatPageCol(c.getValue()) %>" name="cvalue" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=StatusUtils.getEnableName(c.getStatus()+"") %>" name="cstatus" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=SimpleStringUtil.formatPageCol(c.getDescription()) %>" name="cdesc" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=SimpleDateFormatUtils.formatDateReturnYYYMMDD(c.getCreatedate()) %>" name="createdate" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
			   		 		<input type="text" value="<%=SimpleStringUtil.formatPageCol(c.getOperator()) %>" name="operator" style="width: 100%;border: 0px;" readonly="readonly"></th>
			   		 	<th style="font-size:10pt;">
				   		 	<a href  = "#" style="text-decoration: none;font-size: 10pt;" onclick="updateConfig(<%=c.getId() %>,this)" >修改</a>
				   		 	
				   		 	<a href  = "#" style="text-decoration: none;font-size: 10pt;display: none;" onclick="updateCommit(<%=c.getId() %>,this)" >提交</a>
			   		 	</th>
		   		 	</tr>
   		 	<%
	   		 	}
	    	}
   		 	%>
   		 	<tr>
   		 		<td  colspan="9" style="text-align: right;">
   		 			<input value="新增参数" type="button">
   		 		</td>
   		 	</tr>
   		 </table>
  </body>
</html>
