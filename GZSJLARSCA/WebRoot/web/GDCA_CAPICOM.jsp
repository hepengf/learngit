<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>GDAC_CAPICOM演示页面</title>
<OBJECT id="oCAPICOM"
	codeBase="http://download.microsoft.com/download/E/1/8/E18ED994-8005-4377-A7D7-0A8E13025B94/capicom.cab#version=2,0,0,3"
	classid="clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679" VIEWASTEXT>
</OBJECT>
<script type="text/javascript" src="extraInfo.js"></script>
<script type="text/javascript" src="lang/en_us.js"></script>
<script type="text/javascript" src="lang/zh_cn.js"></script>
<script type="text/javascript" src="lang/zh_tw.js"></script>
<script>
	var CAPICOM_LOCAL_MACHINE_STORE = 1;
	var CAPICOM_CURRENT_USER_STORE = 2;
	var CAPICOM_ACTIVE_DIRECTORY_USER_STORE = 3;
	var CAPICOM_SMART_CARD_USER_STORE = 4;

	var CAPICOM_HASH_ALGORITHM_SHA1 = 0;
	var CAPICOM_HASH_ALGORITHM_MD2 = 1;
	var CAPICOM_HASH_ALGORITHM_MD4 = 2;
	var CAPICOM_HASH_ALGORITHM_MD5 = 3;

	var CAPICOM_ENCRYPTION_ALGORITHM_RC2 = 0;
	var CAPICOM_ENCRYPTION_ALGORITHM_RC4 = 1;
	var CAPICOM_ENCRYPTION_ALGORITHM_DES = 2;
	var CAPICOM_ENCRYPTION_ALGORITHM_3DES = 3;
	var CAPICOM_ENCRYPTION_ALGORITHM_AES = 4;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_MAXIMUM = 0;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_40_BITS = 1;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_56_BITS = 2;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_128_BITS = 3;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_192_BITS = 4;
	var CAPICOM_ENCRYPTION_KEY_LENGTH_256_BITS = 5;
	var CAPICOM_SECRET_PASSWORD = 0;
	var CAPICOM_ENCODE_BASE64 = 0;
	var CAPICOM_ENCODE_BINARY = 1;
	var CAPICOM_ENCODE_ANY = -1;

	var CAPICOM_INFO_SUBJECT_SIMPLE_NAME = 0;
	var CAPICOM_INFO_ISSUER_SIMPLE_NAME = 1;
	var CAPICOM_INFO_SUBJECT_EMAIL_NAME = 2;
	var CAPICOM_INFO_ISSUER_EMAIL_NAME = 3;

	var CAPICOM_CHECK_NONE = 0;
	var CAPICOM_CHECK_TRUSTED_ROOT = 1;
	var CAPICOM_CHECK_TIME_VALIDITY = 2;
	var CAPICOM_CHECK_SIGNATURE_VALIDITY = 4;
	var CAPICOM_CHECK_ONLINE_REVOCATION_STATUS = 8;
	var CAPICOM_CHECK_OFFLINE_REVOCATION_STATUS = 16;

	var CAPICOM_TRUST_IS_NOT_TIME_VALID = 1;
	var CAPICOM_TRUST_IS_NOT_TIME_NESTED = 2;
	var CAPICOM_TRUST_IS_REVOKED = 4;
	var CAPICOM_TRUST_IS_NOT_SIGNATURE_VALID = 8;
	var CAPICOM_TRUST_IS_NOT_VALID_FOR_USAGE = 16;
	var CAPICOM_TRUST_IS_UNTRUSTED_ROOT = 32;
	var CAPICOM_TRUST_REVOCATION_STATUS_UNKNOWN = 64;
	var CAPICOM_TRUST_IS_CYCLIC = 128;
	var CAPICOM_TRUST_IS_PARTIAL_CHAIN = 65536;
	var CAPICOM_TRUST_CTL_IS_NOT_TIME_VALID = 131072;
	var CAPICOM_TRUST_CTL_IS_NOT_SIGNATURE_VALID = 262144;
	var CAPICOM_TRUST_CTL_IS_NOT_VALID_FOR_USAGE = 524288;

	var CAPICOM_STORE_OPEN_READ_ONLY = 0;
	var CAPICOM_STORE_OPEN_READ_WRITE = 1;
	var CAPICOM_STORE_OPEN_MAXIMUM_ALLOWED = 2;
	var CAPICOM_STORE_OPEN_EXISTING_ONLY = 128;
	var CAPICOM_STORE_OPEN_INCLUDE_ARCHIVED = 256;

	var CAPICOM_KEY_SPEC_KEYEXCHANGE = 1;
	var CAPICOM_KEY_SPEC_SIGNATURE = 2;

	var CAPICOM_AUTHENTICATED_ATTRIBUTE_SIGNING_TIME = 0;
	var CAPICOM_E_CANCELLED = -2138568446;
	var CAPICOM_VERIFY_SIGNATURE_AND_CERTIFICATE = 1;

	var MyStore = new ActiveXObject("CAPICOM.Store");
	//	var AddrStore = new ActiveXObject("CAPICOM.Store");
	//	var CAStore = new ActiveXObject("CAPICOM.Store");
	//	var RootStore = new ActiveXObject("CAPICOM.Store");

	var Certificate = new ActiveXObject("CAPICOM.Certificate");

	if (IsCAPICOMInstalled() != true) {
		// Alert the that CAPICOM was not able to be installed
		alert("CAPICOM could not be loaded, possibly due to insufficient access privileges on this machine.");
	}

	function IsCAPICOMInstalled() {
		if (typeof (oCAPICOM) == "object") {
			if ((oCAPICOM.object != null)) {
				// We found CAPICOM!
				return true;
			}
		}
	}
	function ListCert() {
		var count = frmStore.allCerts.options.length;//下拉框的值
		//alert(count);
		for (var i = 1; i <= count; i++) {
			frmStore.allCerts.options.remove(count - i);
		}
		var storeName = frmStore.storeName
				.options(frmStore.storeName.selectedIndex).value;

		// clear the list
		while (frmStore.allCerts.options.length)
			frmStore.allCerts.options[0] = null;

		try {
			MyStore.Open(CAPICOM_CURRENT_USER_STORE, storeName,
					CAPICOM_STORE_OPEN_READ_WRITE);//CAPICOM_LOCAL_MACHINE_STORE
			//AddrStore.Open(CAPICOM_CURRENT_USER_STORE, "AddressBook", CAPICOM_STORE_OPEN_READ_WRITE);
			//CAStore.Open(CAPICOM_CURRENT_USER_STORE, "CA", CAPICOM_STORE_OPEN_READ_WRITE);
			//RootStore.Open(CAPICOM_CURRENT_USER_STORE, "Root", CAPICOM_STORE_OPEN_READ_WRITE);

			//读取usb-key的证书
			//12 -> CAPICOM_CERTIFICATE_FIND_KEY_USAGE
			//0x00000080 -> CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE
			//9 -> CAPICOM_CERTIFICATE_FIND_TIME_VALID
			//6,6 -> CAPICOM_CERTIFICATE_FIND_EXTENDED_PROPERTY,CERT_KEY_SPEC_PROP_ID

			//var stores = MyStore.Certificates.Find(12,0x00000080).Find(9).Find(6,6);
			//alert(stores.Count);

		} catch (e) {
			alert("we were unable to open your certificate stores, aborting");
			return false;
		}
		for (var i = 1; i <= MyStore.Certificates.Count; i++) {
			//   var   oOption   =   document.createElement("OPTION");   
			//	oOption.text = MyStore.Certificates(i).subjectName + "Sha1 =" + MyStore.Certificates(i).thumbprint;   
			// 	oOption.value = i - 1;   
			// 	frmStore.allCerts.options.add(oOption);   
			var certInfo = new Option("cert: "
					+ MyStore.Certificates(i).subjectName + " Sha1 ="
					+ MyStore.Certificates(i).thumbprint);
			frmStore.allCerts.options.add(certInfo, i);
		}
	}
	function SelectedCert() {
		var index = frmStore.allCerts.options.selectedIndex;

		//get currently selected certificate
		Certificate = MyStore.Certificates(index + 1);
		Certificate.SubjectName;
		Certificate.IssuerName;
	}

	function DisplayCert() {
		//Certificate.Display()；
		//var $select = document.getElementById("allCerts");
		//alert($select.options[$select.options.selectedIndex].value);
		//var index = frmStore.allCerts.options.selectedIndex;
		//var SelCertificate = MyStore.Certificates(index+1);

		var cert = new extraInfo(Certificate);

		//调用extraInfo对象的espsplit方法，截取证书信息，给相应的属性赋值
		cert.espsplit();
		//调用show方法 返回一个证书信息的HTML
		var certInfo = cert.show();
		//显示返回的HTML
		document.getElementById("showCert").innerHTML = certInfo;
	}
	function GetPrivateInfo() {

		var szHTML = "<p><table id=tblPrivKey cellSpacing=10 cellPadding=0 width="+"800"+" border=0 ms_1d_layout="+"TRUE"+">";//"<p><table id=tblPrivKey cellSpacing=10 cellPadding=0 width='800' border=0 ms_1d_layout='TRUE'>";
		if (!Certificate.HasPrivateKey()) {
			szHTML += "<font color=#993300 size=2 face='Arial'><h5> Certificate has no PrivateKey </h5></font></table></p>";
			datadiv.innerHTML = szHTML;
			return;
		}
		szHTML = szHTML
				+ "<tr><td><font color=#993300 size=2 face='Arial'>Private Key Properties:</font></td><td><font color=#993300 size=2 face='Arial'>";

		if (Certificate.PrivateKey.KeySpec == CAPICOM_KEY_SPEC_KEYEXCHANGE)
			szHTML = szHTML + " Exchange";
		else
			szHTML = szHTML + " Signature";

		if (Certificate.PrivateKey.IsAccessible())
			szHTML = szHTML + ", Accessible";
		else
			szHTML = szHTML + ", Not Accessible";

		if (Certificate.PrivateKey.IsExportable())
			szHTML = szHTML + ", Exportable";
		else
			szHTML = szHTML + ", Not Exportable";

		if (Certificate.PrivateKey.IsHardwareDevice())
			szHTML = szHTML + ", Stored on H/W Device";

		if (Certificate.PrivateKey.IsMachineKeyset())
			szHTML = szHTML + ", Machine";

		if (Certificate.PrivateKey.IsProtected())
			szHTML = szHTML + ", Protected";

		if (Certificate.PrivateKey.IsRemovable())
			szHTML = szHTML + ", Removable";

		szHTML = szHTML + "</font></td></tr>";
		szHTML = szHTML
				+ "<tr><td><font color=#993300 size=2 face='Arial'>Container Name:</font></td><td><font color=#993300 size=2 face='Arial'>"
				+ Certificate.PrivateKey.ContainerName + "</font></td>";

		if (Certificate.PrivateKey.ContainerName != Certificate.PrivateKey.UniqueContainerName)
			szHTML = szHTML
					+ "<tr valign=top><td><font color=#993300 size=2 face='Arial'>Unique Container Name:</font></td><td><font color=#993300 size=2 face='Arial'>"
					+ Certificate.PrivateKey.UniqueContainerName
					+ "</font></td></tr>";

		szHTML = szHTML + "</table>";

		datadiv.innerHTML = szHTML + "</p>";
	}
	function GetPublicInfo() {
		var PublicKey;

		//begin table
		var szHTML = "<p><table id=tblPubKey cellSpacing=15 cellPadding=0 width="+"1010"+" border=0 ms_1d_layout="+"TRUE"+">";
		szHTML = szHTML
				+ "<tr><td><font color=#993300 size=2 face='Arial'>Algorithm:</td>";
		szHTML = szHTML + "<td><font color=#993300 size=2 face='Arial'>"
				+ Certificate.PublicKey().Algorithm.FriendlyName + "</td></tr>";
		szHTML = szHTML
				+ "<tr><td><font color=#993300 size=2 face='Arial'>Key Length:</td>";
		szHTML = szHTML + "<td><font color=#993300 size=2 face='Arial'>"
				+ Certificate.PublicKey().Length + "</td></tr>";
		szHTML = szHTML
				+ "<tr><td><font color=#993300 size=2 face='Arial'>Parameters:</td>";
		szHTML = szHTML + "<td><font color=#993300 size=2 face='Arial'>"
				+ Certificate.PublicKey().EncodedParameters.Format()
				+ "</td></tr>";
		szHTML = szHTML
				+ "<tr><td valign=top><font color=#993300 size=2 face='Arial'>Key:</td>";
		szHTML = szHTML + "<td><font color=#993300 size=2 face='Arial'>"
				+ Certificate.PublicKey().EncodedKey.Format() + "</td></tr>";
		szHTML = szHTML + "</table>";

		datadiv.innerHTML = szHTML;
	}
	function GetExtInfo() {
		var szTmp;
		var szResult;

		var szHTML = "<font color=#993300 size=2 face='Arial'><p>";

		if (Certificate.Extensions().Count == 0)
			szHTML = szHTML + "<h5> Certificate has no extensions </h5>";
		else {
			for (var i = 1; i <= Certificate.Extensions().Count; i++) {
				szHTML = szHTML
						+ Certificate.Extensions().Item(i).OID.FriendlyName;

				if (Certificate.Extensions().Item(i).IsCritical)
					szHTML = szHTML + " (critical): <br>";
				else
					szHTML = szHTML + " (non-critical): <br>";

				szTmp = Certificate.Extensions().Item(i).EncodedData
						.Format(true);

				//use pattern
				szTmp = szTmp.replace(/[\r\n]/g, "<br>");

				//it's copied from msdn, but it's wrong .
				//use the fact that bMultiLines value was set to true in order to format output
				/*for (j = 1; j < szTmp.Length; j++)
				{
					if (szTmp.substr(j, 2) == /(^*\n*)|(^*\r*)/g)//(/[\r\n]/g
						szResult = szResult + "<br>";
					else
						szResult = szResult + szTmp.substr(j, 1);
				}	
				szHTML = szHTML + szResult;
				 */

				szHTML = szHTML + szTmp;
				szHTML = szHTML + "<p>";
			} //end for i			
		}
		datadiv.innerHTML = szHTML;
	}
	function btnEncryptData_onclick() {
		// only attempt to has if data has been supploed
		if (frmStore.srcText.value != "") {
			// instantiate the CAPICOM objects
			var EnvelopedData = new ActiveXObject("CAPICOM.EnvelopedData");

			// clear the cipher text
			frmStore.desEnvText.value = "";

			var algoType = frmStore.EncryptAlgo.options(frmStore.EncryptAlgo.selectedIndex).value;//加密算法

			EnvelopedData.Algorithm.Name = algoType;

			// Provide the Encrypted Data object with the text to encrypt
			EnvelopedData.Content = frmStore.srcText.value;//加密内容

			//add certificate to the Enverloper
			EnvelopedData.Recipients.Add(Certificate);//选择证书
			// Encrypt the data and return it in Base64 to the txtCipherText object
			try {
				frmStore.desEnvText.value = EnvelopedData
						.Encrypt(CAPICOM_ENCODE_BASE64);//显示密文
			} catch (e) {
				alert(e.description);
			}

			// clean up
			EnvelopedData = null;

		}
	}
	function btnDecryptData_onclick() {
		if (frmStore.desEnvText.value != "") {
			// instantiate the CAPICOM objects
			var DevelopedData = new ActiveXObject("CAPICOM.EnvelopedData");

			// clear the cipher text
			frmStore.unEnvText.value = "";

			//var algoType = frmStore.EncryptAlgo.options(frmStore.EncryptAlgo.selectedIndex).value;

			//DevelopedData.Algorithm.Name = algoType;

			// Provide the Encrypted Data object with the text to encrypt
			//DevelopedData.Content = frmStore.desEnvText.value;

			//add certificate to the Enverloper
			DevelopedData.Recipients.Add(Certificate);//选择证书
			// Encrypt the data and return it in Base64 to the txtCipherText object
			try {
				frmStore.unEnvText.value = DevelopedData
						.Decrypt(frmStore.desEnvText.value);//解密
			} catch (e) {
				alert(e.description);
			}

			frmStore.unEnvText.value = DevelopedData.Content;
			// clean up
			DevelopedData = null;

		}
	}
	//签名
	function SignText() {
		var SignedData = new ActiveXObject("CAPICOM.SignedData");
		var Signer = new ActiveXObject("CAPICOM.Signer.2");
		var TimeAttribute = new ActiveXObject("CAPICOM.Attribute");
		if (frmStore.srcSignText.value != "") {
			// Set the data that we want to sign
			SignedData.Content = frmStore.srcSignText.value;//签名数据
			try {
				// Set the Certificate we would like to sign with
				Signer.Certificate = Certificate;//选择用于签名的证书

				// Set the time in which we are applying the signature
				var Today = new Date();
				TimeAttribute.Name = CAPICOM_AUTHENTICATED_ATTRIBUTE_SIGNING_TIME;//0
				TimeAttribute.Value = Today.getVarDate();
				Today = null;
				Signer.AuthenticatedAttributes.Add(TimeAttribute);

				// Do the Sign operation
				var szSignature = SignedData.Sign(Signer, false,CAPICOM_ENCODE_BASE64);
			} catch (e) {
				if (e.number != CAPICOM_E_CANCELLED) {
					alert("An error occurred when attempting to sign the content, the errot was: " + e.description);
					return false;
				}
			}//end catch
			frmStore.signText.value = szSignature;//签名后的值
		}//end if
		SignedData = null;
		Signer = null;
		TimeAttribute = null;
	}
	
	
	//验签
	function VerifySign() {
		var verifyData = new ActiveXObject("CAPICOM.SignedData");
		if (frmStore.signText.value != "")//签名后的值
		{
			try {
				verifyData.Content = frmStore.srcSignText.value;//签名后的值

				// Do the Verify operation
				var szSignature = verifyData.Verify(frmStore.signText.value,false, CAPICOM_VERIFY_SIGNATURE_AND_CERTIFICATE);
			} catch (e) {
				if (e.number != CAPICOM_E_CANCELLED) {
					alert("An error occurred when attempting to sign the content, the errot was: "+ e.description);
					return false;
				}
			}//end catch
			alert("Signature is verified OK.");
		}//end if
		verifyData = null;

	}
