package com.congoal.cert.pojo;


public class HsmCallReq {
    
    private String msgtype;
    private String institution;
    private String data;
    
    private String key;
    
    private Integer index;
    private Integer signindex;
    
    private String startdate;
    private String enddate;
    private String dn;
    
    private String signdata;
    
    public String getMsgtype() {
        return msgtype;
    }
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSignindex() {
        return signindex;
    }
    public void setSignindex(Integer signindex) {
        this.signindex = signindex;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    public String getDn() {
        return dn;
    }
    public void setDn(String dn) {
        this.dn = dn;
    }
    public String getSigndata() {
        return signdata;
    }
    public void setSigndata(String signdata) {
        this.signdata = signdata;
    }
}
