package com.congoal.cert.req;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;


@SuppressWarnings({"deprecation","rawtypes","unchecked"})
public class ESealUtil {
	private Logger logger = Logger.getLogger(this.getClass());
	private String signAlgorithm = "SHA1WithRSA";
	private Eseal eseal;

	// Basic info
	public final static ASN1ObjectIdentifier ExtVersionOID = new ASN1ObjectIdentifier("2.16.156.514556.311");
	public final static ASN1ObjectIdentifier ExtesealNotBeforeOID = new ASN1ObjectIdentifier("2.16.156.514556.318");
	public final static ASN1ObjectIdentifier ExtesealNotAfterOID = new ASN1ObjectIdentifier("2.16.156.514556.319");
	
//	public final static DERObjectIdentifier ExtVersionOID = new DERObjectIdentifier(
//			"2.16.156.514556.311");
//	public final static DERObjectIdentifier ExtesealNotBeforeOID = new DERObjectIdentifier(
//			"2.16.156.514556.318");
//	public final static DERObjectIdentifier ExtesealNotAfterOID = new DERObjectIdentifier(
//			"2.16.156.514556.319");
	// Business cert info
	public final static ASN1ObjectIdentifier ExtSignCertSubjectOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.312");
	public final static ASN1ObjectIdentifier ExtSignCertSnOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.313");
	public final static ASN1ObjectIdentifier ExtSignCertIssuerOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.314");
	public final static ASN1ObjectIdentifier ExtSignCertHashOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.315");
	
//	public final static DERObjectIdentifier ExtSignCertSubjectOID = new DERObjectIdentifier(
//			"2.16.156.514556.312");
//	public final static DERObjectIdentifier ExtSignCertSnOID = new DERObjectIdentifier(
//			"2.16.156.514556.313");
//	public final static DERObjectIdentifier ExtSignCertIssuerOID = new DERObjectIdentifier(
//			"2.16.156.514556.314");
//	public final static DERObjectIdentifier ExtSignCertHashOID = new DERObjectIdentifier(
//			"2.16.156.514556.315");
	// pic info
//	public final static DERObjectIdentifier ExtPictureTypeOID = new DERObjectIdentifier(
//			"2.16.156.514556.316");
//	public final static DERObjectIdentifier ExtPictureDataOID = new DERObjectIdentifier(
//			"2.16.156.514556.302");
	
	public final static ASN1ObjectIdentifier ExtPictureTypeOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.316");
	public final static ASN1ObjectIdentifier ExtPictureDataOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.302");
	// ext attribute
//	public final static DERObjectIdentifier ExtAttrNameOID = new DERObjectIdentifier(
//			"2.16.156.514556.100");
//	public final static DERObjectIdentifier ExtEsealAttrOID = new DERObjectIdentifier(
//			"2.16.156.514556.301");
//	public final static DERObjectIdentifier ExtUserAttrOID = new DERObjectIdentifier(
//			"2.16.156.514556.303");
	
	public final static ASN1ObjectIdentifier ExtAttrNameOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.100");
	public final static ASN1ObjectIdentifier ExtEsealAttrOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.301");
	public final static ASN1ObjectIdentifier ExtUserAttrOID = new ASN1ObjectIdentifier(
			"2.16.156.514556.303");

	/**
	 * @return the esealInfo
	 */
	public Eseal getEseal() {
		return eseal;
	}

	/**
	 * 根据esealInfo添加证书申请的扩展项
	 * 
	 * @param esealInfo
	 * @param esealKeyPair
	 * @return
	 */
	
