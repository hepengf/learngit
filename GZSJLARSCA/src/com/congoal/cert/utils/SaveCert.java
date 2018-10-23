package com.congoal.cert.utils;

import java.io.File;
import java.io.FileOutputStream;

public class SaveCert {
	public static void SaveCer(byte[] cert) throws Exception{
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(CertCommonUtils.ROOTCA_PATH+CertCommonUtils.ROOT_CERT_NAME));
			fout.write(cert);
			fout.flush();
		//按照流程是保存文件还是如何
		} finally {
			if(null != fout)
			{
				fout.close();
			}
		}
}
}
