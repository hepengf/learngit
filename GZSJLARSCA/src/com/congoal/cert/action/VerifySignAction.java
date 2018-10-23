package com.congoal.cert.action;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.pkcs.pkcs7.ContentInfo;
import iaik.pkcs.pkcs7.SignedData;
import iaik.security.provider.IAIK;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataStreamGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.context.annotation.Scope;

import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.SHACoder;

@Scope("prototype")
@SuppressWarnings("all")
public class VerifySignAction extends  BasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4675435341882775719L;

	/**
	 * 使用BouncyCastleProvider作为服务提供者
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginWithBc() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String signval = request.getParameter("signval");
		// String digstval = request.getParameter("digstval");

		// System.out.println("username: " + username + ",password: " +
		// password);
		// System.out.println("摘要: " + digstval);//前台传入经SHA1的摘要
		System.err.println("签名: " + signval);// 前台传入经CAPICOM的签名

		// String md5Str = MD5Coder.encodeMD5Hex("mooke123");

		byte[] byteSignRes = Base64.decode(signval);
		try {
			// 添加BouncyCastle作为安全提供
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			// 新建PKCS#7签名数据处理对象
			CMSSignedData sd = new CMSSignedData(byteSignRes);

			byte[] digest_data = (byte[]) sd.getSignedContent().getContent();// 获取签名的原始数据
			System.err.println("BC摘要：" + Arrays.toString(digest_data));

			String _digest_data = SHACoder.encodeSHAHex(username + password);// 后台生成的摘要，生成的为小写

			String digest_str = Arrays.toString(digest_data);
			String _digest_str = Arrays.toString(_digest_data.toUpperCase()
					.getBytes("UTF-16LE"));// 将后台生成的摘要，转换成大写，再转换成16进制字符串

			System.err.println("前台摘要：" + digest_str + ",后台摘要：" + _digest_str);

			if (_digest_str.equals(digest_str)) {
				System.err.println("BC验证摘要成功");
			} else {
				return null;
			}

			// 获得证书信息
			CertStore certs = sd.getCertificatesAndCRLs("Collection", "BC");

			// 获得签名者信息
			SignerInformationStore signers = sd.getSignerInfos();

			Collection c = signers.getSigners();
			Iterator it = c.iterator();

			// 当有多个签名者信息时需要全部验证
			while (it.hasNext()) {
				SignerInformation signer = (SignerInformation) it.next();

				byte[] data = signer.getSignature();// 签名后的数据
				System.out.println(("签名后数据Base64: " + new String(Base64
						.encode(data), "utf8")));

				// 证书
				X509CertSelector xs = new X509CertSelector();
				xs.setSerialNumber(signer.getSID().getSerialNumber());
				Collection certCollection = certs.getCertificates(xs);
				
				//Collection certCollection = certs.getCertificates(signer.getSID());

				Iterator certIt = certCollection.iterator();
				X509Certificate cert = (X509Certificate) certIt.next();
				
				System.err.println("SubjectDN: " + cert.getSubjectDN().getName()+ ",IssuerDN: " + cert.getIssuerDN().getName());

				PublicKey publicKey = cert.getPublicKey();// 获取证书公钥
				// System.out.println(Arrays.toString(publicKey.getEncoded()));

				// 验证数字签名
				if (signer.verify(publicKey, "BC")) {
					System.out.println("BC验证数字签名成功");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BC验证数字签名失败");
		}

		return SUCCESS;
	}

	/**
	 * 使用IAIK作为服务提供者
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginWithIAIK() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String signval = request.getParameter("signval");
		// String digstval = request.getParameter("digstval");

		// System.out.println("username: " + username + ",password: " +
		// password);
		// System.out.println("摘要: " + digstval);//前台传入经SHA1的摘要
		// System.err.println("签名: " + signval);//前台传入经CAPICOM的签名

		// String md5Str = MD5Coder.encodeMD5Hex("mooke123");

		// 使用IAIK验证摘要
		byte[] digest_data = VerrifyByIAIK(signval);// 从签名中获取前台签名的摘要
		String _digest_data = SHACoder.encodeSHAHex(username + password);// 后台生成的摘要，生成的为小写

		String digest_str = Arrays.toString(digest_data);
		String _digest_str = Arrays.toString(_digest_data.toUpperCase()
				.getBytes("UTF-16LE"));// 将后台生成的摘要，转换成大写，再转换成16进制字符串

		System.err.println("前台摘要：" + digest_str + ",后台摘要：" + _digest_str);

		if (_digest_str.equals(digest_str)) {
			System.err.println("IAIK验证摘要成功");
		}

		// 后台做签名、验签
		// boolean result = Verify(username + password);
		return null;
	}

	private byte[] VerrifyByIAIK(String signData) throws Exception {
		Security.insertProviderAt(new IAIK(), 2);
		InputStream is = new ByteArrayInputStream(signData.getBytes());

		ASN1 obj = new ASN1(is);

		// 通过ASN1构造ASN1Object目的是访问ASN1类型的值
		ASN1Object asn1 = (ASN1Object) obj.toASN1Object();

		// 通过ASN1Object构造PKCS#7中的ContentInfo
		ContentInfo ci = new ContentInfo(asn1);

		// 获取PKCS#7内容类型为SignedData的对象
		SignedData signed_data = (SignedData) ci.getContent();

		// 获得PKCS#7SignedData类型中的certificates
		X509Certificate x9 = signed_data.getCertificates()[0];
		System.out.println("从摘要中获取的公钥："
				+ Arrays.toString(x9.getPublicKey().getEncoded()));

		// 验证证书有效期
		x9.checkValidity(new Date());
		System.err.println("SubjectDN: " + x9.getSubjectDN().getName()
				+ ",IssuerDN: " + x9.getIssuerDN().getName());

		// 获得PKCS#7中SignedData类型中的消息摘要
		byte[] digest_data = signed_data.getContent();

		return digest_data;
	}

	// 后台做签名、验签（先签名，后Base64）
	/*private boolean Verify(String signData) {
		try {
			// 后台签名
			String keyStorePath = "D:\\workspace10\\CertWebTest\\WebRoot\\web\\cert\\client.p12";
			String certificatePath = "D:\\workspace10\\CertWebTest\\WebRoot\\web\\cert\\client.cer";
			String alias = "1";
			String password = "123456";
			byte[] ccRes = CertificateCoder.sign(signData.getBytes(),
					keyStorePath, alias, password, certificatePath);
			byte[] enCcRes = Base64.encode(ccRes);
			System.out.println(new String(enCcRes));
			// 验签名
			return CertificateCoder.verify(signData.getBytes(), ccRes,
					certificatePath);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}*/

	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param signText
	 *            原始数据
	 * @param keystore
	 *            密钥库
	 * @param keyalias
	 *            密钥别名
	 * @param password
	 *            密钥库密码
	 * @return 签名后数据
	 * @throws Exception
	 */
	public static byte[] sign(byte[] signText, KeyStore keystore,
			String keyalias, char[] password) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		Enumeration en = keystore.aliases();

		// 遍历密钥库的密钥别名
		while (en.hasMoreElements()) {
			String alias = (String) en.nextElement();
			//System.out.println("alias: "+alias);
			if (!alias.equalsIgnoreCase(keyalias))
				continue;

			// 根据别名从证书中获取私钥
			PrivateKey prikey = (PrivateKey) keystore
					.getKey(keyalias, password);
			X509Certificate x509 = null;

			// 获取该别名对应的证书链
			Certificate[] certs = keystore.getCertificateChain(keyalias);
			if (certs[0] instanceof X509Certificate) {// user's certificate
				x509 = (X509Certificate) certs[0];
			}
			//if (certs[certs.length - 1] instanceof X509Certificate) {// root
			//															// certificate
			//	x509 = (X509Certificate) certs[certs.length - 1];
			//}

			// String SIGNATURE_ALGORITHM = "SHA1";
			String SIGNATURE_ALGORITHM = CMSSignedDataStreamGenerator.DIGEST_SHA1;
			gen.addSigner(prikey, x509, SIGNATURE_ALGORITHM, "BC");

			CertStore certstore = CertStore.getInstance("Collection",
					new CollectionCertStoreParameters(Arrays.asList(certs)),
					"BC");

			gen.addCertificatesAndCRLs(certstore);
		}
		// 输出字节流
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:/JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, true);// false,不含原始数据，减轻传输负担
		sigOut.write(signText);// 对原始数据进行签名
		sigOut.close();

		byte[] signedData = bOut.toByteArray();// 签名后数据
		bOut.close();
		byte[] signedDataBase64 = Base64.encode(signedData);// 签名后数据做base64加密

		// System.out.println("签名后数据： " + new String(signedData, "utf8") + " ");
		return signedDataBase64;
	}

	/**
	 * 验证pkcs7格式的签名数据
	 * 
	 * @param signedData
	 *            pkcs7格式的签名数据
	 * @param digest
	 *            原摘要
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verify(byte[] signedData, String digest)
			throws Exception {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		CMSSignedData sign = new CMSSignedData(signedData);

		// System.out.println(sign.getSignedContent());

		byte[] digest_data = (byte[]) sign.getSignedContent().getContent();// 获取签名的原始数据
		System.err.println("验签中-摘要：" + new String(digest_data).toUpperCase()+"   验证摘要: "+digest.trim().equals(new String(digest_data).toUpperCase().trim()));

		CertStore certs = sign.getCertificatesAndCRLs("Collection", "BC");
		SignerInformationStore signers = sign.getSignerInfos();
		Collection c = signers.getSigners();
		Iterator it = c.iterator();
		boolean bresult = true;

		// 当有多个签名者信息时需要进行全部验证
		while (it.hasNext()) {
			SignerInformation signer = (SignerInformation) it.next();

			byte[] data = signer.getSignature();// 签名后的数据
			System.out.println(("验签中-签名后数据Base64: " + new String(Base64
					.encode(data), "utf8")));

			X509CertSelector xs = new X509CertSelector();
			xs.setSerialNumber(signer.getSID().getSerialNumber());
			// certs.getCertificates(signer.getSID());

			Collection certCollection = certs.getCertificates(xs);
			Iterator certIt = certCollection.iterator();
			X509Certificate cert = (X509Certificate) certIt.next();// 证书链

			System.err.println("SubjectDN: " + cert.getSubjectDN().getName()+ ",IssuerDN: " + cert.getIssuerDN().getName());
			
			if (signer.verify(cert.getPublicKey(), "BC")) {// 验证过程
				System.out.println(" pkcs7 verifed success!");
			} else {
				bresult = false;
			}
		}
		return bresult;
	}

	/**
	 * 获得KeyStore
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return KeyStore 密钥库
	 * @throws Exception
	 */
	private static KeyStore getKeyStore(String keyStorePath, String password)
			throws Exception {

		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);

		// 加载密钥库
		ks.load(is, password.toCharArray());

		// 关闭密钥库文件流
		is.close();

		return ks;
	}

	public static void main(String[] args) throws Exception {
		// 后台生成的摘要，生成的为小写
		String _digest_data = SHACoder.encodeSHAHex("mooke123");

		System.err.println("签名中-摘要：" + _digest_data.toUpperCase());

		// String _digest_str = Arrays.toString(_digest_data.toUpperCase()
		// .getBytes("UTF-8"));// 将后台生成的摘要，转换成大写，再转换成16进制字符串

		byte[] signText = _digest_data.getBytes();

		String keyStorePath = "D:\\workspace10\\CertWebTest\\WebRoot\\web\\cert\\Client1Keystore.jks";//Client1Keystore.jks
		String password = "123456";
		// 获取密钥库
		KeyStore keystore = getKeyStore(keyStorePath, password);

		String keyalias = "client1";
		// 生成签名
		byte[] signedData = sign(signText, keystore, keyalias,
				password.toCharArray());
		System.out.println("签名中-签名后数据Base64： " + new String(signedData, "utf8")
				+ " ");
		System.out.println();

		// 验证签名
		boolean result = verify(Base64.decode(signedData), _digest_data.toUpperCase());

		System.err.println(result);
	}

}
