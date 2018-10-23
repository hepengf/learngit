<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>shiyongbangz</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="web/css/bootstrap.min.css" />
<link rel="stylesheet" href="web/css/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="web/css/style.css" />
<link href="web/css/common.css" rel="stylesheet" type="text/css"/>
<link href="web/theme/dandelion2/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="web/js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.automaxlength.js"></script>
<style type="text/css">
	p{
		font-size: 15px;
		padding-left: 30px;
		font-family: inherit;
	}
	h4{
		color: #32CD32;
		padding-top: 5px;
	}
</style>
</head>

	<body>
			<div class="widget widget-edit">
			<div class="widget-content">
				<div style="width: 100%; min-height: 600px">
					<div style="padding-left: 45%; padding-top: 15px">
						<h3 style="color: #32CD32;">使用帮助</h3>
					</div>
					<h4>1、个人信息</h4>
					<p>
						系统用户可以对个人账户的登录密码进行修改。
					</p>
					<h4>2、查看根证书</h4>
					<p>
						系统提供查看根证书相关信息，并提供根证书文件下载功能。
					</p>
					<h4>3、证书查询</h4>
					<p>
						系统提供证书查询功能，可以通过查询条件查询出符合条件的证书信息，支持精确查询和模糊查询，系统可以对某种类型的证书进行单独查询。
					</p>
					<h4>4、证书冻结</h4>
					<p>						
						可以对短期内不会使用的证书或已进行挂失的设备相应的证书进行冻结操作，在冻结期间内证书被限制不可使用。被冻结的证书可以通过解冻操作恢复使用。
						证书冻结之后，相对应的充值设备也将暂停使用。
					</p>
					<h4>5、证书解冻</h4>
					<p>				
						证书解冻操作是相对于证书冻结操作的，此操作将冻结的证书解冻，使得证书可以重新使用。证书解冻之后，相对应的充值设备也将正常使用。
					</p>
					<h4>6、证书注销</h4>
					<p>
						用户可以对一些不再使用或是使用过程中出现问题的证书进行注销操作，注销后的证书不可恢复。证书注销之后，相对应的充值设备也将永久停止使用。
					</p>
					<h4>7、证书注销列表管理</h4>
					<p>
						系统提供查看已注销的证书相关信息，并提供已注销证书文件下载和查看注销明细等功能。
					</p>
					<h4>8、批量生成证书</h4>
					<p>
						厂家按格式提供申请证书的业务表单，将业务表单(Excel文件)导入成功系统后，系统自动批量生成终端或服务器证书，同时系统提供批量下载功能。
						下载的文件格式为ZIP格式，文件内容包括本次导入文件的所有设备或服务器的CA证书。
					</p>
					<h4>9、终端证书管理</h4>
					<p>
						提供证书查询功能及操作功能，查询条件有：证书状态、生成时间（起止）、系统根据查询条件显示证书列表信息，显示列表有： 序号、证书类型、证书文件名、签发日期、签发人、操作。
						终端证书管理操作功能包括下载、冻结、解冻、注销等4项功能。
					</p>
					<h4>10、服务器证书管理</h4>
					<p>
						提供证书查询功能及操作功能，查询条件有：证书状态、生成时间（起止）、系统根据查询条件显示证书列表信息，显示列表有： 序号、证书类型、证书文件名、签发日期、操作。
						服务器证书管理操作功能包括下载、冻结、解冻、注销等4项功能。
					</p>
					<h4>11、系统管理</h4>
					<p>
						系统管理主要用于对系统操作员的管理，包含用户管理、功能模块管理、角色管理等。实现功能模块的基本管理功能及不同级别管理员的权限配置。
					</p>
				</div>
			</div>
			</div>
	</body>
	
</html>
