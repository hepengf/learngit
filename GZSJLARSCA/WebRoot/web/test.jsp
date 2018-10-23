<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>test演示页面</title>
<OBJECT id="oCAPICOM"
	codeBase="http://download.microsoft.com/download/E/1/8/E18ED994-8005-4377-A7D7-0A8E13025B94/capicom.cab#version=2,0,0,3"
	classid="clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679" VIEWASTEXT>
</OBJECT>
<script type="text/javascript" src="extraInfo.js"></script>
<script type="text/javascript" src="lang/en_us.js"></script>
<script type="text/javascript" src="lang/zh_cn.js"></script>
<script type="text/javascript" src="lang/zh_tw.js"></script>
<script>
	var CAPICOM_STORE_OPEN_READ_ONLY = 0;
	var CAPICOM_STORE_OPEN_READ_WRITE = 1;
	var CAPICOM_CURRENT_USER_STORE = 2;
	var CAPICOM_CERTIFICATE_FIND_SUBJECT_NAME = 1;
	var CAPICOM_CERTIFICATE_FIND_EXTENDED_PROPERTY = 6;
	var CAPICOM_CERTIFICATE_FIND_TIME_VALID = 9;
	var CAPICOM_CERTIFICATE_FIND_KEY_USAGE = 12;
	var CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE = 0x00000080;
	var CAPICOM_AUTHENTICATED_ATTRIBUTE_SIGNING_TIME = 0;
	var CAPICOM_E_CANCELLED = -2138568446;
	var CERT_KEY_SPEC_PROP_ID = 6;
	var CAPICOM_VERIFY_SIGNATURE_AND_CERTIFICATE = 1;
	var CAPICOM_ENCODE_BASE64 = 0;
	var CAPICOM_HASH_ALGORITHM_SHA1 = 0;

	function test() {
		var ret = "";
		var SignedData = new ActiveXObject("CAPICOM.SignedData"); //数字签名对象 
		var Signer = new ActiveXObject("CAPICOM.Signer"); //签名人对象 
		var ss = "mooke123";
		Signer.Options = 2;
		try {
			//以下从证书列表中获取签名证书 
			var Store = new ActiveXObject("CAPICOM.Store"); //证书存贮对象 
			Store.Open(CAPICOM_CURRENT_USER_STORE, "My",
					CAPICOM_STORE_OPEN_READ_ONLY);//var CAPICOM_STORE_OPEN_READ_WRITE = 1;
					
			
			//Certificates.Find( _ByVal FindType, _[ ByVal varCriteria ], _[ ByVal bFindValidOnly ] _)
			//var FilteredSignCertificates = Store.Certificates.
			//			Find(CAPICOM_CERTIFICATE_FIND_KEY_USAGE,CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE).
			//			Find(CAPICOM_CERTIFICATE_FIND_TIME_VALID).
			//			Find(CAPICOM_CERTIFICATE_FIND_EXTENDED_PROPERTY,CERT_KEY_SPEC_PROP_ID); //证书列表对象 
			
			var FilteredSignCertificates = Store.Certificates.Find(CAPICOM_CERTIFICATE_FIND_SUBJECT_NAME,"GNETE");
			
			/*if (FilteredSignCertificates.Count == 1)
				Signer.Certificate = FilteredSignCertificates.Item(1);
			else if (FilteredSignCertificates.Count > 1)
				Signer.Certificate = FilteredSignCertificates.Select("选择签名证书",
						"请选中你的签名证书，按确定").Item(1);
			else
				alert("无法读取可供选择的证书！");*/
			
			if (FilteredSignCertificates.Count > 0)
				Signer.Certificate = FilteredSignCertificates.Select("选择签名证书","请选中你的签名证书，按确定").Item(1);
			else
				alert("无法读取可供选择的证书！");
				
			
			//SignedData.Content = hex_sha1(ss); //对原文摘要签名 
			//ret = SignedData.Sign(Signer, false, CAPICOM_ENCODE_BASE64);
			//alert(ret);
		} catch (e) {
			alert("签名过程中出现错误: " + e.description);
		}
		return ret;
	}
	
	var hexcase = 0; 
	var chrsz = 8; 
	function hex_sha1(s){return binb2hex(core_sha1(str2binb(s),s.length * chrsz));} 
	
	function core_sha1(x, len){ 
		x[len >> 5] |= 0x80 << (24 - len % 32); 
		x[((len + 64 >> 9) << 4) + 15] = len; 
		var w = Array(80); 
		var a = 1732584193; 
		var b = -271733879; 
		var c = -1732584194; 
		var d = 271733878; 
		var e = -1009589776; 
		for(var i = 0; i < x.length; i += 16){ 
		var olda = a; 
		var oldb = b; 
		var oldc = c; 
		var oldd = d; 
		var olde = e; 
		for(var j = 0; j < 80; j++){ 
			if(j < 16) 
				w[j] = x[i + j]; 
			else w[j] = rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1); 
				var t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)), safe_add(safe_add(e, w[j]), sha1_kt(j))); 
				e = d; 
				d = c; 
				c = rol(b, 30); 
				b = a; 
				a = t; 
		} 
			a = safe_add(a, olda); 
			b = safe_add(b, oldb); 
			c = safe_add(c, oldc); 
			d = safe_add(d, oldd); 
			e = safe_add(e, olde); 
		} 
		return Array(a, b, c, d, e); 
	} 
	
	function sha1_ft(t, b, c, d){ 
		if(t < 20) return (b & c) | ((~b) & d); 
		if(t < 40) return b ^ c ^ d; 
		if(t < 60) return (b & c) | (b & d) | (c & d); 
		return b ^ c ^ d; 
	} 
	
	function login()
	{
		//在原有基础之上创建一个HashedData对象，用于在前台对明文计算SHA1摘要：
		var HashedData = new ActiveXObject("CAPICOM.HashedData");

		HashedData.Algorithm=CAPICOM_HASH_ALGORITHM_SHA1;//指定SHA1算法

		//将表单中需要进行签名的（多个）域串起来，传给一个自定义变量theOne
		//var theOne=document.all.con.value+document.all.con1.value+document.all.con2.value;
		var theOne = "mooke123";
		//将变量theOne的值传给表单中的名为theOne的隐藏域，以便传到后台
		//将Unicode转换为ANSII后进行Hash运算，即求得明文的信息摘要
		//var hashTheOne = HashedData.Hash(Unicode2Ansi(theOne));
		HashedData.Hash(theOne);
		var hashTheOne = HashedData.value;
		//SignedData.Content =HashedData.Value;//将（Hash）摘要传给签名对象，进行签名。
		
		alert(hashTheOne);
		
	}
</script>

</head>

<body>
	<input type="button" value="测试" onclick="test()"><br/>
	<input type="button" value="登录" onclick="login()">
</body>
</html>
