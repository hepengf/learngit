<%@ page language="java"
	import="java.util.*,org.springframework.security.access.*,org.springframework.security.web.access.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>您没有权限</title>


</head>

<body>
	<div style="width: 100%;text-align: center;color: #004799; padding-top: 2%">
		<p>您没有权限访问指定资源</p>
		<%
			AccessDeniedException ex = (AccessDeniedException) request
					.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
		%>
		<p><%=ex.getMessage()%></p>
		<br /> <em>Error Details</em>
		<blockquote>
			<pre><%=ex.getStackTrace()%></pre>
		</blockquote>
	</div>
</body>
</html>
