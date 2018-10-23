<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>

<title>分配资源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath}/web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/web/css/style.css" />
<link href="${basePath}/web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/web/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.automaxlength.js"></script>
<script type="text/javascript" src="${basePath}/web/js/dhtmlXTree/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${basePath}/web/js/dhtmlXTree/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/web/js/dhtmlXTree/dhtmlxtree.css"/>


<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>

<script type="text/javascript">
if ('${message}' != '') {
	alert('${message}');
}
</script>

<script type="text/javascript">
		$(document).ready(function(){
			$("#setCheckedResourceForm").submit(function(){
				$("#resourceValues").val(tree.getAllCheckedBranches());
			});
		});
		
		function formSubmit() {
		var rolename = document.getElementById("rolename").value;
		var description = document.getElementById("description").value;

		if (null == rolename || "" == rolename.trim()) {
			alert("系统提示：角色名不能为空!");
			return false;
		}
		if (rolename.length>20) {
			alert("系统提示：角色名太长!");
			return false;
		}
		if (null == description || "" == description.trim()) {
			alert("系统提示：描述不能为空!");
			return false;
		}
		if (description.length>50) {
			alert("系统提示：描述太长!");
			return false;
		}
		return true;

	}
</script>
</head>

<body>
	<div style="height: 650px">
	<form action="addsysUserAction.action" method="post" onsubmit="return formSubmit()" id="setCheckedResourceForm">
		<div class="widget widget-edit">
			<div class="widget-content">
				<table class="pn-ftable table-bordered" border="0" cellpadding="10" width="374px;">
				<tr>
						<th style="width:50px;text-align: center;">
						<span style="COLOR:RED">*</span>角色名
						</th>
						<td class="pf-content">
						<input type="text" maxlength="20" name="rolename" id="rolename" value="${rolename }" style="width:95%" /></td>
					</tr>
					<tr>
						<th style="text-align: center;"> 
							<span style="COLOR:RED">*</span>描述
						</th>
						<td class="pf-content"><input type="text" maxlength="50" value="${description }" name="description" id="description"  style="width:95%"></td>
					</tr>
			</table>
			<table height="410px;">
				<tr>
					<td>
						<div id="treebox_tree"
							style="width:350px; height:100%; background-color:#f5f5f5;border:1px solid Silver;">
					     </div>
					</td>
				</tr>
			</table>
			<div class="widget-bottom">
				<input type="hidden" name="id" value="${user.id}">
				<input type="submit" class="btn btn-primary pull-right" value="保存"/>
				<input type="button" class="btn btn-primary pull-right" 
					onclick="javascript:window.history.back()" value="返回"/>
			</div>
		</div>
	</div>
	<input type="hidden" name="resourceValues" id="resourceValues" />
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
				
		tree.loadXML("getResourceXMLAction.action");
	</script>				
</form>
</div>
</body>
</html>