</SCRIPT>
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
<script type="text/javascript">
	var CAPICOM_HASH_ALGORITHM_SHA1 = 0;
	var CAPICOM_HASH_ALGORITHM_MD2 = 1;
	var CAPICOM_HASH_ALGORITHM_MD4 = 2;
	var CAPICOM_HASH_ALGORITHM_MD5 = 3;

	function btnHashData()
	{
		var txt = arguments[0];
		// only attempt to has if data has been supploed
		if (txt != null && txt != "")
		{
			// instantiate the CAPICOM objects
			var HashedData = new ActiveXObject("CAPICOM.HashedData");
	
			// convert to ANSII
			//var Utilities = new ActiveXObject("CAPICOM.Utilities");
			//var SourceData = Utilities.BinaryStringToByteArray(txtSourceText.value);
			
			SourceData = Unicode2Ansi(txt);
			//SourceData = txtSourceText.value;
			// clear the list
			
			try
			{
				HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_SHA1;
				HashedData.Hash(SourceData);
				return HashedData.Value;	
			}
			catch (e)
			{
				alert("An error occured while calculating a has over the supploed data, the error was: " + e.description + "\nThe hash algorith being used was: " + HashedData.Algorithm);
			}
		}	
	}

	function login(style)
	{
		if(null == style) return;
		var loginStyle = style;
		var username = document.getElementById("username").value;//用户名
		var password = document.getElementById("password").value;//登录密码
		
		var txt = username+password;
		//在原有基础之上创建一个HashedData对象，用于在前台对明文计算SHA1摘要：
		var encTxt = btnHashData(txt);//生成摘要
		
		//根据用户名和密码做数字信封
		//var EnvelopedData = new ActiveXObject("CAPICOM.EnvelopedData");
		//var algoType = frmStore.EncryptAlgo.options(frmStore.EncryptAlgo.selectedIndex).value;//加密算法
		//var content = username+password;//加密前
		//EnvelopedData.Content = content;//加密内容
		//EnvelopedData.Recipients.Add(Certificate);//选择证书
		//var encryptContent =  EnvelopedData.Encrypt(CAPICOM_ENCODE_BASE64);//显示密文
		//alert("原文: "+content+" ,密文: "+encryptContent);
		
		//签名
		var SignedData = new ActiveXObject("CAPICOM.SignedData");
		var Signer = new ActiveXObject("CAPICOM.Signer.2");
		var TimeAttribute = new ActiveXObject("CAPICOM.Attribute");

		SignedData.Content = encTxt;//把摘要设置为签名内容
		document.getElementById("digstval").value=encTxt;
		
		//选择用于签名的证书
		Signer.Certificate = Certificate;
		
		// Set the time in which we are applying the signature
		var Today = new Date();
		TimeAttribute.Name = CAPICOM_AUTHENTICATED_ATTRIBUTE_SIGNING_TIME;//0
		TimeAttribute.Value = Today.getVarDate();
		Today = null;
		Signer.AuthenticatedAttributes.Add(TimeAttribute);
		
		// Do the Sign operation
		var szSignature = SignedData.Sign(Signer, false,CAPICOM_ENCODE_BASE64);
		
		//将签名值赋给隐藏域并传入后台
		document.getElementById("signval").value=szSignature;
		//alert("签名后："+szSignature);
		
		if(loginStyle == "bc")
		{
			document.verifySignForm.action="bcVerifySign.action";
		}else
		{
			document.verifySignForm.action="iaikVerifySign.action";
		}
		document.verifySignForm.submit();
	}
