package com.congoal.cert.utils;

import org.apache.commons.lang3.StringUtils;

public abstract class SimpleStringUtil {
	/**
	 * 格式化页面字符串
	 * @param col
	 * @return
	 */
	public static String formatPageCol(String col) {
		return StringUtils.isEmpty(col) || StringUtils.isBlank(col) ? "&nbsp;" : col
				.trim();
	}
}
