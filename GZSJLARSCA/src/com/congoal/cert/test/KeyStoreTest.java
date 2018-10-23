package com.congoal.cert.test;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.crypto.Cipher;

/**
 * http://blog.csdn.net/madun/article/details/8677783
 * 
 * @author charles 验证提取的私钥是否正确
 */
public class KeyStoreTest {
	private final static String PASSWORD = "654321";
	private final static String ALIAS = "CN20150113162742";
	private final static String keyStorePath = "F:/tempWorkSpaces/CCA/WebRoot/certs/new/sub/CN20150113162742.pfx";
	private final static String certPath = "F:/tempWorkSpaces/CCA/WebRoot/certs/new/sub/CN20150113162742.cer";
	private final static String keyStoreType = "PKCS12";//PKCS12 JKS
	
	
//	private final static String PASSWORD = "123456";
//	private final static String ALIAS = "1";
//	private final static String keyStorePath = "F:/tempWorkSpaces/CCA/WebRoot/certs/new/root/ROOTCA20150112175610.keystore";
//	private final static String certPath = "F:/tempWorkSpaces/CCA/WebRoot/certs/new/root/ROOTCA20150112175610.cer";
//	private final static String keyStoreType = "JKS";//PKCS12 JKS
	
	private static PrivateKey getPrivateKey(String pwd, String alias ,String path,String type)
			throws Throwable {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PrivateKey key1 = null;
		FileInputStream fis = null;
		try {
			// 读取keyStore文件:
			char[] password = pwd.toCharArray();
			fis = new FileInputStream(path);
			KeyStore ks = null;
			
			if ("PKCS12".equals(type.toUpperCase())) {
				ks = KeyStore.getInstance("PKCS12", "BC");
			} else if ("JKS".equals(type.toUpperCase())) {
				ks = KeyStore.getInstance("JKS");
			}
			// 从指定的输入流中加载此 KeyStore
			ks.load(fis, password);
			// keystore 中的每一项都用“别名”字符串标识。 使用指定保护参数获取指定别名的 keystore Entry。
			// KeyStore.PrivateKeyEntry 保存 PrivateKey 和相应证书链的 KeyStore 项。

			KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks
					.getEntry(alias, new KeyStore.PasswordProtection(password));
			key1 = pkEntry.getPrivateKey();
			System.out.println("PrivateKey: "+bytesToHexString(key1.getEncoded()));

			// 返回与给定别名相关联的密钥
			PrivateKey key2 = (PrivateKey) ks.getKey(alias, password);
			System.out.println("PrivateKey: "+bytesToHexString(key2.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis != null)
			{
				fis.close();
			}
		}

		return key1;
	}
	
	/**
	 * keytool -export -alias 1 -keystore ROOTCA20150110231219.keystore -file ROOTCA.cer
	 * @param pwd
	 * @param alias
	 * @return
	 * @throws Throwable
	 */
	private static PublicKey getPublicKey(String path) throws Throwable {
		PublicKey publicKey = null;
		FileInputStream in = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
		    in = new FileInputStream(path);
			
			//生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化。
			Certificate c = cf.generateCertificate(in);
			publicKey = c.getPublicKey();
			  
			System.out.println("PublicKey: "+bytesToHexString(publicKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null)
			{
				in.close();
				
			}
		}

		return publicKey;
	}

	public static void main(String[] args) throws Throwable {
		PrivateKey privateKey = getPrivateKey(PASSWORD, ALIAS,keyStorePath,keyStoreType);
		PublicKey publicKey = getPublicKey(certPath);
		
		//原文
		String before = "asdf";
        byte[] plainText = before.getBytes("UTF-8");
        // 用公钥进行加密，返回一个字节流
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(plainText);
        
        // 用私钥进行解密，返回一个字节流
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] newPlainText = cipher.doFinal(cipherText);
        System.out.println(new String(newPlainText, "UTF-8"));
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
