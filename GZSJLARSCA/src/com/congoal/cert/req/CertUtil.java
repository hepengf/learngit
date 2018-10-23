package com.congoal.cert.req;

import java.io.ByteArrayInputStream;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.congoal.cert.utils.CertCommonUtils;
import com.congoal.cert.utils.LogJob;

/**
 * 证书公共类
 * 
 * @author huang
 * 
 */
@Service("certUtil")
@Scope("prototype")
@SuppressWarnings("all" )
public class CertUtil extends LogJob{
	private Logger logger = Logger.getLogger(this.getClass());
	private String signAlgorithm = "SHA1WithRSA";

	/**
	 * 生成请求文件,输入文件名,SUBJECTDN,KEYPAIR;返回文件名；
	 * 
	 * @param fullFileName
	 * @param subjectDN
	 * @param subjectKeyPair
	 * @return
	 */
	public String createCertRequest(String fullFileName, String subjectDN,
			KeyPair subjectKeyPair) {
		try {
			PKCS10CertificationRequest req = new PKCS10CertificationRequest(
					signAlgorithm, new X509Name(subjectDN),
					subjectKeyPair.getPublic(), null,
					subjectKeyPair.getPrivate());
			FileOutputStream fout = new FileOutputStream(new File(fullFileName));
			fout.write(Base64.encode(req.getEncoded()));
			fout.close();
			return fullFileName;
		} catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}
		return null;
	}

	/**
	 * 生成请求对象,输入SUBJECTDN,KEYPAIR;返回请求对象；
	 * 
	 * @param subjectDN
	 * @param subjectKeyPair
	 * @return
	 */
	public PKCS10CertificationRequest createCertRequest(String subjectDN,
			KeyPair subjectKeyPair) {
		try {
			PKCS10CertificationRequest req = new PKCS10CertificationRequest(
					signAlgorithm, new X509Name(subjectDN),
					subjectKeyPair.getPublic(), null,
					subjectKeyPair.getPrivate());

			return req;
		} catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}
		return null;
	}

	/**
	 * 生成V1版本根证书，返回证书对象
	 * 
	 * @param issuerDN
	 * @param validityDays
	 * @param rootkeypair
	 * @return
	 */
	public X509Certificate signRootCertV1(String issuerDN, int validityYears,
			KeyPair rootkeypair,String signAlgorithm) {
		// // signers name
		// String issuer = "C=AU, O=The Legion of the Bouncy Castle, OU=Bouncy
		// ROOT
		// // subjects name - the same as we are self signed.
		// String subject = "C=AU, O=The Legion of the Bouncy Castle, OU=Bouncy
		// ROOT

		String subjectDN = issuerDN;
		// create the certificate - version 1
		X509V1CertificateGenerator v1CertGen = new X509V1CertificateGenerator();
		
		v1CertGen.setSerialNumber(this.createSerialNum());
		v1CertGen.setIssuerDN(new X509Principal(issuerDN));//签发者
		v1CertGen.setSubjectDN(new X509Principal(subjectDN));//申请者
		Date start = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24* 0);
		v1CertGen.setNotBefore(start);

		Date end = DateUtils.addYears(start, validityYears);
		v1CertGen.setNotAfter(end);
	
		v1CertGen.setPublicKey(rootkeypair.getPublic());
		v1CertGen.setSignatureAlgorithm(signAlgorithm);
		
		X509Certificate cert = null;
		try {
			cert = v1CertGen.generate(rootkeypair.getPrivate());// 根据私钥生成v1版本根证书
			logger.debug("生成V1根证书,签发者: "+issuerDN+",申请者: "+issuerDN+",签名算法："+signAlgorithm+",证书有限期: "+validityYears+"年");
			
			//将证书保存到证书文件.CER
			//saveCertToFile(path,cert);
//			将私钥保存到PEM文件
			//savePrivateKeyToFile(path, password, rootkeypair.getPrivate());
			
		} catch (CertificateEncodingException e) {
			writeLog(e,logger,this.getClass());
		} catch (InvalidKeyException e) {
			writeLog(e,logger,this.getClass());
		} catch (IllegalStateException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (SignatureException e) {
			writeLog(e,logger,this.getClass());
		}
		// cert.checkValidity(new Date());
		// cert.verify(pubKey);
		// PKCS12BagAttributeCarrier bagAttr = (PKCS12BagAttributeCarrier) cert;
		// //
		// // this is actually optional - but if you want to have control
		// // over setting the friendly name this is the way to do it...
		// //
		// bagAttr.setBagAttribute(PKCSObjectIdentifiers.pkcs_9_at_friendlyName,
		// new DERBMPString("Bouncy Primary Certificate"));

		return cert;

	}
	
	/**
	 * 生成V3版本根证书，返回证书对象
	 * 
	 * @param issuerDN
	 * @param validityDays
	 * @param rootkeypair
	 * @return
	 */
	public X509Certificate signRootCertV3(String issuerDN, int validityYears,
			KeyPair rootkeypair,String signAlgorithm) {
		String subjectDN = issuerDN;
		X509Certificate cert = null;
		try {
			X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
			// 设置证书序列号
			v3CertGen.setSerialNumber(this.createSerialNum());
			
			v3CertGen.setIssuerDN(new X509Principal(issuerDN));//签发者
			v3CertGen.setSubjectDN(new X509Principal(subjectDN));//申请者
			Date start = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24* 0);
			v3CertGen.setNotBefore(start);
			Date end = DateUtils.addYears(start, validityYears);
			// new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * validityDays))
			v3CertGen.setNotAfter(end);
			v3CertGen.setPublicKey(rootkeypair.getPublic());//设置公钥信息
			v3CertGen.setSignatureAlgorithm(signAlgorithm);//指定签名算法
	
			// 设置好一些扩展字段,包括签发者和 接收者的公钥标识
			//BasicConstraints标识是否是ca
			v3CertGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(true));
			
			v3CertGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.keyCertSign | KeyUsage.cRLSign));
			
			v3CertGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
					new SubjectKeyIdentifierStructure(rootkeypair.getPublic()));
			
			
