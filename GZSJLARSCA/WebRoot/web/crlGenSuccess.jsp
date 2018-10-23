<%@ page language="java" import="java.util.*,java.security.cert.*,java.text.*,com.congoal.cert.utils.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>证书吊销列表明细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<div style="width:100%;text-align: center;"> 
    	<fieldset title="证书吊销列表">
    		<%
    		
    				X509CRL crl =  (X509CRL)request.getAttribute("crl");
		    		Set<X509CRLEntry> crlSet = (Set<X509CRLEntry>)crl.getRevokedCertificates();
		    		if (crlSet != null && crlSet.size() > 0)
		    		{
		    	%>
		    			<table style="width: 510px;" border="1px" >
		    			<tr>
		    				<td style="width:50px;text-align: center;">序号</td><td style="width:220px;text-align: center;">证书序列号</td><td style="width:120px;text-align: center;">撤销日期</td><td style="width:120px;text-align: center;">颁发者</td>
		    			</tr>
		    	<%		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    			int i = 0;
		    		     for(Object o : crlSet)
		    		     {
		    		    	 i++;
		    		         X509CRLEntry crlEntry = (X509CRLEntry)o;
		    		         String issuerName="&nbsp;";
		    		         if(null != crlEntry.getCertificateIssuer())
		    		         {
		    		        	 issuerName = crlEntry.getCertificateIssuer().getName();
		    		         }
		    		         
		    		         int serialNumber = crlEntry.getSerialNumber().intValue();
		    		         Date revocationDate = crlEntry.getRevocationDate();
		    		         //System.err.println("SerialNumber: "+serialNumber+",revocation: "+sdf.format(revocationDate));
		    	%>	     
		    			<tr><td><%= i%></td>
		    				<td><%= ValidateCRL.getSerialNumber(crlEntry)%></td>
		    				<td><%= sdf.format(revocationDate)%></td>
		    				<td><%= issuerName%></td></tr>
		    		
			    <%		     
			    		    }
			    %>	
			    		 <tr><td colspan="4" style="text-align: right;"><input type="button" value="返回" onclick="javascript:history.back();">&nbsp;</td></tr>
		    		     </table>
		      <%	}else
		    		{
		    			//System.err.println("crl is empty");
		    			out.println("系统提示：证书吊销列表不存在");
		    		}
    		%>
    	</fieldset>
    	</div>
  </body>
</html>
