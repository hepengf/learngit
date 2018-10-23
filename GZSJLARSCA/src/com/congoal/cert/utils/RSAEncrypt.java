package com.congoal.cert.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;








//import org.apache.abdera.security.util.KeyHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.congoal.cert.req.CertUtil;
import com.congoal.cert.test.CertUtils;


public class RSAEncrypt {
	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 随机生成密钥对
	 */
	public static void genKeyPair(String filePath) {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024,new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		System.out.println("publicKey: "+publicKey.getEncoded().length);
		System.out.println("privateKey: "+privateKey.getEncoded().length);
		try {
			// 得到公钥字符串
			String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
			// 得到私钥字符串
			String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
			// 将密钥对写入到文件
			FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			BufferedWriter pribw = new BufferedWriter(prifw);
			pubbw.write(publicKeyString);
			pribw.write(privateKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
			pribw.flush();
			pribw.close();
			prifw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 随机生成密钥对
	 */
	public static void genKeyPair(String filePath, byte[] seed) {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024,new SecureRandom(seed));
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		try {
			// 得到公钥字符串
			String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
			// 得到私钥字符串
			String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
			// 将密钥对写入到文件
			FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			BufferedWriter pribw = new BufferedWriter(prifw);
			pubbw.write(publicKeyString);
			pribw.write(privateKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
			pribw.flush();
			pribw.close();
			prifw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static String loadPublicKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/publicKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}
	
	/**
	 * 从输入流中加载公钥
	 * @param input
	 * @return
	 */
	public static String loadPublicKeyByUploadFile(File file) throws Exception{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
////			byte[] buffer = Base64.decodeBase64(publicKeyStr);
//			byte[] buffer=Hex.decodeHex(publicKeyStr.toCharArray());
//			
////			byte[] buffer = publicKeyStr.getBytes();
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			
//			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
			
			
			String exponentHex = "10001";
			 
			KeyFactory factory = KeyFactory.getInstance("RSA");
			 
			BigInteger n = new BigInteger(publicKeyStr, 16);
			BigInteger e = new BigInteger(exponentHex, 16);
			RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);
			 
			return (RSAPublicKey) factory.generatePublic(spec);
			
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static String loadPrivateKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/privateKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer=Hex.decodeHex(privateKeyStr.toCharArray());
//			byte[] buffer = Base64.decodeBase64(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	public static void main(String[] args) {
//		String pk = "308204bc020100300d06092a864886f70d0101010500048204a6308204a20201000282010100a6d50fa26fc91cc282207e4ff5d33148131ff5c8cc00453bff101eabe31ea8bbb177a9d1137868fa194314d3f6aec627671de0369b5d24c3bd6fbde2503a04915873145aa249825e299634f6017b94945441416d4ea54ff8a76ac084cbeae4361b05f1b330b5b147a455700d89b0fbd08ba8711caf6b5fda11a070fabb34b25ec9d6fac1326df46aeaa3f61912a12df0d05a46f9469d5a5d7f2a58bbc9a5a5acf7e97db9d264d7ad74c58927d2675f7de4b4eefe321d861a7792ba56e7b46a9016b64d4937f7bc9f6567defa3b2c03fe679acf5347db6c3fa9bb2e2b8712995ac57a9c36036e6aa8b87241d30c3700c07500543305582bc245b297cd7ed0293f0203010001028201006f6bf90729d58da9b2b2f4e97e6ad08d2c88b9e4a3aac292b16518b6fff72806431e82822a98ceb3ca4f6d07abc74a7c6278279910e682796079d2bde58afa260086cb5f44e80773d01e8bbc897cf9dc9ef0fe3b7cbd17ce203241e5ef332fbb9b0cbb424c13ed14c60271d5f8d2053c6129dc71a60ef67374364d7e87ad483ac3da8bcf5523bedf80b0dd45d1215cf357fdb3f5f4083a65e3226387ae0b5eb1ca7b6e0927057567fbcc164b4b400ec03b153ac5f17d328fc58b503ee0f5b15e25d5ef7179e0ceaac1191457d7a3d6a74ec18642a7475710127b9563ef9cbf2f01f18cacfc4934d719163979b127fcd108417653c1a1ed588867f042241e78d102818100ec3583e69e30fd48116ef6f48b2d154146f8cea6b5f5168d6b491b158846ffd6d2a35b61c74715c16b04d6b0d1ccbc6bc49fc2bd30682f72fe99fc257559539f32f52dd0de4021feb8f045ff1d8157cecf55fa3cc7a2dfde573bd3e90c6fff419fd0ffb4ee2526c55063b50db75d564594f0d1bd83f82c54394998426d4f8d9302818100b4cf78f8c6c6db050bb6f7c7ede4b537b6aad949021c41401882ac9c92825b2121a6fc8bbd7feba67a48484d47ae91cdb8214dd4a3b054e7d4e56281c734ca40c6d0736d34ea494028af1b04496dcf09acc80c9171c3476147331ee2cc9b7937ccb91e5dea3102795b766ba4daffc9ccc96050061f361a6fcdf75163e7246125028180762e985e0c8c0a4dc29b386846323a4e3cca43ead96354f350d874faa3029302d2c4f4f5c914e1e7b239eb88f08f753e9e5428c52bb114fc7e32611c04c8bab40e74e985758db4f49367743e1e6f1695dcdaf1a6f363a48ac42ea4ea1754f9ebc5a0762ffa6f1024ed01d61694c5d8625c10d97feec57eb86d294b4b01122a8d02818017d150b52cb21b3dd1ea3b3f99e5cbbd5811af67bfa06b33ff468f685ca05152cef509457b23f3adcd63e22a53ebfa06e85f72ba3509d0556da4fdd1eca4d693abdcdfa79e15d2aa61f197147c7bbb8f21b4847b504a12080d71fd7a90cb562f74875a29b2e4dbd792a5c9ce408efb4d3fa5b144ed2d9a53ea04cf32e2cad7050281800434e532c83202d814d5c7d2096b82f4430fcab00a7c6d4162ee297ec100c6b28c8179a9fb4dd17c9fa98fcf5f7034ca5ce10b20a2fe3bd2afc7fe8d3dfef0dbea7903edd0c95b8115aa9a89d7063b8cc3e966e3108ffb9ba2d02725b47c4d8ecc4de0e716a3b7eae74472533262b5d3f6663f58c870864b5ce84c655e0133b3";
//		try {
//			PrivateKey privateKey = loadPrivateKeyByStr(pk);
//			System.out.println(CertUtils.bytesToHexString(privateKey.getEncoded()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		String seeds = "^^^^^ITKSDLKDFLJS655sd65sd556WW454BB5656sdfs8888&&sdswwwsdsdflkwoes";
//		System.out.println(seeds.length());
//		byte[] seed = seeds.getBytes();
		String filePath = CertCommonUtils.ROOTCA_PATH;
		genKeyPair(filePath);
//		genKeyPair(filePath, seed);
		
		try {
			String privateKeys = loadPrivateKeyByFile(filePath);
			String publicKeys = loadPublicKeyByFile(filePath);
			
			System.out.println("privateKeys: "+privateKeys);
			System.out.println("publicKeys: "+publicKeys);
			
			byte[] prk = Base64.decodeBase64(privateKeys.replaceAll(" ", ""));
			byte[] pubK = Base64.decodeBase64(publicKeys.replaceAll(" ", ""));
			
			String privateKey1 = CertUtils.bytesToHexString(prk);
			String publicKey1 = CertUtils.bytesToHexString(pubK);
			System.out.println("privateKey1: "+privateKey1);
			System.out.println("publicKey1: "+publicKey1);
			
			PrivateKey privateKey = loadPrivateKeyByStr(privateKey1.replaceAll(" ", "").toUpperCase());
			PublicKey publicKey = loadPublicKeyByStr(publicKey1.replaceAll(" ", "").toUpperCase());
			System.out.println("privateKey: "+CertUtils.bytesToHexString(privateKey.getEncoded()));
			System.out.println("publicKey: "+CertUtils.bytesToHexString(publicKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 私钥加密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 私钥解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 公钥解密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

}

