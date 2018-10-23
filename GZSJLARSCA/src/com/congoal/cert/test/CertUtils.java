package com.congoal.cert.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.abdera.security.util.KeyHelper;
//import org.apache.abdera.security.util.KeyHelper;
import org.apache.commons.codec.binary.Base64;

import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.req.CertUtil;
import com.congoal.cert.utils.RSAEncrypt;

@SuppressWarnings("all")
public class CertUtils {
	public static void main(String[] args) throws Exception {
		// String str =
		// "b6 83 81 c4 70 52 d3 03 d1 b2 df 00 4a 1e b7 21 61 d9 4b 21 2f 79 71 90 e7 76 71 eb 6a 76 89 00 52 bf 13 52 70 9c a5 2d 1f a7 25 ce 2c 1c ad 48 4a 5b 0b b4 98 8e 1a 90 d0 4b 14 21 97 0d 8f 64 01 35 b9 cd 43 9d 32 6d 15 82 0f 45 1c 12 14 6a 20 58 01 24 5b bf 23 ec e6 0d 29 35 cb e3 bd 72 d1 ab b7 e6 ca 0a ab b8 0a ca 15 94 94 4d 63 9a 94 e6 a1 e8 b6 a0 e6 b0 19 e5 de 66 bb e0 40 91";
		//String str = "b6 83 81 c4 70 52 d3 03 d1 b2 df 00 4a 1e b7 21 61 d9 4b 21 2f 79 71 90 e7 76 71 eb 6a 76 89 00 52 bf 13 52 70 9c a5 2d 1f a7 25 ce 2c 1c ad 48 4a 5b 0b b4 98 8e 1a 90 d0 4b 14 21 97 0d 8f 64 01 35 b9 cd 43 9d 32 6d 15 82 0f 45 1c 12 14 6a 20 58 01 24 5b bf 23 ec e6 0d 29 35 cb e3 bd 72 d1 ab b7 e6 ca 0a ab b8 0a ca 15 94 94 4d 63 9a 94 e6 a1 e8 b6 a0 e6 b0 19 e5 de 66 bb e0 40 91";
		String str = "308204bd020100300d06092a864886f70d0101010500048204a7308204a30201000282010100841c4b9cb6e6fcd06567c381e945711cb723b775a011414892eacd7de7d85a2814cecb0c14c524cf545b41e02be4a22b90e6e72e5a5ede50475a19d34e5d307e72c7dc794eb5d8a0d0602e6438be9d49b67782d17db7753af10b24341b4f4b43cf63903ef27d5b70919c8afb15eeb44f2a191800ed29f79ad7da9112dd8ba70700215b76d2ac4f177a930b169423a7c09cfb1d15b6e7ed541445921c5c1464ee3ce8507c40a7f2eb1d43f0e7964eacee72ddcc5c74abbb9d81d1307af7d782298c368ce0bc6a563522435e23ba728135644064e0390f50a48aa548020d97c2cd7d8372e696bb000de49085177e9f94fa9de5a1035dc84ac1eee6f137fadb94c102030100010282010046c048964335ed8b45e38e46d2c107d4d317e3b6b0e4cccbab32e89e573dfcfa9e3a296fece7afd101626623421e8de33b150e163afbb79fd2b6a4421023c446d3fdc94b8625c94046985bfffc8bc8fca325d9ff8b8e66d00c2b65562a920ac2e41c7431e1f7d4b2b2a8dd9d9e9f5a1d537460f08e12fdb2058bde49f6304660a828d4a43a6e5f10d01a395ddc7e03640ecfe5ad0ea869e404e04be28db5e981b530b4e67cf3312417e7679f896d75c4f852d7bf3674f0b40086a87bcbeda0c719d2e22c2b52f1d887d484baca52aa605d7cb0244c21236fde225d1e59b5b2fab46d34342d10e04c76ce581b95e8ec391382d353e54e4d667df9f7bf8c159ce102818100d5faddf11b5f182e4b779c0d8779dfcf81a8397b3b5070c458c071898f6771d4899dcabdafe57e1f3223aba5c3f930e1a529309b9b282798087b10cfae8265e191ec3d321400f28747adce0b184b488cdb06bf18058d88fbb5dc886e5788f947ac3183d0e1108735e2bab6ff543f56c8e56d5d712fddfe3613e5a51b60efd24d028181009e0db6ead48f6ad1e87740b440860345038bce8be0c6ac82165ebdcb32d838f6ba7e921ee3a9fa34de994eefbe4ff2749832f77ec04e2645636f784588b8676acf0d6853ed0632ea8a88a2524307d688a5f4901538f00d3c50f4055fc21b5b56b421744defa13e1e4fdbe9203f3b9184f20c5a3872150cec66cf7b73d28e7e4502818049fdf39d19ffea16a815f62a5f7c6f3e0bf1a5decd05e5d89f6ffc52baf9e44cc0418607cdb927c195ceebdde84454f8d1e9ca5b246de0331c4ba1e68ecb9c7b41c054f0ea25e1b791bbb211f816a041c4c3327516067925c11f3f3618a2cec04ff4d745f56d87b3c47118327669689313729beb35c827cd7f57db9dfa0933c90281810084540b2fb5639615c4c2036467194ca77d7e9221bec1d830da35dc45c27e11c3510523a5c50acd37729f7cdd70c352c597a02462f4f2f94be322b2d9d4cc3f99c75cfc5a37fa96aa93ee2da91e01f3f658abd7ee4ccb7ee95f05cf720afc088f21255950df2e6ae9ca06deba66f6a2b1624e41433d10aecf468cb459969242d902818047703f4b41c9953f5a21ba9ab0eefce869c4a60fa09174d7761c26a1943449622f492b2c170fdfddba8561629143bf6a1c1e2e0fa2b458dade47399c3784d3049d83898d922d7f9b0c24e83f2cb6bea1294f712af523a7874905953eef6d3242af84fd2e4cf3e7937ca247441f04c0066e682accbb74f9a1d38a25fc14862a4b";
		String publicKeyStr = str.replaceAll(" ", "").toUpperCase();
		System.out.println(publicKeyStr);
//		PublicKey publicKey = RSAEncrypt.loadPublicKeyByStr(publicKeyStr);
////		PublicKey publicKey = KeyHelper.generatePublicKey(publicKeyStr);
////		RSAEncrypt.loadPrivateKeyByStr(privateKeyStr);
////		String str1 =bytesToHexString(Base64.encodeBase64(publicKey.getEncoded()));
//		System.out.println(bytesToHexString(publicKey.getEncoded()));
		
		PrivateKey privateKey = RSAEncrypt.loadPrivateKeyByStr(publicKeyStr);
		System.out.println(bytesToHexString(privateKey.getEncoded()));
	}


	 
	public static String bytesToHexString(byte[] src){  
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
	
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	
	/**
	 * 16进制转成2进制数组
	 * 
	 * @param Str
	 */
	public static byte[] debugHexStrToBinary(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte[] toBytes(String str) {
		if (str == null) {
			throw new IllegalArgumentException("binary string is null");
		}
		char[] chs = str.toCharArray();
		byte[] bys = new byte[chs.length / 2];
		int offset = 0;
		int k = 0;
		while (k < chs.length) {
			bys[offset++] = (byte) ((toInt(chs[k++]) << 4) | toInt(chs[k++]));
		}
		return bys;
	}

	private static int toInt(char a) {
		if (a >= '0' && a <= '9') {
			return a - '0';
		}
		if (a >= 'a' && a <= 'f') {
			return a - 'a' + 10;
		}
		if (a >= 'A' && a <= 'F') {
			return a - 'A' + 10;
		}
		throw new IllegalArgumentException("parameter \"" + a
				+ "\"is not hex number!");
	}
	/**
	 * 压缩文件
	 * @param uncerts
	 * @throws Exception
	 */
	public static void zipAllFiles(List<UnCertAward> uncerts, String fpath, String fname) throws Exception {
		
		FileOutputStream os = new FileOutputStream(new File(fpath+fname));
		ZipOutputStream zos = new ZipOutputStream(os);
		for (int i = 0; i < uncerts.size(); i++) {
			UnCertAward uncert = uncerts.get(i);
			String filename = uncert.getFname();
			String path = uncert.getPath();
			FileInputStream fis = new FileInputStream(path);
			System.out.println("entryname: "+filename);
			ZipEntry zipEntry = new ZipEntry(filename);
			zos.putNextEntry(zipEntry);
			byte[] buf = new byte[1024];
			int bufz = 1024;
			int len = 0;
			while ((len=fis.read(buf, 0, bufz))>0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			fis.close();
		}
		zos.flush();
		zos.close();
	}
}
