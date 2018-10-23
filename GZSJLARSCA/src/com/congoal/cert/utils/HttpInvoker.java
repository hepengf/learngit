package com.congoal.cert.utils;

import java.io.IOException;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.congoal.cert.pojo.HsmCallReq;
import com.congoal.cert.pojo.HsmCallResp;

/**
 * HttpInvoker
 *
 * @author ChenPeng
 * @date 2015/8/25
 */
@Service
public class HttpInvoker {
    private static final Logger logger = LoggerFactory.getLogger(HttpInvoker.class);

    private static final String INSTITUTION = "A0001";
    
    public static void main(String[] args) {
        getRootCert();
    }
    
    public static X509Certificate getRootCert() {
        HsmCallReq hsmReq = new HsmCallReq();
        hsmReq.setMsgtype("7001");
        hsmReq.setInstitution(INSTITUTION);
        hsmReq.setIndex(Integer.parseInt(CertCommonUtils.ROOTINDEX));
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("params", JSONObject.toJSONString(hsmReq));
        
        String result = post(CertCommonUtils.SJLURL, params);
        if (StringUtils.isNotBlank(result)) {
            HsmCallResp hsmResp = (HsmCallResp) JSONObject.parseObject(result, HsmCallResp.class);
            if ("00".equals(hsmResp.getCode())) {
                try {
                    return (X509Certificate)CertificateCoder.generateCertificate(BaseUtil.hexStringToBytes(hsmResp.getAnsdata()));
                } catch (CertificateException e) {
                    logger.error("certificate is error", e);
                }
            }
        }
        
        return null;
    }
    
    public static X509Certificate signRootCert(String startdate, String enddate, String dn) {
        HsmCallReq hsmReq = new HsmCallReq();
        hsmReq.setMsgtype("5003");
        hsmReq.setInstitution(INSTITUTION);
        hsmReq.setIndex(Integer.parseInt(CertCommonUtils.ROOTINDEX));
        hsmReq.setStartdate(startdate);
        hsmReq.setEnddate(enddate);
        hsmReq.setDn(dn);

        
        Map<String, String> params = new HashMap<String, String>();
        params.put("params", JSONObject.toJSONString(hsmReq));
        String result = post(CertCommonUtils.SJLURL, params);
        if (StringUtils.isNotBlank(result)) {
            HsmCallResp hsmResp = JSONObject.parseObject(result, HsmCallResp.class);
            if ("00".equals(hsmResp.getCode())) {
                try {
                    return (X509Certificate)CertificateCoder.generateCertificate(BaseUtil.hexStringToBytes(hsmResp.getAnsdata()));
                } catch (CertificateException e) {
                    logger.error("certificate is error", e);
                }
            }
        }
        
        return null;
    }
    
    public static X509Certificate signInnerServerCert(Integer keyIndex, String startdate, String enddate, String dn) {
        HsmCallReq hsmReq = new HsmCallReq();
        hsmReq.setMsgtype("5014");
        hsmReq.setInstitution(INSTITUTION);
        hsmReq.setIndex(Integer.parseInt(CertCommonUtils.ROOTINDEX));
        hsmReq.setSignindex(keyIndex);
        hsmReq.setStartdate(startdate);
        hsmReq.setEnddate(enddate);
        hsmReq.setDn(dn);

        Map<String, String> params = new HashMap<String, String>();
        params.put("params", JSONObject.toJSONString(hsmReq));
        String result = post(CertCommonUtils.SJLURL, params);
        if (StringUtils.isNotBlank(result)) {
            HsmCallResp hsmResp = JSONObject.parseObject(result, HsmCallResp.class);
            if ("00".equals(hsmResp.getCode())) {
                try {
                    return (X509Certificate)CertificateCoder.generateCertificate(BaseUtil.hexStringToBytes(hsmResp.getAnsdata()));
                } catch (CertificateException e) {
                    logger.error("certificate is error", e);
                }
            }
        }
        
        return null;
    }
    
    public static X509Certificate signCert(String key, String startdate, String enddate, String dn) {
        HsmCallReq hsmReq = new HsmCallReq();
        hsmReq.setMsgtype("5004");
        hsmReq.setInstitution(INSTITUTION);
        hsmReq.setIndex(Integer.parseInt(CertCommonUtils.ROOTINDEX));
        hsmReq.setKey(key);
        hsmReq.setStartdate(startdate);
        hsmReq.setEnddate(enddate);
        hsmReq.setDn(dn);

        Map<String, String> params = new HashMap<String, String>();
        params.put("params", JSONObject.toJSONString(hsmReq));
        String result = post(CertCommonUtils.SJLURL, params);
        if (StringUtils.isNotBlank(result)) {
            HsmCallResp hsmResp = JSONObject.parseObject(result, HsmCallResp.class);
            if ("00".equals(hsmResp.getCode())) {
                try {
                    return (X509Certificate)CertificateCoder.generateCertificate(BaseUtil.hexStringToBytes(hsmResp.getAnsdata()));
                } catch (CertificateException e) {
                    logger.error("certificate is error", e);
                }
            }
        }
        
        return null;
    }
    

    /**
     * 调用url接口
     */
    public static String post(String url, Map<String, String> params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpEntity entity = null;
        HttpPost httpPost;
        try {
            httpPost = new HttpPost(url);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            // 封装参数
            for (String s : params.keySet()) {
                   formParams.add(new BasicNameValuePair(s, params.get(s)));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(uefEntity);
            logger.debug("调用HTTP请求：{}", httpPost.getURI());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                if (result.indexOf("(") != -1 && result.indexOf(")") != -1) {
                    int start = result.indexOf("(");
                    int end = result.lastIndexOf(")");
                    result = result.substring(start + 1, end);
                }
                logger.info(result);
                return result;
            }
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 将model转换为map
     * @param object
     * @param fieldPrefix
     * @return
     */
    public Map castToMap(Object object , String fieldPrefix){
        Map<String , String> map = new HashMap<String ,String>();
        Field[] fields = object.getClass().getDeclaredFields();
        int length = fields.length;
        for(int i = 0 ; i < length ; i++){
            String fieldName = fieldPrefix + "." + fields[i].getName();
            try{
                boolean access = fields[i].isAccessible();
                fields[i].setAccessible(true);
                Object o = fields[i].get(object);
                if(o != null){
                    map.put(fieldName , o.toString());
                }
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;

    }


}