//			List<String> domainNames = new ArrayList<String>();
//			domainNames.add("localhost");
//			domainNames.add("127.0.0.1");
//			
//			ASN1EncodableVector alternativeNames = new ASN1EncodableVector();
//			for( String domainName : domainNames )
//			{
//				alternativeNames.add( new GeneralName( GeneralName.dNSName, domainName ) );
//			}
//			v3CertGen.addExtension( X509Extensions.SubjectAlternativeName, false, new GeneralNames( new DERSequence( alternativeNames ) ) );
		
		
			cert = v3CertGen.generate(rootkeypair.getPrivate());
			logger.debug("生成V3根证书,签发者: "+issuerDN+",申请者: "+issuerDN+",签名算法："+signAlgorithm+",证书有限期: "+validityYears+"年");
			
			//将证书保存到证书文件.CER
			//saveCertToFile(path,cert);
			//将私钥保存到PEM文件
			//savePrivateKeyToFile(path, password, rootkeypair.getPrivate());
			
		} catch (CertificateEncodingException e) {
			writeLog(e,logger,this.getClass());
		} catch (InvalidKeyException e) {
			writeLog(e,logger,this.getClass());
		} catch (IllegalStateException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (SignatureException e) {
			writeLog(e,logger,this.getClass());
		} catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}

		return cert;

	}

	/**
	 * 根据证书请求文件，签发下级证书，返回证书对象
	 * 
	 * @param subjectRequestFilename
	 * @param issuerCert
	 * @param issuerKeyPair
	 * @param validityDays
	 * @return
	 */
	public X509Certificate signSubCert(String subjectRequestFilename,
			X509Certificate issuerCert, PrivateKey issuerPrivateKey,
			int validityDays) {
		File file = new File(subjectRequestFilename);
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fin.read(b);
			fin.close();

			return this.signSubCert(b, issuerCert, issuerPrivateKey,
					validityDays);
		} catch (Exception e) {
			logger.error("signCert error." + e.getMessage());
		}
		return null;

	}

	/**
	 * 根据证书请求的内容，签发下级证书，返回证书对象
	 * 
	 * @param subjectRequestFilename
	 * @param issuerCert
	 * @param issuerKeyPair
	 * @param validityDays
	 * @return
	 */
	public X509Certificate signSubCert(byte[] requestContent,
			X509Certificate issuerCert, PrivateKey issuerPrivateKey,
			int validityDays) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			PKCS10CertificationRequest csr = new PKCS10CertificationRequest(
					Base64.decode(requestContent));
			PublicKey subjectPublicKey = csr.getPublicKey();
			CertificationRequestInfo CSRInfo = csr
					.getCertificationRequestInfo();
			X509Name subjectDN = CSRInfo.getSubject();
			// X500Name subjectDN = CSRInfo.getSubject();
			// X500Principal x5p = new X500Principal(subjectDN.getEncoded());
			// X509Name subjectDN = new X509Name(CSRInfo.getSubject());
			// ASN1Set attributes = CSRInfo.getAttributes();

			// set generator's params: subCA's DN/serialNumber/date
			X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
			certGen.setSignatureAlgorithm(signAlgorithm);
			// subject's DN/publicKey
			certGen.setSubjectDN(subjectDN);// x5p
			// certGen.setSubjectDN(x5p);
			certGen.setPublicKey(subjectPublicKey);
			// subCA's info
			certGen.setIssuerDN(issuerCert.getSubjectX500Principal());
			certGen.setSerialNumber(this.createSerialNum());
			certGen.setNotBefore(issuerCert.getNotBefore());
			certGen.setNotAfter(issuerCert.getNotAfter());
			// extensions
			// certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
			// new SubjectKeyIdentifierStructure(subjectPublicKey));
			//
			// certGen.addExtension(
			// X509Extensions.AuthorityKeyIdentifier,
			// false,
			// new AuthorityKeyIdentifierStructure(issuerCert
			// .getPublicKey()));

			X509Certificate cert = certGen.generate(issuerPrivateKey, "BC");
			return cert;
		} catch (Exception e) {
			logger.error("signCert error." + e.getMessage());
		}
		return null;

	}

	/**
	 * 
	 * 根据请求者DN和请求者KEYPAIR，签发证书，返回证书对象
	 * 
	 * @param subjectDN
	 * @param subjectPeyPair
	 * @param issuerCert
	 * @param issuerKeyPair
	 * @param validityDays
	 * @return
	 */
	public X509Certificate signSubCert(String subjectDN,
			PublicKey subjectPublicKey, X509Certificate issuerCert,
			PrivateKey issuerPrivateKey, int validityDays,String signAlgorithm,
			Map<String,String> extensions) {
		try {
			X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
			// 设置证书序列号
			v3CertGen.setSerialNumber(this.createSerialNum());
			// // 这里有一点小技巧,我们要把JCE中定义的用来表示DN的对象X500Principal转化成在
			// // BC Provider中的相应的对象X509Name,先从CA的证书中读取出CA的DN进行DER编码
			// DERInputStream dnStream =new DERInputStream(new ByteArrayInputStream( caCert.getSubjectX500Principal().getEncoded()));
			// // 马上又从编码后的字节流中读取DER编码对象
			// DERConstructedSequence dnSequence =(DERConstructedSequence)dnStream.readObject();
			// // 利用读取出来的DER编码对象创建X509Name
			// // 对象,并设置为证书生成器中的"签发者DN"
			// certGen.setIssuerDN(new X509Name(dnSequence));

			// v3CertGen.setIssuerDN(getSubjectX509Principal(rootCACert));// or
			v3CertGen.setIssuerDN(issuerCert.getSubjectX500Principal());
			// 设置好证书生成器中的"接收方DN"
			v3CertGen.setSubjectDN(new X509Principal(subjectDN));
			Date start = new Date(System.currentTimeMillis() - 0);
			v3CertGen.setNotBefore(start);
			Date end = DateUtils.addDays(start, validityDays);
			// new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * validityDays))
			v3CertGen.setNotAfter(end);
			v3CertGen.setPublicKey(subjectPublicKey);//设置公钥信息
			v3CertGen.setSignatureAlgorithm(signAlgorithm);//指定签名算法

			// 设置好一些扩展字段,包括签发者和 接收者的公钥标识
			v3CertGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
					new SubjectKeyIdentifierStructure(subjectPublicKey));

			v3CertGen.addExtension(X509Extensions.AuthorityKeyIdentifier,
					false, new AuthorityKeyIdentifierStructure(issuerCert));
			
			
			if(extensions != null && extensions.size()>0)
			{
				String alternateName = "";
				for(Map.Entry<String, String> entrys : extensions.entrySet())
				{
					alternateName+=entrys.getKey()+"="+entrys.getValue()+",";
				}
				
				if(alternateName.endsWith(","))
				{
					alternateName.substring(0, alternateName.length()-1);
				}
				GeneralName name=new GeneralName(GeneralName.directoryName,alternateName);
				v3CertGen.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(name));
			}
			
			X509Certificate subCA_Cert = v3CertGen.generate(issuerPrivateKey);
			// subCA_Cert.checkValidity(new Date());
			// subCA_Cert.verify(issuerKeyPair.getPublic());
			logger.debug("签发子证书,签发者: "+issuerCert.getIssuerX500Principal().getName()+",申请者: "+subjectDN+",签名算法："+signAlgorithm+",证书有限期: "+validityDays+"天");
			return subCA_Cert;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("signCert error." + e.getMessage());
		}
		return null;
	}

	/**
	 * 根据签章请求内容(BASE64)，返回X509格式的证书
	 * 
	 * @param esealRequest
	 *            证书请求字符串
	 * @param issuerCert
	 *            签发证书
	 * @param issuerKeyPair
	 *            签发私钥
	 * @return
	 */

	public X509Certificate signESeal(String esealRequest,
			X509Certificate issuerCert, PrivateKey issuerPrivateKey) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] derCode = Base64.decode(esealRequest.getBytes());
		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(derCode);
		try {
			logger.info("ESeal request info:==============");
			PublicKey subjectPublicKey = csr.getPublicKey();
			System.out.println("Requester publickey: " + subjectPublicKey);

			CertificationRequestInfo CSRInfo = csr
					.getCertificationRequestInfo();
			X509Name subjectDN = CSRInfo.getSubject();
			// X500Name subjectDN = CSRInfo.getSubject();
			// X500Principal x5p = new X500Principal(subjectDN.getEncoded());
			System.out.println("Requester subjectDN: " + subjectDN);

			X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
			certGen.setSignatureAlgorithm(signAlgorithm);
			// subject's DN/publicKey
			certGen.setSubjectDN(subjectDN);
			// certGen.setSubjectDN(x5p);
			certGen.setPublicKey(subjectPublicKey);
			// subCA's info
			// certGen.setIssuerDN(this.getSubjectX509Principal(issuerCert));
			certGen.setIssuerDN(issuerCert.getSubjectX500Principal());
			certGen.setSerialNumber(this.createSerialNum());

			// NotBefore/NotAfter in extensions
			// certGen.setNotBefore(issuerCert.getNotBefore());
			// certGen.setNotAfter(issuerCert.getNotAfter());

			// extensions
			certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
					new SubjectKeyIdentifierStructure(subjectPublicKey));

			certGen.addExtension(
					X509Extensions.AuthorityKeyIdentifier,
					false,
					new AuthorityKeyIdentifierStructure(issuerCert
							.getPublicKey()));

			ASN1Set attributes = CSRInfo.getAttributes();
			for (int i = 0; i != attributes.size(); i++) {
				Attribute attr = Attribute.getInstance(attributes
						.getObjectAt(i));
				if (attr.getAttrType().equals(
						PKCSObjectIdentifiers.pkcs_9_at_extensionRequest)) {
					X509Extensions extensions = X509Extensions.getInstance(attr
							.getAttrValues().getObjectAt(0));

					Enumeration e = extensions.oids();
					while (e.hasMoreElements()) {
						DERObjectIdentifier oid = (DERObjectIdentifier) e
								.nextElement();
						X509Extension ext = extensions.getExtension(oid);
						DateFormat formatter = new SimpleDateFormat(
								"dd MM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
						formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
						if (oid.getId().equals(
								ESealUtil.ExtesealNotBeforeOID.getId())) {
							Date notBefore = formatter.parse((new String(ext
									.getValue().getOctets())));
							certGen.setNotBefore(notBefore);
						} else if (oid.getId().equals(
								ESealUtil.ExtesealNotAfterOID.getId())) {
							Date notAfter = formatter.parse((new String(ext
									.getValue().getOctets())));
							certGen.setNotAfter(notAfter);
						} else {
							certGen.addExtension(oid, ext.isCritical(), ext
									.getValue().getOctets());
							// certGen.addExtension(oid, ext.isCritical(),
							// ext.getValue());
						}
					}
				}
			}

			// If attribute=DERSet(extensions) that is new
			// PKCS10CertificationRequest(signAlgorithm, new
			// X509Name(subjectDN), esealKeyPair.getPublic(), new
			// DERSet(extensions), esealKeyPair.getPrivate());
			// if (attributes != null) {
			// Enumeration enums = attributes.getObjects();
			// addCustomExtensions(enums, certGen);
			// }

			X509Certificate cert = certGen.generate(issuerPrivateKey, "BC");
			System.out.println("ESealCert info:=============");
			System.out.println(cert.toString());
			return cert;
		} catch (InvalidKeyException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchProviderException e) {
			writeLog(e,logger,this.getClass());
		} catch (CertificateEncodingException e) {
			writeLog(e,logger,this.getClass());
		} catch (IllegalStateException e) {
			writeLog(e,logger,this.getClass());
		} catch (SignatureException e) {
			writeLog(e,logger,this.getClass());
		} catch (ParseException e) {
			writeLog(e,logger,this.getClass());
		} catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}

		return null;
	}

	/**
	 * 将证书和私钥保存到KEYSTORE
	 * 
	 * @param fullFileName
	 *            保存的文件名
	 * @param certChain
	 *            证书链
	 * @param keyPair
	 *            密钥对
	 * @param keyentry_alias
	 *            保存的key别名
	 * @param storepass
	 *            密钥库密码
	 * @return
	 */
	public String saveCertChainToKeyStore(String fullFileName,
			X509Certificate[] certChain, KeyPair keyPair,
			String keyentry_alias, String storepass,String keyStoreType) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		FileOutputStream fOut = null;
		try {
			//KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			KeyStore keyStore = null;
			if(keyStoreType.equals(CertCommonUtils.STORE_TYPE_JKS))
				keyStore = KeyStore.getInstance(CertCommonUtils.STORE_TYPE_JKS);
			else
				keyStore = KeyStore.getInstance(CertCommonUtils.STORE_TYPE_PKCS12, "BC");
			keyStore.load(null, null);
			
//			keyStore.setCertificateEntry("root", certChain[0]); 
			keyStore.setKeyEntry(keyentry_alias, keyPair.getPrivate(),storepass.toCharArray(), certChain);

			fOut = new FileOutputStream(fullFileName);
			keyStore.store(fOut, storepass.toCharArray());
			fOut.flush();
			
			logger.debug("生成密钥库文件： "+fullFileName+",别名："+keyentry_alias);
			return fullFileName;
		} catch (Exception e) {
			logger.error("Save cert error." + e.getMessage());
		}finally{
			if(fOut != null)
			{
				try {
					fOut.close();
				} catch (IOException e) {
					logger.error("Save cert error." + e.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 将证书和私钥保存到KEYSTORE
	 * 
	 * @param fullFileName
	 * @param certChain
	 * @param keyPair
	 * @param keyentry_alias
	 * @param storepass
	 * @return
	 */
	public String saveCertChainToExistKeyStore(String fullFileName,
			X509Certificate[] certChain, KeyPair keyPair,
			String keyentry_alias, String storepass) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			InputStream inStream = new FileInputStream(fullFileName);
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");

			keyStore.load(inStream, storepass.toCharArray());
			keyStore.setKeyEntry(keyentry_alias, keyPair.getPrivate(),
					storepass.toCharArray(), certChain);
			inStream.close();
			FileOutputStream fOut = new FileOutputStream(fullFileName);
			keyStore.store(fOut, storepass.toCharArray());
			fOut.close();
			return fullFileName;
		} catch (Exception e) {
			logger.error("Save cert error." + e.getMessage());
		}
		return null;
	}

	/**
	 * 将私钥保存到PEM文件
	 * 
	 */
	public  void savePrivateKeyToFile(String fullFileName, String password,PrivateKey key)  { 
			  PEMWriter writer = null;
			  try {
//			    writer = new PEMWriter(new PrintWriter(System.out)); 
				writer = new PEMWriter(new FileWriter(fullFileName)); 
				  writer.writeObject(key, "DESEDE", password.toCharArray(), new SecureRandom());
				} catch (Exception e) {
					writeLog(e,logger,this.getClass());
				} finally{
					if(writer != null)
						try {
							writer.close();
						} catch (IOException e) {
							writeLog(e,logger,this.getClass());
						} 
				}
			
	} 
	
	/**
	 * 将证书保存到证书文件.CER
	 * 
	 * @param fullFileName
	 * @param cert
	 */
	public void saveCertToFile(String fullFileName, X509Certificate cert) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(fullFileName));
			fout.write(cert.getEncoded());
			fout.flush();
		} catch (Exception e) {
			writeLog(e,logger,this.getClass());
			throw new RuntimeException(e.getMessage());
		}finally{
			if(null != fout)
			{
				try {
					fout.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
	}

	/**
	 * 仅将证书保存到KEYSTORE
	 * 
	 * @param fullFileName
	 *            保存的文件路径
	 * @param cert
	 *            证书
	 * @param cert_alias
	 *            证书别名
	 * @param storepass
	 *            密钥库密码
	 * @return
	 */
	public String saveCertToKeyStore(String fullFileName, X509Certificate cert,
			String cert_alias, String storepass) {
		FileOutputStream fout = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(null, null);
			keyStore.setCertificateEntry(cert_alias, cert);
			// keyStore.setKeyEntry(cert_alias, null, new
			// X509Certificate[]{cert} );
			fout = new FileOutputStream(fullFileName);
			keyStore.store(fout, storepass.toCharArray());
			fout.flush();
			return fullFileName;
		} catch (Exception e) {
			logger.error("Save cert error." + e.getMessage());
		}finally{
			if(null != fout)
			{
				try {
					fout.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
		return null;
	}

	/**
	 * 从KEYSTORE中获取私钥
	 * 
	 * @param fullFileName
	 * @param alias
	 * @param storepass
	 * @return
	 */
	public PrivateKey getPrivateFromKeyStore(String fullFileName, String alias,
			String storepass, String storetype) {
		String defaultType = "PKCS12";
		KeyStore keyStore = null;
		PrivateKey priKey = null;
		// 添加BouncyCastle作为安全提供
		try {
			if (null != storetype && !"".equals(storetype)) {
				defaultType = storetype;
			}
			
			if (defaultType.equals("JKS")) {
				keyStore = KeyStore.getInstance(defaultType);

			} else if (defaultType.equals("PKCS12")) {
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				keyStore = KeyStore.getInstance(defaultType, "BC");
			}

			InputStream inStream = new FileInputStream(fullFileName);
			keyStore.load(inStream, storepass.toCharArray());
			priKey = (PrivateKey) keyStore.getKey(alias,storepass.toCharArray());
			inStream.close();

		} catch (KeyStoreException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchProviderException e) {
			writeLog(e,logger,this.getClass());
		} catch (FileNotFoundException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (CertificateException e) {
			writeLog(e,logger,this.getClass());
		} catch (IOException e) {
			writeLog(e,logger,this.getClass());
		} catch (UnrecoverableKeyException e) {
			writeLog(e,logger,this.getClass());
		}
		return priKey;
	}

	/**
	 * 从证书文件获取公钥
	 * 
	 * @param fullFileName
	 * @return
	 */
	public PublicKey getPublicKeyFromCertFile(String fullFileName) {
		X509Certificate cert = this.getCertFromCertFile(fullFileName);
		return cert.getPublicKey();
	}

	/**
	 * 从证书文件获取证书对象
	 * 
	 * @param fullFileName
	 * @return
	 */
	public X509Certificate getCertFromCertFile(String fullFileName) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(fullFileName);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
			
			return cert;
		} catch (CertificateException e) {
			writeLog(e,logger,this.getClass());
		} catch (FileNotFoundException e) {
			writeLog(e,logger,this.getClass());
		} catch (IOException e) {
			writeLog(e,logger,this.getClass());
		}catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}finally{
			if(in != null)
			{
				try {
					in.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
		return null;
	}

	/**
	 * 从KEYSTORE中,根据证书别名获取证书
	 * 
	 * @param fullFileName
	 * @param cert_alias
	 * @param storepass
	 * @return
	 */
	public X509Certificate getCertFromKeyStoreFile(String fullFileName,
			String cert_alias, String storepass, String storeType) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyStore keyStore = null;
		InputStream inStream = null;
		try {
			if ("PFX".equals(storeType.toUpperCase())) {
				keyStore = KeyStore.getInstance("PKCS12", "BC");
			} else if ("JKS".equals(storeType.toUpperCase())) {
				keyStore = KeyStore.getInstance("JKS");
			}
		    inStream = new FileInputStream(fullFileName);
			keyStore.load(inStream, storepass.toCharArray());
//			Enumeration<String> aliases = keyStore.aliases();
//			if(aliases != null)
//			{
//				while(aliases.hasMoreElements())
//				{
//					System.out.println(aliases.nextElement());
//				}
//			}
			
			return (X509Certificate) keyStore.getCertificate(cert_alias);
		} catch (KeyStoreException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchProviderException e) {
			writeLog(e,logger,this.getClass());
		} catch (FileNotFoundException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (CertificateException e) {
			writeLog(e,logger,this.getClass());
		} catch (IOException e) {
			writeLog(e,logger,this.getClass());
		}catch (Throwable e) {
			writeLog(e,logger,this.getClass());
		}finally{
			if(inStream != null)
			{
				try {
					inStream.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
		return null;
	}

	/**
	 * 从KEYSTORE中,根据私钥别名获取证书链
	 * 
	 * @param fullFileName
	 * @param key_alias
	 * @param storepass
	 * @return
	 */
	public Certificate[] getCertChainFromKeyStoreFile(String fullFileName,
			String key_alias, String storepass) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		InputStream inStream = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			inStream = new FileInputStream(fullFileName);
			keyStore.load(inStream, storepass.toCharArray());
			Certificate[] certs = (Certificate[]) keyStore
					.getCertificateChain(key_alias);
			return certs;
		} catch (KeyStoreException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchProviderException e) {
			writeLog(e,logger,this.getClass());
		} catch (FileNotFoundException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (CertificateException e) {
			writeLog(e,logger,this.getClass());
		} catch (IOException e) {
			writeLog(e,logger,this.getClass());
		}catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}finally{
			if(inStream != null)
			{
				try {
					inStream.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
		return null;
	}

	/**
	 * 从base64编码的字符串解码中出证书
	 * 
	 * @param base64Cert
	 * @return
	 * @throws Exception
	 */
	public static X509Certificate getCertFromBase64String(String base64Cert)
			throws Exception {
		X509Certificate cert = null;
		ByteArrayInputStream bin = null;
		try {
			bin = new ByteArrayInputStream(Base64.decode(base64Cert));
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			if (bin.available() > 0) {
				cert = (X509Certificate) cf.generateCertificate(bin);
			}
		} catch (CertificateException e) {
			throw e;
		} finally {
			if (bin != null) {
				try {
					bin.close();
					bin = null;
				} catch (IOException e) {
					// do nothing
				}

			}
		}
		return cert;
	}

	/**
	 * 从KEYSTORE中同时获取私钥和证书
	 * 
	 * @param fullFileName
	 * @param alias
	 * @param storepass
	 * @param privateKey
	 * @param cert
	 */
	public void getPrivateKeyAndCertFromKeyStoreFile(String fullFileName,
			String alias, String storepass, PrivateKey privateKey,
			X509Certificate cert) {
		// 添加BouncyCastle作为安全提供
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(fullFileName);
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(inStream, storepass.toCharArray());
			privateKey = (PrivateKey) keyStore.getKey(alias,
					storepass.toCharArray());
			Certificate[] certs = keyStore.getCertificateChain(alias);
			cert = (X509Certificate) certs[0];
		} catch (KeyStoreException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchProviderException e) {
			writeLog(e,logger,this.getClass());
		} catch (FileNotFoundException e) {
			writeLog(e,logger,this.getClass());
		} catch (NoSuchAlgorithmException e) {
			writeLog(e,logger,this.getClass());
		} catch (CertificateException e) {
			writeLog(e,logger,this.getClass());
		} catch (IOException e) {
			writeLog(e,logger,this.getClass());
		} catch (UnrecoverableKeyException e) {
			writeLog(e,logger,this.getClass());
		}catch (Exception e) {
			writeLog(e,logger,this.getClass());
		}finally{
			if(inStream != null)
			{
				try {
					inStream.close();
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
			}
		}
	}

	public X509Certificate[] parseB64Cert(String b64Cert) {
		return parseDerCert(Base64.decode(b64Cert.getBytes()));
	}

	public X509Certificate[] parseDerCert(byte[] derCert) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Collection col = cf.generateCertificates(new ByteArrayInputStream(
					derCert));
			int clen = col.size();
			X509Certificate[] x509s = new X509Certificate[clen];
			col.toArray(x509s);
			return x509s;
		} catch (Exception exp) {
			throw new IllegalArgumentException("parse der cert  - " + exp);
		}
	}

	/**
	 * @param subjectName
	 */
	public static String parseEsealName(String subjectName) {
		String[] subject = subjectName.split(",");
		int size = subject.length;
		if (!"".equals(subjectName)) {
			for (int i = 0; i < size; i++) {
				if (subject[i].indexOf("CN=") >= 0) {
					return subject[i].replaceAll("CN=", "");
				}
			}
		}
		return null;

	}

	public static String parseOrg(String subjectName) {
		String[] subject = subjectName.split(",");
		int size = subject.length;
		if (!"".equals(subjectName)) {
			for (int i = 0; i < size; i++) {
				if (subject[i].indexOf("O=") >= 0) {
					return subject[i].replaceAll("O=", "");
				}
			}
		}
		return null;

	}

	public static String parseST(String subjectName) {
		String[] subject = subjectName.split(",");
		int size = subject.length;
		if (!"".equals(subjectName)) {
			for (int i = 0; i < size; i++) {
				if (subject[i].indexOf("ST=") >= 0) {
					return subject[i].replaceAll("ST=", "");
				}
			}
		}
		return null;

	}

	public static String parseLocal(String subjectName) {
		String[] subject = subjectName.split(",");
		int size = subject.length;
		if (!"".equals(subjectName)) {
			for (int i = 0; i < size; i++) {
				if (subject[i].indexOf("L=") >= 0) {
					return subject[i].replaceAll("L=", "");
				}
			}
		}
		return null;

	}

	 private void addCustomExtensions(Enumeration enums, X509V3CertificateGenerator certGen)
	 {
		 //DERSet extensionSet = null;
		 DERObjectIdentifier oid = null;
		 DEROctetString value = null;
		 while (enums.hasMoreElements()) {
			 Object obj = enums.nextElement();
			 if(obj instanceof DERObjectIdentifier){
				 logger.debug("DERObjectIdentifier obj: " + obj);
				 oid = (DERObjectIdentifier) obj;
			 }
			 else if(obj instanceof DEROctetString){
				 value = (DEROctetString)obj;
				 logger.debug("DEROctetString value: " + new String(value.getOctets()));
				 if(oid!=null && value!=null){
					 certGen.addExtension(oid, true,new DEROctetString(value.getOctets()));
					 oid = null;
					 value = null;
				 }
			}else {
				 Enumeration enum2= ((DERSequence)obj).getObjects();
				 this.addCustomExtensions(enum2, certGen);
			}
	 // }else if
	 //
	 // while(enums2.hasMoreElements()){
	 // obj = enums2.nextElement();
	 // if(obj instanceof DERSequence){
	 // sequence = (DERSequence)obj;
	 // Enumeration enums3 = sequence.getObjects();
	 // DERObjectIdentifier oid = null;
	 // DEROctetString value = null;
	 // while(enums3.hasMoreElements()){
	 // Object derobj = enums3.nextElement();
	 // if(derobj instanceof DERObjectIdentifier){
	 // System.out.println("DERObjectIdentifier obj: " + derobj);
	 // oid = (DERObjectIdentifier) derobj;
	 // }else if(derobj instanceof DEROctetString){
	 // value = (DEROctetString)derobj;
	 // System.out.println("DEROctetString value: " + new String(value.getOctets()));
	 // if(oid!=null && value!=null){
	 // certGen.addExtension(oid, true,new DEROctetString(value.getOctets()));
	 // oid = null;
	 // value = null;
	 // }
	 // }else{
	 // System.out.println("Unknown obj: " + derobj);
	 // }
	 // }
	 // }
	 // }
	 // }
	 	}
	 }

	 public X509Principal getSubjectX509Principal(X509Certificate cert) 
	 {
		 X509Principal princ = null;
		 try {
			 ByteArrayInputStream bIn = new ByteArrayInputStream(cert.getTBSCertificate());
			 ASN1InputStream aIn = new ASN1InputStream(bIn);
			 TBSCertificateStructure tbsCert = new TBSCertificateStructure((ASN1Sequence) aIn.readObject());
			
			 princ = new X509Principal(tbsCert.getSubject());
			
		 } catch (Exception e) {
			writeLog(e,logger,this.getClass());
		 }
		 return princ;
	 }

	 public BigInteger createSerialNum() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		logger.info("createSerialNum: "+format.format(Calendar.getInstance().getTime()));
		String newtime = format.format(Calendar.getInstance().getTime());
/*		int random = (int) ((Math.random() * 100) + 1);
		if (random%10==0) {
			random += 1;
		}
		String newrandom = String.valueOf(random);*/
		
		return BigInteger.valueOf(Long.valueOf(newtime).longValue());
	}
	 /**
	  * 设置CRL列表拓展属性
	  * @param caKeyStore
	  * @param caCrlCert
	  * @param subCert
	  * @param crlGen
	  * @param alias
	  * @param password
	  * @param newCrlPath
	  * @param nextUpdate
	  * @return
	  * @throws Exception
	  */
	 private X509CRL setCrlExtension(KeyStore caKeyStore,X509Certificate caCrlCert,X509Certificate subCert,X509V2CRLGenerator crlGen,String alias,String password,String newCrlPath,Date nextUpdate ) throws Exception
	 {

		 FileOutputStream fos = null;
		 Date now = new Date();
		 X509CRL crl = null;
		 try{
			crlGen.setSignatureAlgorithm(caCrlCert.getSigAlgName());//SHA512withRSA
			crlGen.setIssuerDN(caCrlCert.getIssuerX500Principal());
			crlGen.setThisUpdate(now);
			crlGen.setNextUpdate(nextUpdate);//下次更新时间
			
			crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,new AuthorityKeyIdentifierStructure(caCrlCert));
			crlGen.addExtension(X509Extensions.CRLNumber,false,new CRLNumber(caCrlCert.getSerialNumber()));

			crlGen.addCRLEntry(subCert.getSerialNumber(), now,CRLReason.privilegeWithdrawn);
		
			PrivateKey caCrlPrivateKey = (PrivateKey)caKeyStore.getKey(alias, password.toCharArray());
			crl = crlGen.generateX509CRL(caCrlPrivateKey, "BC");
			
			fos = new FileOutputStream(newCrlPath);
			fos.write(crl.getEncoded());
			
			fos.flush();
			
			logger.debug("create crl file,path: "+newCrlPath);
		 }
		 finally{
			 if(fos != null)
			 {
				 fos.close();
			 }
		 }
		 return crl;
		
	 }
	 
	 /**
	  *  创建CRL列表
	  *  吊销秘钥库中的子证书
	  * @param caKeyStore
	  * @param alias
	  * @param password
	  * @param subCert
	  * @param nextUpdDate
	  * @param crlPath
	  * @return
	  */
	 public X509CRL createCertCrl(KeyStore caKeyStore,String alias,String password,X509Certificate subCert,int nextUpdDate,String oldCrlPath,String newCrlPath)
	 {
		 	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		 	if(caKeyStore == null)
		 	{
		 		logger.error("KeyStore is null!");
		 		return null;
		 	}
		 	if(alias == null || alias.trim().equals(""))
		 	{
		 		logger.error("alias is null!");
		 		return null;
		 	}
		 	if(password == null || password.trim().equals(""))
		 	{
		 		logger.error("password is null!");
		 		return null;
		 	}
		 	if(subCert == null)
		 	{
		 		logger.error("Certificate is null!");
		 		return null;
		 	}
		 	X509V2CRLGenerator crlGen = new X509V2CRLGenerator();
		 	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date now = new Date();
			Date nextUpdate = DateUtils.addDays(now, nextUpdDate);
			
			FileOutputStream fos = null;
			FileInputStream fis = null;
			X509CRL crl = null;
			try {
				X509Certificate caCrlCert = (X509Certificate)caKeyStore.getCertificate(alias);
				
				if(oldCrlPath == null)
				{
					crl = setCrlExtension( caKeyStore, caCrlCert, subCert, crlGen, alias, password, newCrlPath, nextUpdate);
				}
				else
				{

					File file = new File(oldCrlPath);
					
					//crl文件已存在
					if(file.exists()){
						 fis = new FileInputStream(file);
						
						 // 指定证书类型     
						 CertificateFactory cf = CertificateFactory.getInstance("X.509");     
						       
						  // 根据CRL文件流，获得X509CRL实例     
						 crl = (X509CRL) cf.generateCRL(fis);   
						 
						 if(crl != null){
							crlGen.setSignatureAlgorithm(caCrlCert.getSigAlgName());//SHA512withRSA
							crlGen.setIssuerDN(caCrlCert.getIssuerX500Principal());
							crlGen.setThisUpdate(now);
							crlGen.setNextUpdate(nextUpdate);//下次更新时间
							
							crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,new AuthorityKeyIdentifierStructure(caCrlCert));
							crlGen.addExtension(X509Extensions.CRLNumber,false,new CRLNumber(caCrlCert.getSerialNumber()));
							crlGen.addCRL(crl);
							
							//将过往吊销证书写进去
//							for(X509CRLEntry entry :  crl.getRevokedCertificates()){
//								crlGen.addCRLEntry(entry.getSerialNumber(), now,CRLReason.privilegeWithdrawn);
//							}
							
							 crlGen.setThisUpdate(now);
							 //新吊销的证书
							 crlGen.addCRLEntry(subCert.getSerialNumber(), now,CRLReason.privilegeWithdrawn);
							  
							 PrivateKey caCrlPrivateKey = (PrivateKey)caKeyStore.getKey(alias, password.toCharArray());
							 crl = crlGen.generateX509CRL(caCrlPrivateKey, "BC");
							 fos = new FileOutputStream(newCrlPath);
							 fos.write(crl.getEncoded());
							
							 fos.flush();
								
							 logger.debug("load and create crl file,path: "+newCrlPath);
						 }
						 
					}
					else
					{
						
						crl = setCrlExtension( caKeyStore, caCrlCert, subCert, crlGen, alias, password, newCrlPath, nextUpdate );
					}
				}
				
				
				
			} catch (Throwable e) {
				writeLog(e,logger,this.getClass());
			} finally{
				try {
					if(fos != null)
					{
						fos.close();
					}
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
				
				try {
					if(fis != null)
					{
						fis.close();
					}
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
				
			}
			
			return crl;
	 }
	 
	
	 /**
	  * 创建CRL列表
	  * 吊销根证书下面的子证书
	  * @param caCrlCert 根证书
	  * @param caCrlPrivateKey 根私钥
	  * @param subCert 吊销证书
	  * @param nextUpdDate 间隔多久后更新
	  * @param crlPath crl生成位置
	  * @return
	  */
	 public String createCertCrl(X509Certificate caCrlCert,PrivateKey caCrlPrivateKey,X509Certificate subCert,int nextUpdDate,String crlPath)
	 {
//		 	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		 	if(caCrlCert == null)
		 	{
		 		logger.error("Ca Certificate is null!");
		 		return "error";
		 	}
			//暂时可以取到私钥
		 	if(caCrlPrivateKey == null )
		 	{
		 		logger.error("Ca PrivateKey is null!");
		 		return "error";
		 	}
		 	if(subCert == null)
		 	{
		 		logger.error("Certificate is null!");
		 		return "error";
		 	}
		 	
		 	logger.info("crlPath "+crlPath);
		 	X509CRL oldCrl = null;
			try {
				oldCrl = loadX509CRL(crlPath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
//		 	String oldCrlFile = CertCommonUtils.getOldCrlFile(crlPath);
//		 	logger.info("oldCrlFile: "+oldCrlFile);
//		 	if (null!=oldCrlFile && !oldCrlFile.equals("")) {//如果存在旧的crl文件，直接添加到旧的文件中
//		 		File file = new File(crlPath);
//				try {
//					oldCrl = loadX509CRL(file);
//					newCrlPath = crlPath;
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		 	
		 	org.bouncycastle.x509.X509V2CRLGenerator crlGen = new org.bouncycastle.x509.X509V2CRLGenerator();
		 	if (null!=oldCrl) {
				try {
					crlGen.addCRL(oldCrl);
				} catch (CRLException e) {
					e.printStackTrace();
				}
			}
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date nextUpdate = DateUtils.addDays(now, nextUpdDate);

			crlGen.setIssuerDN(caCrlCert.getIssuerX500Principal());
			crlGen.setThisUpdate(now);
			crlGen.setNextUpdate(nextUpdate);//下次更新时间
			crlGen.setSignatureAlgorithm("SHA512withRSA");
			crlGen.setSignatureAlgorithm(caCrlCert.getSigAlgName());
			
//			if (null==newCrlPath || newCrlPath.equals("")) {
//				newCrlPath = CertCommonUtils.CRL_ONLINECERTSPATH;
//			}
			FileOutputStream fos = null;
			X509CRL newCrl = null;
			try {//
				//扩张特性 
				crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
						new AuthorityKeyIdentifierStructure(caCrlCert));
				//设置根证书序列号
				crlGen.addExtension(X509Extensions.CRLNumber,false,new org.bouncycastle.asn1.x509.CRLNumber(caCrlCert.getSerialNumber()));
				
				//将子证书序列号加入到吊销列表  
				crlGen.addCRLEntry(subCert.getSerialNumber(), now,CRLReason.privilegeWithdrawn);
				
				//用根证书的私钥进行处理
				newCrl = crlGen.generateX509CRL(caCrlPrivateKey, "BC");
				
				File file = new File(crlPath);
				if (!file.exists()) {
					file.createNewFile();
				}
				fos = new FileOutputStream(crlPath);
				fos.write(newCrl.getEncoded());
				
				fos.flush();
				
			} catch (Throwable e) {
				writeLog(e,logger,this.getClass());
			} finally{
				try {
					if(fos != null)
					{
						fos.close();
					}
				} catch (IOException e) {
					writeLog(e,logger,this.getClass());
				}
				
			}
			return "success";
	 }

	 /**
	  * 加载CRL证书吊销列表文件
	  * 
	  * @param crlFilePath
	  * @return
	  * @throws Exception
	  */
	public static X509CRL loadX509CRL(String crlFilePath) throws Exception {
		File crlFile = new File(crlFilePath);
		if (crlFile.exists()) {
			FileInputStream in = new FileInputStream(crlFilePath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509CRL crl = (X509CRL) cf.generateCRL(in);
			in.close();
			return crl;
		}
		return null;
	}
	
	
	public static void main(String[] args) throws Exception {
		// String certsPath ="D:\\workspace10\\CertWebTest\\WebRoot\\web\\newcerts\\";
		System.out.println(System.getProperty("user.dir"));
		CertUtil certUtil = new CertUtil();

		// 测试tomcat ssl
		String certsPath = CertCommonUtils.ROOTCA_PATH;
		String newcrlPath = "E:/360Downloads/证书测试文档/ttttttttttttt.crl";
		
//		X509CRL crl = loadX509CRL(newcrlPath);
		// // 取私钥匙对象
		 PrivateKey privateKey =certUtil.getPrivateFromKeyStore(certsPath+"ROOTCA20150127093033.keystore",
		 "1", "123456", "JKS");
		 
//		 PrivateKey privateKey =certUtil.getPrivateFromKeyStore(certsPath+"\\ca.p12", "1", "123", "JKA");
				 
		 System.out.println(privateKey);
		 
		 
//		 if(null == privateKey) 
//			 throw new Exception("私钥获取失败");
//		 else{
//		 X509Certificate x9 = certUtil.getCertFromKeyStoreFile(certsPath+"ssl\\C03CA1.keystore", "1", "123456", "JKS");
//		
//		  //生成密钥
//		 KeyPair subkeypair = KeyUtil.generateKeyPair(1024);
//		 String subjectDN = "C=CN,ST=GD,L=GZ,O=CONGOAL,OU=CONGOAL,CN=GNETE1";
//		 X509Certificate subCert = certUtil.signSubCert(subjectDN,
//		 subkeypair.getPublic(), x9,privateKey, 365);
//		 //生成密钥库
//		 certUtil.saveCertChainToKeyStore(certsPath+"Client2FromCA.pfx",
//		 new X509Certificate[] { subCert, x9 }, subkeypair,
//		 "client2", "123456");
//		 }

		// 生成crl证书吊销列表文件
		org.bouncycastle.x509.X509V2CRLGenerator crlGen = new org.bouncycastle.x509.X509V2CRLGenerator();
//		if (crl!=null) {
//			crlGen.addCRL(crl);
//		}
		Date now = new Date();
		Date nextUpdate = DateUtils.addDays(now, 3);
		X509Certificate caCrlCert = certUtil.getCertFromKeyStoreFile(certsPath+ "ROOTCA20150127093033.keystore", "1", "123456", "JKS");
		PrivateKey caCrlPrivateKey = certUtil.getPrivateFromKeyStore(certsPath+ "ROOTCA20150127093033.keystore", "1", "123456", "JKS");

//		X509Certificate subCert = certUtil.getCertFromKeyStoreFile(certsPath+ "ssl\\Client2FromCA.pfx", "client2", "123456", "PKCS12");
		String fullFileName = CertCommonUtils.SUBCA_ONLINECERTSPATH+"CN20150127104401_4401143100000860.cer";
		String fullFileName1 = CertCommonUtils.SUBCA_ONLINECERTSPATH+"CN20150127104401_4401143601073169.cer";
		X509Certificate subCert = certUtil.getCertFromCertFile(fullFileName);
		X509Certificate subCert1 = certUtil.getCertFromCertFile(fullFileName1);
		System.out.println("SubCert SerialNumber: "+ subCert.getSerialNumber().intValue());
		System.out.println("Subcert SeriaNumber1: "+ subCert.getSerialNumber().intValue());
		crlGen.setIssuerDN(caCrlCert.getIssuerX500Principal());
		crlGen.setThisUpdate(now);
		crlGen.setNextUpdate(nextUpdate);//下次更新时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(nextUpdate));

		crlGen.setSignatureAlgorithm("SHA512withRSA");

		crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(caCrlCert));
		crlGen.addExtension(
				X509Extensions.CRLNumber,
				false,new org.bouncycastle.asn1.x509.CRLNumber(caCrlCert.getSerialNumber()));
		crlGen.addCRLEntry(subCert.getSerialNumber(), now,CRLReason.CERTIFICATE_HOLD);
		crlGen.addCRLEntry(subCert1.getSerialNumber(), now, CRLReason.CESSATION_OF_OPERATION);
//		Set<X509CRLEntry> revokedCertificates = (Set<X509CRLEntry>) crl.getRevokedCertificates();
//		for (Iterator iterator = revokedCertificates.iterator(); iterator
//				.hasNext();) {
//			X509CRLEntry entry = (X509CRLEntry) iterator.next();
//			System.out.println("orgi: "+entry.getSerialNumber());
//			crlGen.addCRLEntry(entry.getSerialNumber(), now, CRLReason.privilegeWithdrawn);
//			
//		}
		X509CRL oldcrl = crlGen.generateX509CRL(caCrlPrivateKey, "BC");
		
		FileOutputStream fos = new FileOutputStream(newcrlPath);
		fos.write(oldcrl.getEncoded());
		fos.close();
//
//		// 获取所有的吊销证书
//		Set<X509CRLEntry> crlSet = (Set<X509CRLEntry>) crl
//				.getRevokedCertificates();
//
//		if (crlSet != null && crlSet.size() > 0) {
//			for (Object o : crlSet) {
//				X509CRLEntry crlEntry = (X509CRLEntry) o;
//				int serialNumber = crlEntry.getSerialNumber().intValue();
//				Date revocationDate = crlEntry.getRevocationDate();
//				System.err.println("SerialNumber: " + serialNumber
//						+ ",revocation: " + sdf.format(revocationDate));
//			}
//		} else {
//			System.err.println("crl is empty");
//		}

		// 颁发 root CA
		// KeyPair rootkeypair = KeyUtil.generateKeyPair(2048);// 生成密钥对
		// String issuerDN =
		// "C=cn,ST=bj,L=beijing,O=o1,OU=ou1,CN=I am a rootCA";
		// X509Certificate rootCert = certUtil.signRootCert(issuerDN, 365,
		// rootkeypair);// 签发根证书
		//
		// String ksSavePathName = certUtil.saveCertChainToKeyStore(certsPath +
		// "RootCA.pfx",
		// new X509Certificate[] { rootCert }, rootkeypair, "rootca",
		// "123456");
		// System.out.println("ca kssavepathname: "+ksSavePathName);
		//
		// certUtil.saveCertToFile(certsPath + "rootca.cer", rootCert);
		// certUtil.saveCertToKeyStore(certsPath + "truststore.pfx", rootCert,
		// "trustcert", "123456");//生成根证书密钥库

		// sub CA
		// PrivateKey privateKey =
		// certUtil.getPrivateFromKeyStore(certsPath+"RootCA.pfx", "rootca",
		// "123456");
		// X509Certificate x9 =
		// certUtil.getCertFromKeyStoreFile(certsPath+"RootCA.pfx", "rootca",
		// "123456", "PKCS12");
		//
		// KeyPair subkeypair = KeyUtil.generateKeyPair(1024);
		// String subjectDN = "C=cn,ST=bj,L=beijing,O=o1,OU=ou1,CN=localhost";
		// // X509Certificate subCert = certUtil
		// // .signSubCert(subjectDN, subkeypair.getPublic(), rootCert,
		// // rootkeypair.getPrivate(), 365);
		// X509Certificate subCert = certUtil
		// .signSubCert(subjectDN, subkeypair.getPublic(), x9,privateKey, 365);
		// certUtil.saveCertToFile(certsPath+"subca1.cer", subCert);
		// certUtil.saveCertChainToKeyStore(certsPath+"SubCA1.pfx",
		// new X509Certificate[] { subCert, x9 }, subkeypair,
		// "ESEALCA2009081011121355588", "123456");

		// String subjectDN =
		// "C=cn,ST=bj,L=beijing,O=o1,OU=ou1,CN=I am a subCA2";
		// X509Certificate subCert = certUtil.signSubCert(subjectDN,
		// subkeypair.getPublic(),x9, privateKey, 365);
		// certUtil.saveCertToFile(certsPath+"subca2.cer", subCert);
		// certUtil.saveCertChainToKeyStore(certsPath+"SubCA2.pfx", new
		// X509Certificate[] { subCert, x9 },
		// subkeypair,"ESEALCA2009081011121355588", "123456");

		// X509Certificate trustcert =
		// certUtil.getCertFromKeyStoreFile("D:/truststore.pfx","trustcert",
		// "123456","JKS");
		// Certificate[] certchain =
		// certUtil.getCertChainFromKeyStoreFile("D:/SubCA.pfx", "subca",
		// "123456");

		// // ESeal
		// PrivateKey privateKey =
		// certUtil.getPrivateFromKeyStore(certsPath+"RootCA.pfx", "rootca",
		// "123456");
		// X509Certificate x9 =
		// certUtil.getCertFromKeyStoreFile(certsPath+"RootCA.pfx", "rootca",
		// "123456", "PKCS12");
		//
		// KeyPair caKeyPair = new
		// KeyPair(x9.getPublicKey(),privateKey);//构造根ca密钥对
		//
		// ESealUtil esealUtil = new ESealUtil();
		// EsealInfo info = new EsealInfo();
		// info.setVersion(1);
		// info.setEsealName("I am a user");
		// info.setPictureData(new byte[] {'1', '2', '3' });
		// info.setEsealNotBefore(Calendar.getInstance().getTime());
		// info.setEsealNotAfter(Calendar.getInstance().getTime());
		// String[] userAttrs = new String[] { "name=li", "id=100", "sex=1" };
		//
		// for (int i = 0; i < userAttrs.length; i++) {
		// info.getUserAttr().add(userAttrs[i]);
		// }
		// //X509Certificate signCert =
		// certUtil.getCertFromCertFile("business_der.cer");
		// try {
		// info.setDerSignCert(x9.getEncoded());
		// } catch(CertificateEncodingException e)
		// {
		// writeLog(e,logger,this.getClass());
		// }
		// KeyPair esealkeypair = KeyUtil.generateKeyPair(1024); // eseal
		// request
		// String esealRequest = esealUtil.createESealRequest(info,
		// esealkeypair); //eseal cert
		//
		// X509Certificate esealCert =certUtil.signESeal(esealRequest, x9,
		// caKeyPair.getPrivate());
		//
		// certUtil.saveCertToFile(certsPath+"eseal.cer", esealCert);
		// //esealUtil.parseESealCert(esealCert);
		//
		// // byte[] b = new //
		// Hex().decode("433d636e2c53543d626a2c4c3d6265696a696e672c4f3d6f312c4f553d6f75312c434e3d4920616d20612075736572");
		// // System.out.println(new String(b));
		// // BigInteger bit = certUtil.createSerialNum();
		// // System.out.println(bit.longValue());
		// System.out.println("OK");

	}

}
