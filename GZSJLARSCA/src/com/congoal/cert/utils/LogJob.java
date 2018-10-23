package com.congoal.cert.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public abstract class LogJob {

	protected final static SimpleDateFormat yyyyMMhhHHmmss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	protected final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyy-MM-dd");

	public final static void writeLog(Throwable e, Logger logger, Class clazz) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			final String str = sw.toString();
			// logger.error("["+clazz.getName()+","+yyyyMMhhHHmmss.format(new
			// Date())+"]: "+str);
			logger.error("[" + clazz.getName() + ","
					+ yyyyMMhhHHmmss.format(new Date()) + "]: " + str);
		} finally {
			try {
				if (sw != null) {
					sw.close();
					sw = null;
				}
				if (pw != null) {
					pw.close();
					pw = null;
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

}
