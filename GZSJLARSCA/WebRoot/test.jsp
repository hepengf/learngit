<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.security.cert.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>证书信息</title>
  </head>
  
  <body>
    <%
    	for(Enumeration en = request.getAttributeNames();en.hasMoreElements();)
    	{
    		String name = (String)en.nextElement();
    		out.println(name);
    		out.println(" = "+ request.getAttribute(name));
    		out.println("<br/>");
    	}
    %>
    <p>数字证书信息</p>
    <%
    	X509Certificate[] certs = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
    	if(certs != null){
	    	for(X509Certificate cert : certs)
	    	{
	    		out.println("版本："+cert.getVersion()+"<br/>");
	    		
	    		out.println("序列号: "+cert.getSerialNumber()+"<br/>");
	    		out.println("颁布者: "+cert.getIssuerDN().getName()+"<br/>");
	    		out.println("使用者: "+cert.getSubjectDN().getName()+"<br/>");
	    		out.println("签名算法: "+cert.getSigAlgName()+"<br/>");
	    		out.println("证书类型: "+cert.getType()+"<br/>");
	    		out.println("有效期从: "+cert.getNotBefore()+"<br/>");
	    		out.println("至: "+cert.getNotAfter()+"<br/>");
	    		out.println("<br/><br/><br/>");
	    	}
    	}
    %>
  </body>
</html>
