package com.congoal.cert.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatUtils {
	private SimpleDateFormatUtils() {
	}

	private static SimpleDateFormat yyyymmdd = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat mmddhhmmss = new SimpleDateFormat(
			"MMddHHmmss");

	public static String formatDateReturnYYYMMDD(Date date) {
		if (null == date)
			return "";
		return yyyymmdd.format(date);
	}

	public static String formatDateReturnYYYYMMDDHHMMSS(Date date) {
		if (null == date)
			return "";
		return yyyymmddhhmmss.format(date);
	}
	
	public static String formatDateReturnMMDDHHMMSS(Date date) {
		if (null == date)
			return "";
		return mmddhhmmss.format(date);
	}

	public static SimpleDateFormat returnYYYYMMDDSdf() {
		return yyyymmdd;
	}

	public static void main(String[] args) {
		System.out.println(SimpleDateFormatUtils.formatDateReturnYYYYMMDDHHMMSS(new Date()));
	}
}
