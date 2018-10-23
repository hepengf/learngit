package com.congoal.cert.test;

import java.awt.TrayIcon.MessageType;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 证书链验证
 * @author charles
 *
 */
public class CertValidatorTest {
	private final static String path = "E:/dev_workspace/me14/CCA/WebRoot/certs/new/sub/";
	/**
	 * 信任库地址(不含私钥)
	 */
	private final static String keyStorePath = "E:/dev_workspace/me14/CCA/WebRoot/certs/root/root.keystore";
	
	private final static String CA = "E:/dev_workspace/me14/CCA/WebRoot/certs/root/root.cer";

	/**
	 * 证书签名有效性验证
	 * @param CACertPath CA证书路径
	 * @param certPath 证书路径
	 * @return
	 * @throws Exception
	 */
	public static boolean CertSignatureValidator(String CACertPath,String certPath) throws Exception{
		//获取待校验证书的X509Certificate类型对象
		CertificateFactory cf=CertificateFactory.getInstance("X.509");
		FileInputStream in=new FileInputStream(CACertPath);
		Certificate  ca =cf.generateCertificate(in);
		in.close();
		
		PublicKey publicKey = ca.getPublicKey();
				
		//获取待校验证书的X509Certificate类型对象
//		CertificateFactory cf=CertificateFactory.getInstance("X.509");
		in=new FileInputStream(certPath);
		Certificate  cert =cf.generateCertificate(in);
		in.close();
		
        try{
        	cert.verify(publicKey);
        	System.out.println("证书签名合法");
		    return Boolean.TRUE;
        }catch(Exception e){
           e.printStackTrace();
           return Boolean.FALSE;
		}
	}
	
	
	
	/**
	 * 证书有效期验证
	 * @param certPath 证书路径
	 * @return
	 * @throws Exception
	 */
	public static boolean CertValidValidator(String certPath) throws Exception{
		//获取X509Certificate类型对象
		CertificateFactory cf=CertificateFactory.getInstance("X.509");
		FileInputStream in=new FileInputStream(certPath);
		Certificate  certificate=cf.generateCertificate(in);
		X509Certificate t=(X509Certificate)certificate;
		System.out.println("Signature: "+t.getSignature().length);
		in.close();
		
		//获取日期
		Date TimeNow=new Date();
		//检验有效性
		try{
		   t.checkValidity(TimeNow);
		   System.out.println("证书有效");
		   
		   return Boolean.TRUE;
		}catch(CertificateExpiredException e){  //过期
		   System.out.println("Expired");
		   System.out.println(e.getMessage());
		   return Boolean.FALSE;
		}catch(CertificateNotYetValidException e){ //尚未生效
		   System.out.println("Too early");
		   System.out.println(e.getMessage());
		   return Boolean.FALSE;
		}
	}
	/**
	 * 证书链验证
	 */
	public static void CertPathValidator() throws Exception
	{

//		String trustAnchor = "F:/tempWorkSpaces/SSLSocket/test/certs/ca.crt";
		String trustAnchor = "E:/dev_workspace/me14/CCA/WebRoot/certs/root/root.cer";
		
		//签发的证书链 A > B > C 把B、C证书作为构造证书链路径 A为信任证书
		String[] arg = new String[] { path+"CN20150204104656_0000000000003881.cer"};
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		List<Certificate> mylist = new ArrayList<Certificate>();
		for (int i = 0; i < arg.length; i++) {
			FileInputStream in = new FileInputStream(arg[i]);
			Certificate c = cf.generateCertificate(in);
			mylist.add(c);
			
			in.close();
		}
		CertPath cp = cf.generateCertPath(mylist);
		// 以上将证书列表转换成证书链
		
		
		// 设置锚点
		FileInputStream in = new FileInputStream(trustAnchor);
		Certificate trust = cf.generateCertificate(in);
		// Create TrustAnchor
		//设置信任证书
		TrustAnchor anchor = new TrustAnchor((X509Certificate) trust, null);
		
//		// Set the PKIX parameters
//		KeyStore ks = KeyStore.getInstance("JKS");
//		// 设置信任库
//		FileInputStream fis = new FileInputStream(keyStorePath);
//		// 从指定的输入流中加载此 KeyStore
//		ks.load(fis, "123456".toCharArray());
//		PKIXParameters params = new PKIXParameters(ks);
		
		PKIXParameters params = new PKIXParameters(Collections.singleton(anchor));
		
		// Disable CRL checking since we are not supplying any CRLs
		params.setRevocationEnabled(false);
		
		CertPathValidator cpv = CertPathValidator.getInstance(CertPathValidator.getDefaultType());//"PKIX"
		try {
			PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult) cpv.validate(cp, params);
			System.out.println(result);
			System.out.println("===================================================");
			System.out.println(result.getTrustAnchor().getTrustedCert().getIssuerDN());
		} catch (CertPathValidatorException cpve) {
			System.out.println("Validation failure, cert[" + cpve.getIndex()
					+ "] :" + cpve.getMessage());
		}
		
		in.close();
	
	}
	
	public static void main(String args[]) throws Exception {
		//验证签名有效性
//		CertSignatureValidator(CA,path+"CN20150204104656_0000000000003881.cer");
		//验证证书有效期
		CertValidValidator(path+"CN20150204124934_0000000000003881.cer");
		//验证证书路径
//		CertPathValidator() ;
	}
}
