<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<TITLE>CAPICOM - HashedData Sample</TITLE>
<meta name="vs_targetSchema"
	content="http://schemas.microsoft.com/intellisense/ie5">
<OBJECT id="oCAPICOM"
	codeBase="http://download.microsoft.com/download/E/1/8/E18ED994-8005-4377-A7D7-0A8E13025B94/capicom.cab#version=2,0,0,3"
	classid="clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679" VIEWASTEXT>
</OBJECT>

<SCRIPT language="vbscript">
Function Unicode2Ansi(unicodestring) 
	Dim lngLoop
	Dim strChar
	Ustr2Bstr = ""

	For lngLoop = 1 to Len(unicodestring)
		strChar = Mid(unicodestring, lngLoop, 1)
		Unicode2ANSI = Unicode2ANSI & ChrB(AscB(strChar))
	Next
End function
</SCRIPT>

<SCRIPT id="clientEventHandlersJS" language="jscript">
		// CAPICOM Constants
		var CAPICOM_HASH_ALGORITHM_SHA1 = 0;
		var CAPICOM_HASH_ALGORITHM_MD2 = 1;
		var CAPICOM_HASH_ALGORITHM_MD4 = 2;
		var CAPICOM_HASH_ALGORITHM_MD5 = 3;
		
			    
		// check if CAPICOM has been installed.
		if (IsCAPICOMInstalled() != true)
		{
			// Alert the that CAPICOM was not able to be installed
			alert("CAPICOM could not be loaded, possibly due to insufficient access privileges on this machine.");	
		}
		
		
		function IsCAPICOMInstalled()
		{
			if(typeof(oCAPICOM) == "object")
			{
				if( (oCAPICOM.object != null) )
				{
					// We found CAPICOM!
					return true;
				}
			}
		}		
		
		function btnHashData_onclick()
		{
			// only attempt to has if data has been supploed
			if (txtSourceText.value != "")
			{
				// instantiate the CAPICOM objects
				var HashedData = new ActiveXObject("CAPICOM.HashedData");
		
				// convert to ANSII
				//var Utilities = new ActiveXObject("CAPICOM.Utilities");
				//var SourceData = Utilities.BinaryStringToByteArray(txtSourceText.value);
				
				SourceData = Unicode2Ansi(txtSourceText.value);
				//SourceData = txtSourceText.value;
				// clear the list
				while (lstHashes.options.length) lstHashes.options[0] = null;
				
				// place in a try block so we catch any errors
				try
				{
					if (chkSHA1.checked==true)
					{
						// set the algorithm
						HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_SHA1;
						
						// set the data to hash
						HashedData.Hash(SourceData);
		
						// update the list
						var Hash = new Option("SHA1: " + HashedData.Value, CAPICOM_HASH_ALGORITHM_SHA1);
						lstHashes.options.add(Hash, CAPICOM_HASH_ALGORITHM_SHA1);
					}
					
					if (chkMD2.checked==true)
					{
						// set the algorithm
						HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_MD2;
						
						// set the data to hash
						HashedData.Hash(SourceData);
		
						// update the list
						var Hash = new Option("MD2: " + HashedData.Value, CAPICOM_HASH_ALGORITHM_MD2);
						lstHashes.options.add(Hash, CAPICOM_HASH_ALGORITHM_MD2);
					}
		
					if (chkMD4.checked==true)
					{
						// set the algorithm
						HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_MD4;
						
						// set the data to hash
						HashedData.Hash(SourceData);
		
						// update the list
						var Hash = new Option("MD4: " + HashedData.Value, CAPICOM_HASH_ALGORITHM_MD4);
						lstHashes.options.add(Hash, CAPICOM_HASH_ALGORITHM_MD4);
					}
		
					if (chkMD5.checked==true)
					{
						// set the algorithm
						HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_MD5;
						
						// set the data to hash
						HashedData.Hash(SourceData);
		
						// update the list
						var Hash = new Option("MD5: " + HashedData.Value, CAPICOM_HASH_ALGORITHM_MD5);
						lstHashes.options.add(Hash, CAPICOM_HASH_ALGORITHM_MD5);
					}	
				}
				catch (e)
				{
					alert("An error occured while calculating a has over the supploed data, the error was: " + e.description + "\nThe hash algorith being used was: " + HashedData.Algorithm);
				}
			}	
		}

		</SCRIPT>
		
</head>
<BODY>
	<TABLE id="WizardTable" height="394" cellPadding="15" width="527"
		align="center" border="1">
		<TR>
			<TD vAlign="top">
				<P align="center">
					<STRONG>Welcome to the CAPICOM 2.0 HashedData Sample</STRONG>
				</P>
				<TABLE cellSpacing="1" cellPadding="1" width="485" border="0">
					<TR>
						<TD><INPUT id="chkSHA1" type="checkbox" CHECKED
							name="chkSHA1">&nbsp;SHA1</TD>
						<TD><INPUT id="chkMD2" type="checkbox" CHECKED name="chkMD2">&nbsp;MD2</TD>
						<TD><INPUT id="chkMD4" type="checkbox" CHECKED name="chkMD4">&nbsp;MD4</TD>
						<TD><INPUT id="chkMD5" type="checkbox" CHECKED name="chkMD5">&nbsp;MD5</TD>
					</TR>
				</TABLE>
				<P>CAPICOM 2.0 introduced a new object called HashedData, this
					object allows you to hash arbitrary data. In this sample we show
					some basic uses of this new object.</P>
				<P>
					This sample was written in JavaScript and its source can be found <A
						onclick='window.location = "view-source:" + window.location.href'
						href="#"> here</A>.
				</P>
				<P>
					<TEXTAREA id="txtSourceText" name="txtSourceText" rows="5"
						cols="60"></TEXTAREA>
					&nbsp;
				</P>
				<P>
					<SELECT id="lstHashes" style="WIDTH: 497px; HEIGHT: 102px" size="6"
						name="lstHashes"></SELECT>
				</P>
				<P align="right">
					<INPUT id="btnHashData" type="button" value="Hash"
						name="btnHashData" onclick="btnHashData_onclick()">
				</P></TD>
		</TR>
	</TABLE>
</BODY>
</html>
