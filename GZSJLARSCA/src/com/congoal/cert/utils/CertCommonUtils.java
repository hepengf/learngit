package com.congoal.cert.utils;

import java.io.File;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import com.congoal.cert.listener.InitListener;

public abstract class CertCommonUtils {
	public static final String CA_ALIAS = InitListener.getValue("CA_ALIAS", "1");//rootca
	public static final String PASSWORD = InitListener.getValue("PASSWORD", "123456");
	
	public static final String STORE_TYPE_PKCS12 = "PKCS12";
	public static final String STORE_TYPE_JKS = "JKS";
	//生成的根证书前缀
	public static final String ROOTCA_PREFIX = InitListener.getValue("ROOTCA_PREFIX", "ROOTCA");
	//默认CA文件名
	public static final String CA_NAME = "root.keystore";//RootCA.pfx
	public static final String ROOT_CERT_NAME = ROOTCA_PREFIX+".cer";
	//CA路径
	public static final String ROOTCA_PATH = InitListener.getValue("ROOTCA_PATH", "/usr/soft/tomcat/InterfaceManage/tomcat7/webapps/ARSCA/certs/root/");
	//证书签发路径(根证书)
	public static final String ROOTCA_ONLINECERTSPATH = InitListener.getValue("ROOTCA_ONLINECERTSPATH", "/usr/soft/tomcat/InterfaceManage/tomcat7/webapps/ARSCA/certs/new/root/");
	
	//压缩包存放路径
	public static final String ZIPFILE_ONLINECERTSPATH = InitListener.getValue("ZIPFILE_ONLINECERTSPATH", "/usr/soft/tomcat/InterfaceManage/tomcat7/webapps/ARSCA/certs/zip/");
	//证书签发路径(根证书)
	public static final String SUBCA_ONLINECERTSPATH = InitListener.getValue("SUBCA_ONLINECERTSPATH", "/usr/soft/tomcat/InterfaceManage/tomcat7/webapps/ARSCA/certs/new/sub/");
	//导出证书文件的后缀
	public static final String EXPORT_CERT_SUFFIX = InitListener.getValue("EXPORT_CERT_SUFFIX", "cer");
	//证书CRL
	public static final String CRL_ONLINECERTSPATH = InitListener.getValue("CRL_ONLINECERTSPATH", "/usr/soft/tomcat/InterfaceManage/tomcat7/webapps/ARSCA/certs/crl/");
	//crl文件后缀
	public static final String CRL_SUFFIX = InitListener.getValue("EXPORT_CRL_SUFFIX", "crl");
	//crl下次更新时间
	public static final String CRL_NEXT_UPDATE_TIME = InitListener.getValue("CRL_NEXT_UPDATE_TIME", "30");
	
	//服务器证书有效时间（年）
	public static final String SERVICE_DUETIME=InitListener.getValue("SERVICE_DUETIME", "5");
	
	//终端证书有效时间（年）
	public static final String DEVICE_DUETIME=InitListener.getValue("DEVICE_DUETIME", "2");
	
	//加密机接口URL
	public static final String SJLURL=InitListener.getValue("SJLURL", " http://10.52.0.203:8080/ars-hsm/hsm_call.do");
	
	//根密钥索引
	public static final String ROOTINDEX=InitListener.getValue("ROOTINDEX", "-1");
	
	//服务器密钥索引
	public static final String SIGNINDEX=InitListener.getValue("SIGNINDEX", "-1");
	
	//接口自签发根证书消息类型
	public static final String ROOTMSGTYPE="5003";
	
	//接口签发证书消息类型
	public static final String CERTMSGTYPE="5004";
	
	//接口签发自己的服务器证书消息类型
	public static final String SIGNMSGTYPE="5014";
	
	//接口响应码
	public static final String CODE="00";
	
	
	/**
	 * 密钥库文件后缀
	 */
	public static final String KEYSTORETYPE_PFX = ".pfx";
	public static final String KEYSTORETYPE_KEYSTORE = ".keystore";
	
	
	/**
	 * 公钥导入文件映射
	 */
	public static final String MAP_DEVICE_ID = "deviceId";//公钥标识
	public static final String MAP_PUBLIC_KEY = "key";//设备唯一号
	public static final String MAP_SERVER_IP = "serverIp";//服务器ip
	public static final String MAP_COMPANY= "company";//生产厂家/服务接入商
	
	public static final int CERT_SERVER = 4;//终端证书
	public static final int CERT_MOBILE = 5;//服务器证书
	
	 /**  
     * 读取证书序列号  
     * @param cert  
     * @return  
     */  
    public static String getSerialNumber(X509Certificate cert) {   
        if(null == cert) return null;   
        byte [] serial = cert.getSerialNumber().toByteArray();             
        if(serial.length>0){   
            String serialNumberString = new String();   
            for(int i=0;i<serial.length;i++){   
                String s =Integer.toHexString(Byte.valueOf(serial[i]).intValue());   
                if(s.length()==8) s = s.substring(6);   
                else if(1==s.length()) s="0"+s;   
                serialNumberString+=s+" ";   
            }   
            return serialNumberString;   
        }   
        return null;   
    } 
    /**
     * 返回文件
     * @param path
     * @return
     */
    public static String getOldCrlFile(String path) {
    	if (null == path) return null;
    	File file = new File(path);
    	if (file.exists()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File subFile = listFiles[i];
				if (subFile.getName().endsWith("crl")) {
					return subFile.getName();
				}
			}
		}
    	return null;
    }
    
    public static void main(String[] args) {
    	System.out.println(CRL_ONLINECERTSPATH);
    	String CRL_ONLINECERTSPATH = "F:/pojo_wse14/CCA/WebRoot/certs/crl/aaa.hh";
    	int index = CRL_ONLINECERTSPATH.lastIndexOf("/");
    	String clrFname = CRL_ONLINECERTSPATH.substring(index+1, CRL_ONLINECERTSPATH.length());
    	System.out.println(clrFname);
		System.out.println(getOldCrlFile(CRL_ONLINECERTSPATH));
	}
}
