package com.congoal.cert.req1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;


@SuppressWarnings("deprecation")
public class CertificateChainDemo {
	public String caName = "1";// 别名
	public String caPasswd = "123456";

	public String basePath = "D:\\workspace10\\CertWebTest\\WebRoot\\web\\cert\\";
	public String keyStorePasswd = "123456";
	public String keyStorePath = basePath + "ca.keystore";

	public String userDN = "CN=hcx,   OU=CONGOAL,   O=HUST ,L=SZ, ST=Hubei, C=CN";
	public String userAlias = "hcx"; // 用户别名

	private String SignatureAlgorithm = "SHA1WithRSA";// 签名算法

	public CertificateChainDemo() {
	}

	/**
	 * 生成证书
	 * 
	 * @param userCertPath
	 *            生成证书的位置
	 * @return
	 */

	public boolean generateX509Certificate(String userCertPath) {
		try {
			// 添加BouncyCastle作为安全提供
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			FileInputStream in = new FileInputStream(keyStorePath);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(in, keyStorePasswd.toCharArray());
			in.close();

			// Get CA private key.获取CA的私钥
			PrivateKey caPrivateKey = (PrivateKey) ks.getKey(caName,
					caPasswd.toCharArray());

			System.out.println("\nCA private key:\n" + caPrivateKey);

			// Get CA DN. 获取CA证书
			Certificate c = ks.getCertificate(caName);
			X509Certificate t = (X509Certificate) c;
			String caDN = t.getIssuerDN().toString();

			// CN:姓氏、名字 OU:组织单位名称 O:组织名称 L:城市、区域 C:国家代码
			System.out.println("\nCA DN:\n" + caDN);

			X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
			KeyPair KPair = RSAKeyPairGenDemo.getRSAKeyPair(1024);
			System.out.println("\nuser private key:\n" + KPair.getPrivate());
			System.out.println("\nuser public key:\n" + KPair.getPublic());

			v3CertGen.setSerialNumber(BigInteger.valueOf(1));
			v3CertGen.setIssuerDN(new X509Principal(caDN));
			// 证书有效期起
			v3CertGen.setNotBefore(new Date(System.currentTimeMillis()));
			// 证书有效期止
			v3CertGen.setNotAfter(new Date(System.currentTimeMillis()
					+ (1000L * 60 * 60 * 24 * 365)));
			v3CertGen.setSubjectDN(new X509Principal(userDN));
			v3CertGen.setPublicKey(KPair.getPublic());
			v3CertGen.setSignatureAlgorithm(SignatureAlgorithm);

			// 生成扩展项
			// v3CertGen.addExtension(X509Extensions.SubjectKeyIdentifier,
			// false, createSubjectKeyIdentifier(KPair.getPublic()));
			//
			// v3CertGen.addExtension(X509Extensions.AuthorityKeyIdentifier,
			// false, createAuthorityKeyIdentifier(t.getPublicKey()));

			v3CertGen.addExtension(X509Extensions.BasicConstraints, false,
					new BasicConstraints(false));

			// Generate user certificate signed by ca private key.
			X509Certificate cert = v3CertGen.generate(caPrivateKey, "BC");

			System.err.println(cert.getSubjectDN().getName());
			// X509Certificate cert =
			// v3CertGen.generateX509Certificate(caPrivateKey);
			FileOutputStream out = new FileOutputStream(userCertPath);
			out.write(cert.getEncoded());
			out.close();

			// Add user entry into keystore
			ks.setCertificateEntry(userAlias, cert);
			out = new FileOutputStream(keyStorePath);
			ks.store(out, caPasswd.toCharArray());
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void listX509CertificateInfo(String certFile) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			X509Certificate x509Cert = (X509Certificate) cf
					.generateCertificate(new FileInputStream(certFile));
			System.out.println("\nIssuerDN:" + x509Cert.getIssuerDN());
			System.out.println("Signature   alg:" + x509Cert.getSigAlgName());
			System.out.println("Version:" + x509Cert.getVersion());
			System.out.println("Serial   Number:" + x509Cert.getSerialNumber());
			System.out.println("Subject   DN:" + x509Cert.getSubjectDN());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证证书
	 * 
	 * @param certPath
	 *            证书路径
	 * @return
	 */
	public boolean Verify(String certPath) {
		Certificate cert;
		PublicKey caPublicKey;

		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			FileInputStream in = new FileInputStream(certPath);
			cert = cf.generateCertificate(in);
			in.close();

			X509Certificate t = (X509Certificate) cert;
			Date timeNow = new Date();
			t.checkValidity(timeNow);

			in = new FileInputStream(keyStorePath);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(in, keyStorePasswd.toCharArray());
			in.close();
			// 获取CA证书公钥
			caPublicKey = ks.getCertificate(caName).getPublicKey();
			System.out.println("\nCA public key:\n" + caPublicKey);
			try {
				cert.verify(caPublicKey);

			} catch (Exception e) {
				System.out.println("no pass.\n");
				e.printStackTrace();
			}
			System.err.println("验证成功");
		} catch (CertificateExpiredException e1) {
			e1.printStackTrace();
		} catch (CertificateNotYetValidException e1) {
			e1.printStackTrace();
		} catch (CertificateException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	public static void main(String args[]) {
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		// System.err.println(sdf.format(new Date()));
		String certname = "cert" + sdf.format(new Date());
		String userCertPath = "D:\\workspace10\\CertWebTest\\WebRoot\\web\\newcerts\\"
				+ certname + ".cer";
		CertificateChainDemo ccd = new CertificateChainDemo();
		ccd.generateX509Certificate(userCertPath);
		// ccd.listX509CertificateInfo(userCertPath);
		ccd.Verify(userCertPath);
	}

}
