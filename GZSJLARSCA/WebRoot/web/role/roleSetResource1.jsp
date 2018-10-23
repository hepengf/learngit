<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>分配资源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="<%=request.getContextPath() %>/web/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/web/js/dhtmlXTree/dhtmlxcommon.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/web/js/dhtmlXTree/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/web/js/dhtmlXTree/dhtmlxtree.css"/>


<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<script type="text/javascript">
		$(document).ready(function(){
			$("#setCheckedResourceForm").submit(function(){
				$("#resourceValues").val(tree.getAllCheckedBranches());
			});
		});
	</script>
</head>

<body>
	<form action="roleSetCheckedResource.action" method="post"
		id="setCheckedResourceForm">
		<fieldset title="分配资源" style="text-align: left;">
			<legend>
				<input type="submit" value="提  交"/>
				&nbsp;&nbsp;&nbsp;
				<input value="返  回" type="button"
					onclick="javascript:history.back();"/>
			</legend>
			<table height="410px;">
				<tr>
					<td>
						<div id="treebox_tree"
							style="width:350px; height:100%; background-color:#f5f5f5;border:1px solid Silver;">
					     </div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="resourceValues" id="resourceValues" />
			<input type="hidden" name="roleId" value="${roleId}" />

			<script type="text/javascript">
				//生成树的代码,需要放在onload里面
				tree = new dhtmlXTreeObject("treebox_tree","100%","100%",0);
				tree.setSkin('yellowbooks');
				tree.setImagePath('<%=request.getContextPath() %>/web/js/dhtmlXTree/imgs/csh_yellowbooks/');
				tree.enableCheckBoxes(true);//使用复选框
				tree.enableThreeStateCheckboxes(true);//父节点选中后，子节点自动被选中 选中，未选中，子节点部分选中
				
				
				tree.enableDragAndDrop(false);
				//tree.enableSmartXMLParsing(true);
				//tree.enableKeyboardNavigation(true);
				
				tree.loadXML("getResourceXMLAction.action?roleId=<%=request.getAttribute("roleId") %>");
			</script>

		</fieldset>
	</form>
</body>
</html>
