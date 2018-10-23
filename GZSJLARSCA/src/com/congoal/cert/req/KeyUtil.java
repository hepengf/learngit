package com.congoal.cert.req;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class KeyUtil {
	private static Logger logger = Logger.getLogger(KeyUtil.class);

	public static KeyPair generateKeyPair(int keySize) {
		Security.addProvider(new BouncyCastleProvider());
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
			keyGen.initialize(keySize, new SecureRandom());
			KeyPair keyPair = keyGen.generateKeyPair();
			logger.info("Generate new keypair.");
			return keyPair;
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @param oriData
	 * @return digestpass base64的摘要结果
	 * 
	 *         sha-1摘要算法
	 * */
	public static String keyStorePassDgt(String oriData) {
		return keyStorePassDgt(oriData.getBytes());

	}

	public static String keyStorePassDgt(byte[] oriData) {
		byte[] digest = keyStorePassDgtByte(oriData);
		String digestpass = new String(Base64.encode(digest));

		return digestpass;
	}

	public static byte[] keyStorePassDgtByte(byte[] oriData) {
		MessageDigest dgt = null;
		try {
			dgt = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}

		return dgt.digest(oriData);
	}

}
