package com.congoal.cert.utils;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonObject {

	/**
	 * 常用变量
	 */
	
	private String msg;
	private boolean success;
	private String ids;//页面层传递过来用于批量操作的逗号分隔的多个id
	private String modifyId;//是否为更新,如果为-1为新增，否则为修改并且为要修改的id
	private Map map;
	
	
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 分页用变量
	 */
	private int totalCount;
	private int start;
	private int limit;
	private List root;

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List getRoot() {
		return root;
	}
	public void setRoot(List root) {
		this.root = root;
	}
	
}