</script>
</head>

<body onLoad="ListCert()">
	<form name="frmStore" method="post" action="">
		<p>
			<br> 1.读取用户证书 <br> <br> 证书类型： <select id="storeName"
				size="1" name="storeName">
				<option value="my" selected>Personal</option>
				<option value="root">Root</option>
				<option value="AddressBook">Address Book</option>
				<option value="ca">CA</option>
			</select> <INPUT type=button value=获取证书列表 onclick="ListCert()">
		</p>
		<p>
			选择一个证书：<br> <select id="allCerts" size="6" name="allCerts"
				onchange="SelectedCert()">
			</select> <br> <input id="Button8" onclick="DisplayCert()" type="button"
				value="显示选定证书信息" name="btPrivateKey">
		<div id="showCert"></div>
		<br> <br> 2.读取用户证书信息 <br> <br> <INPUT
			type="button" value=获取私钥信息 onclick="return GetPrivateInfo()">
		<INPUT name="button" type=button onclick="return GetPublicInfo()"
			value=获取公钥信息> <INPUT name="button2" type=button
			onclick="return GetExtInfo()" value=获取扩展信息> <br>
		<p></p>
		<div id="datadiv"></div>

		3.数字信封 <br> <br> 明文： <input name="srcText" size=40>
		加密算法： <select name="EncryptAlgo"
			style="LEFT: 4px; WIDTH: 139px; TOP: 75px">
			<option value=3 selected>CALG_3DES</option>
			<option value=2>CALG_DES</option>
			<option value=1>CALG_RC4</option>
		</select> <br> <br> 密文：
		<textarea name="desEnvText" cols="100" rows="5" wrap="OFF"></textarea>
		<br> <br> 解密后的明文: <input name="unEnvText" size=40> <br>
		<br> <input name="button3" type=button
			onClick="return btnEncryptData_onclick()" value=加密>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="button32" type=button
			onClick="return btnDecryptData_onclick()" value=解密> <br>
		<br> 4.签名验签 <br> <br> 明文：&nbsp; <input
			name="srcSignText" size=40> 哈希算法： <select name="HashAlgo"
			style="LEFT: 4px; WIDTH: 139px; TOP: 75px">
			<option value=0 selected>SHA1</option>
			<option value=3>MD5</option>
		</select> <br> <br> <INPUT type=button value=签名
			onclick="return SignText()"> <br> <br> 签名值:
		<textarea name="signText" cols="100" rows="5"></textarea>
		<br> <br> <INPUT type=button value=验签
			onclick="return VerifySign()"> <br> <br>
	</form>
	<form  name="verifySignForm" method="post">
		<input type="hidden" name="signval" id="signval">
		<input type="hidden" name="digstval" id="digstval">
		<p>用户名: <input type="text" name="username"></p>
		<p>密&nbsp;&nbsp;&nbsp;&nbsp;码: <input type="text" name="password"></p>
		<p><input type="button" value="BC登 录" onclick="login('bc')">&nbsp;<input type="button" value="IAIK登 录" onclick="login('iaik')"></p>
	</form>
</body>
</html>
