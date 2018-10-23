package com.congoal.cert.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;


public class CreateCertUtil {
	public static Certificate generateCertificate(byte[] encoded) throws Exception {
		Certificate cert = null;
		InputStream is = null;
		
		try {
			CertificateFactory certfactory = CertificateFactory.getInstance("X.509");
			is = new ByteArrayInputStream(encoded);
			cert = certfactory.generateCertificate(is);
		} finally {
			close(is);
		}
		
		return cert;
	}

	private static void close(InputStream is) {
		if(is!=null)
		{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