	public String createESealRequest(EsealInfo esealInfo, KeyPair esealKeyPair) {
		try {
			// org.bouncycastle.asn1.ASN1Object
			// Extensions object
			Vector oids = new Vector();
			Vector values = new Vector();

			DateFormat formatter = new SimpleDateFormat(
					"dd MM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

			oids.add(ExtVersionOID);
//			 values.add(new X509Extension(true, new DEROctetString(
//			 new DERInteger(esealInfo.getVersion()))));
			
			values.add(new X509Extension(true,new DEROctetString(new Integer(
					esealInfo.getVersion()).toString().getBytes())));
			
			oids.add(ExtesealNotBeforeOID);
			String notBefore = formatter.format(esealInfo.getEsealNotBefore());
			values.add(new X509Extension(true, new DEROctetString(notBefore
					.getBytes())));

			oids.add(ExtesealNotAfterOID);
			String notAfter = formatter.format(esealInfo.getEsealNotAfter());
			values.add(new X509Extension(true, new DEROctetString(notAfter
					.getBytes())));

			X509Certificate[] signCerts = this.parseDerCert(esealInfo
					.getDerSignCert());
			X509Certificate signCert = signCerts[0];

			// sign cert info
			oids.add(ExtSignCertSubjectOID);
			// values.add(new X509Extension(true, new DEROctetString(new
			// X509Name(
			// false, signCert.getSubjectDN().getName()))));
			values.add(new X509Extension(true, new DEROctetString(signCert
					.getSubjectDN().getName().getBytes())));

			oids.add(ExtSignCertSnOID);
			// values.add(new X509Extension(true, new DEROctetString(
			// new DERInteger(signCert.getSerialNumber()))));
			values.add(new X509Extension(true, new DEROctetString(signCert
					.getSerialNumber().toString().getBytes())));

			oids.add(ExtSignCertIssuerOID);
			// values.add(new X509Extension(true, new DEROctetString(new
			// X509Name(
			// false, signCert.getIssuerDN().getName()))));
			values.add(new X509Extension(true, new DEROctetString(signCert
					.getIssuerDN().getName().getBytes())));

			MessageDigest dgt = MessageDigest.getInstance("SHA-1");
			byte[] digest = dgt.digest(esealInfo.getDerSignCert());

			oids.add(ExtSignCertHashOID);
			values.add(new X509Extension(true, new DEROctetString(
					digest)));

			// pic info
			if (esealInfo.getPictureType() > -1) {
				oids.add(ExtPictureTypeOID);
//				values.add(new X509Extension(true, new DEROctetString(
//						new DERInteger(esealInfo.getPictureType()))));
				values.add(new X509Extension(true, new DEROctetString(
						new Integer(esealInfo.getPictureType()).toString().getBytes())));
			}

			if (esealInfo.getPictureData() != null) {
				oids.add(ExtPictureDataOID);
				values.add(new X509Extension(true, new DEROctetString(
						esealInfo.getPictureData())));
			}

			// ext attribute
			if (esealInfo.getEsealAttr() != null) {
				oids.add(ExtEsealAttrOID);
				values.add(new X509Extension(true, new DEROctetString(this
						.attrToASN1Encodable(esealInfo.getEsealAttr()))));
				
			}
			
			if (esealInfo.getUserAttr() != null) {
				oids.add(ExtUserAttrOID);
				values.add(new X509Extension(true, new DEROctetString(this
						.attrToASN1Encodable(esealInfo.getUserAttr()))));
			}
			// P10 request
			String subjectDN = "cn=" + esealInfo.getEsealName()
					+ ", ou=koaliieseal";
			
			X509Extensions extensions = new X509Extensions(oids, values);
			
			Attribute attribute = new Attribute(
					PKCSObjectIdentifiers.pkcs_9_at_extensionRequest,
					new DERSet(extensions));
			
			PKCS10CertificationRequest req = new PKCS10CertificationRequest(
					signAlgorithm, new X509Name(subjectDN),
					esealKeyPair.getPublic(), new DERSet(attribute),
					esealKeyPair.getPrivate());
			

			return new String(Base64.encode(req.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("createCertRequest error." + e.getMessage());
		}
		return null;
	}

	private static DERObject derObjectDecode(byte[] derData) {
		ASN1InputStream ain = null;
		try {
			ain = new ASN1InputStream(derData);
			return ain.readObject();
		} catch (IOException exp) {
			throw new IllegalArgumentException(exp.getMessage());
		} finally {
			try {
				ain.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void parseESealCert(X509Certificate esealCert) {

		eseal = new Eseal();

		// basic info
		eseal.setEsealSn(esealCert.getSerialNumber());
		parseEsealName(esealCert.getSubjectDN().getName());
		eseal.setIssuerDN(esealCert.getIssuerDN().getName());
		eseal.setEsealNotBefore(esealCert.getNotBefore());
		eseal.setEsealNotAfter(esealCert.getNotAfter());

		// sign cert info

		X509Extensions exts = null;
		try {
			X509CertificateStructure esealcertStruct = X509CertificateStructure
					.getInstance(ASN1Object.fromByteArray(esealCert
							.getEncoded()));
			exts = esealcertStruct.getTBSCertificate().getExtensions();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Enumeration enums = exts.oids();
		while (enums.hasMoreElements()) {
			DERObjectIdentifier oid = (DERObjectIdentifier) enums.nextElement();
			// System.out.println("oid: " + oid);
			byte[] bvalue = esealCert.getExtensionValue(oid.getId());

			// 1 if certGen.addExtension(oid, ext.isCritical(),
			// ext.getValue().getOctets());
			try {
				ASN1Object derObj = ASN1Object.fromByteArray(bvalue);
				if (derObj instanceof DEROctetString) {// customer extension
					// must DEROctetString
					byte[] value = ((DEROctetString) derObj).getOctets();
					if (oid.getId().endsWith(ExtSignCertSubjectOID.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						X509Name x509name = new X509Name((DERSequence) derObj);
						eseal.setSignCertSubject(x509name.toString());
					} else if (oid.getId().equals(ExtSignCertIssuerOID.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						X509Name x509name = new X509Name((DERSequence) derObj);
						eseal.setSignCertIssuer(x509name.toString());
					} else if (oid.getId().equals(ExtSignCertSnOID.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						DERInteger serialNum = DERInteger.getInstance(derObj);
						eseal.setSignCertSn(serialNum.getValue());
					} else if (oid.getId().equals(ExtPictureDataOID.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						DERBitString picData = DERBitString.getInstance(derObj);
						eseal.setPictureData(picData.getBytes());
					} else if (oid.getId().equals(ExtUserAttrOID.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						ArrayList userList = parseAttr(derObj.getEncoded());
						eseal.setUserAttr(userList);
					} else if (ExtEsealAttrOID.getId().equals(oid.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						ArrayList esealList = parseAttr(derObj.getEncoded());
						eseal.setEsealAttr(esealList);
					} else if (ExtVersionOID.getId().equals(oid.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						DERInteger verision = DERInteger.getInstance(derObj);
						eseal.setVersion(verision.getValue().intValue());
					} else if (ExtSignCertHashOID.getId().equals(oid.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						DERBitString hashsign = DERBitString
								.getInstance(derObj);
						eseal.setHashedSignCert(hashsign.getBytes());
					} else if (ExtPictureTypeOID.getId().equals(oid.getId())) {
						derObj = ASN1Object.fromByteArray(value);
						DERInteger picType = DERInteger.getInstance(derObj);
						eseal.setPictureType(picType.getValue().intValue());
					}
				} else {
					System.out
							.println("Unknown data type:" + derObj.getClass());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * @param derData
	 * @return
	 */
	private ArrayList parseAttr(byte[] derData) {
		GeneralNames gnames = GeneralNames
				.getInstance(derObjectDecode(derData));
		GeneralName[] nameArray = gnames.getNames();

		ArrayList list = new ArrayList();

		for (int i = 0; i < nameArray.length; i++) {
			if (GeneralName.otherName != nameArray[i].getTagNo()) {
				// throw new Exception("Illegal attr in eseal");
			}

			ASN1Sequence othername = DERSequence.getInstance(nameArray[i]
					.getName());
			DERObjectIdentifier oid = DERObjectIdentifier.getInstance(othername
					.getObjectAt(0));
			if (!ExtAttrNameOID.getId().equals(oid.getId())) {
				// throw new Exception("Illegal attr name oid in eseal");
			}

			DERUTF8String uname = DERUTF8String.getInstance(othername
					.getObjectAt(1));
			list.add(uname.getString());
		}

		return list;
	}

	/**
	 * @param subjectName
	 */
	public void parseEsealName(String subjectName) {
		String[] subject = subjectName.split(",");
		int size = subject.length;
		if (!"".equals(subjectName)) {
			for (int i = 0; i < size; i++) {
				if (subject[i].indexOf("CN=") >= 0) {
					eseal.setEsealName(subject[i].replaceAll("CN=", ""));
					break;
				}
			}
		}

	}

	private X509Certificate[] parseDerCert(byte[] derCert) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Collection col = cf.generateCertificates(new ByteArrayInputStream(
					derCert));
			int clen = col.size();
			X509Certificate[] x509s = new X509Certificate[clen];
			col.toArray(x509s);
			return x509s;
		} catch (Exception exp) {
			return null;
		}
	}

	private ASN1Encodable attrToASN1Encodable(ArrayList attr) {
		ASN1EncodableVector gnames = new ASN1EncodableVector();

		for (int i = 0; i < attr.size(); i++) {
			String s = (String) attr.get(i);
			ASN1EncodableVector v = new ASN1EncodableVector();
			v.add(ExtAttrNameOID);
			v.add(new DERUTF8String(s));
			DERSequence seq = new DERSequence(v);
			gnames.add(new GeneralName(GeneralName.otherName, seq));
		}

		return new DERSequence(gnames);
	}

	public static void main(String[] args) {
		String cert = "MIIEKDCCA5GgAwIBAgIIG+GocKPeBcAwDQYJKoZIhvcNAQEFBQAwVzESMBAGA1UEAwwJZXNlYWxjYWFhMQowCAYDVQQKDAFhMQowCAYDVQQHDAFhMQowCAYDVQQIDAFhMQswCQYDVQQGEwJaSDEQMA4GA1UECwwHRVNFQUxDQTAeFw0wOTA3MjEwMjUyMTNaFw0wOTA4MDcwMzMyNDJaMCUxDTALBgNVBAMMBGVzZWExFDASBgNVBAsMC2tvYWxpaWVzZWFsMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvINVs04ScdRNvxxg7IPxvgsXkGyjT/kbA6taA/GP8IwZt9ngZ2jtoPMiW3lGcAf7K+XY5v56BVWq6AlI7WUnbtjEzODf6jd2h2uxC5tgZWihuu+Gi8GJZjy+LQ4+fKm9dsJtCJS8rVo3F//QMYYFX6wAdukyKhB1ANKRMjkYMdwIDAQABo4ICLTCCAikwHQYDVR0OBBYEFOTpJtAKW/0kBULttjzacIeaRBXjMB8GA1UdIwQYMBaAFI9UfJNncAGunMAzlAH1vZvE2X+AMBIGCGCBHJ+zfII3AQH/BAMCAQEwgaIGCGCBHJ+zfII4AQH/BIGSMIGPMUQwQgYDVQQDDDswNDFAMGNhaWt1d3V6aGlodWF0d29hYUA0Njk3ODk0NTZAY2Fpa3V3dXpoaWh1YXR3b0AwMDAwMDAwMTERMA8GA1UECwwIT3BlcmF0b3IxETAPBgNVBAsMCDc4OTQ1NkAxMRQwEgYDVQQKDAtQQkMgVGVzdCBDQTELMAkGA1UEBhMCQ04wIQYIYIEcn7N8gjkBAf8EEgIQfeeMg7T2Libadx984iTXzDA0BghggRyfs3yCOgEB/wQlMCMxFDASBgNVBAoMC1BCQyBUZXN0IENBMQswCQYDVQQGEwJDTjAmBghggRyfs3yCOwEB/wQXAxUAxL9qLDNnOU+oYNh7Br7fbFwcgAowFQYIYIEcn7N8gi4BAf8EBgMEADEyMzBKBghggRyfs3yCLQEB/wQ7MDmgEgYHYIEcn7N8ZAwHbmFtZT1saaARBgdggRyfs3xkDAZpZD0xMDCgEAYHYIEcn7N8ZAwFc2V4PTEwSgYIYIEcn7N8gi8BAf8EOzA5oBIGB2CBHJ+zfGQMB25hbWU9bGmgEQYHYIEcn7N8ZAwGaWQ9MTAwoBAGB2CBHJ+zfGQMBXNleD0xMA0GCSqGSIb3DQEBBQUAA4GBAC8S6jAnYlNjL3tA1aqD00+7aHaiuZIn/QGXJ9IMLQc07i6xwAXoDdTTgVvGfeHWbBZ1msASKCzXp7yqPxW8rYcJgwc3OvE12v0NXcpRmenXrqlXryl8ZNe42V5aZ5TWyCbQBiLf35oNnMhYsa1RHyItxYNfgEBv6ODjPVRfKeqs";
		ESealUtil e = new ESealUtil();
		X509Certificate[] x509cert = e.parseDerCert(Base64.decode(cert
				.getBytes()));
		e.parseESealCert(x509cert[0]);
		// System.out.println("begin");
		// ESealUtil util = new ESealUtil();
		// EsealInfo info = new EsealInfo();
		// info.setEsealName("C=cn,ST=bj,L=beijing,O=o1,OU=ou1,CN=I am a user");
		// info.setPictureData(new byte[]{'1','2','3'});
		// System.out.println("key pair");
		// KeyPair pair = new KeyUtil().generateKeyPair(2048);
		// System.out.println("request");
		// util.createESealRequest(info, pair);

		// //String/byte[] to DERObject to String
		// String abc ="weeeeeeeeewww";
		// ASN1Object derObj = new DEROctetString(abc.getBytes());
		// System.out.println(derObj);
		//
		// byte[] value;
		// try {
		// value =
		// ((ASN1OctetString)ASN1Object.fromByteArray(derObj.getEncoded())).getOctets();
		// System.out.println(new String(value));
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// }

	}

}
