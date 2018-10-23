<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript">
function gotoPage(pageNo) {
	if(pageNo>0)
		$("[name='pager.currentPage']").prop("value",pageNo);
	else
		changeBiggest();
		pageNo = $("[name='pager.currentPage']").val();
	try{
		document.myForm.submit();
	}
	catch(e)
	{
			if(pageNo > 0){
				$("#currentPage",window.parent.document).val(pageNo);
			}
			var $myForm = $("#myForm",window.parent.document);
			$myForm.submit();
	}
}
function changeBiggest(){
	var biggest = ${pager.totalPage};
	var jumpInput = $("[name='pager.currentPage']").val();
    var jumpInputNum = parseInt(jumpInput,10);
	if(isNaN(jumpInputNum)||jumpInputNum>biggest){
		$("[name='pager.currentPage']").val(biggest);
	};
}
function resizePageSize() {  //改变每页选择记录条数
	document.myForm.submit();
}
</script>
<div class="paginations pull-right">
	<ul class="pagination pagination-sm pull-left">
		<li><a <c:if test="${pager.hasFirst==false}">disabled</c:if> <c:if test="${pager.hasFirst!=false}">href="javascript:void(0);" onclick="gotoPage(1)"</c:if>>首页</a> </li>
		<li><a <c:if test="${pager.hasPrevious==false}">disabled</c:if> <c:if test="${pager.hasPrevious!=false}">href="javascript:void(0);" onclick="gotoPage(${pager.currentPage-1})"</c:if>>上一页</a> </li>
		<li><a <c:if test="${pager.hasNext==false}">disabled</c:if> <c:if test='${pager.hasNext!=false}'>href="javascript:void(0);" onclick="gotoPage(${pager.currentPage+1})"</c:if>>下一页</a> </li>
		<li><a <c:if test="${pager.hasLast==false}">disabled</c:if> <c:if test="${pager.hasLast!=false}">href="javascript:void(0);" onclick="gotoPage(${pager.totalPage})"</c:if>>末页</a> </li>
	</ul>
	<div class="toPage pull-left"> 
		&nbsp;&nbsp;第&nbsp;${pager.currentPage}&nbsp;页&nbsp;(共&nbsp;${pager.totalSize}&nbsp;条，&nbsp;${pager.totalPage}&nbsp;页)&nbsp;&nbsp;
	                    ，显示<select onchange="resizePageSize()" id="pagesize" name="pager.pageSize" style="width:60px; line-height:24px;">
            <option <c:if test="${pager.pageSize==10}">selected="selected"</c:if> value="10">10
            </option>
            <option <c:if test="${pager.pageSize==20}">selected="selected"</c:if> value="20">20
            </option>
            <option <c:if test="${pager.pageSize==50}">selected="selected"</c:if> value="50">50
            </option>
            <option <c:if test="${pager.pageSize==100}">selected="selected"</c:if> value="100">100
            </option>
        </select> 项，跳转到第
	     <input type="text" id="child.currentPage" name="pager.currentPage" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" size="2" value="${pager.currentPage}"  onfocus="this.select();"/>
	                    页&nbsp;&nbsp;
	      <a class="btn btn-default btn-sm" href="javascript:gotoPage(-1);" >跳转</a>&nbsp;&nbsp;&nbsp;
	</div>
</div>

