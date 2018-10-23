package com.congoal.cert.listener;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.congoal.cert.utils.LogJob;

public final class InitListener extends LogJob implements ServletContextListener {
	public static Logger LOGGER = Logger.getLogger(InitListener.class);
	/**
	 * 配置文件名称
	 */
	private final static String LOAD_PROPERTIES_FILE = "config.properties";
	/**
	 * 初始化参数
	 */
	private static Map<String, String> PARAMS = new HashMap<String, String>();

	/**
	 * 从内存中加载指定变量的值,当内存中没有指定变量值的情况下，返回默认值
	 * 
	 * @param key
	 *            待获取的键
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		if (PARAMS.get(key) == null)
			return defaultValue;
		return PARAMS.get(key);
	}

	private final void init(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		InputStream is = null;
		try {
			Properties props = new Properties();
			is = servletContext.getResourceAsStream("/WEB-INF/classes/"+LOAD_PROPERTIES_FILE);
//			is = InitListener.class.getResourceAsStream(LOAD_PROPERTIES_FILE);
			props.load(is);

			for (Entry<Object, Object> entry : props.entrySet()) {
				if("ROOTCA_ONLINECERTSPATH".equals(entry.getKey()) || "SUBCA_ONLINECERTSPATH".equals(entry.getKey()))
				{
					try{
						File dir = new File(entry.getValue()+"");
						
						if(dir != null  && dir.isDirectory() && !dir.exists())
						{
							dir.mkdirs();
						}
					}catch(Exception e)
					{
						writeLog(e, LOGGER, InitListener.class);
					}
				}
				
				PARAMS.put(entry.getKey() + "", entry.getValue() + "");
				LOGGER.debug("init param: "+entry.getKey()+",value: "+entry.getValue());
			}

		} catch (Exception e) {
			writeLog(e, LOGGER, InitListener.class);
		} finally {
			try {
				if(is!=null)
					is.close();
				is = null;
			} catch (Exception e) {
				writeLog(e, LOGGER, InitListener.class);
			}
		}

	}

	private final void destory() {
//		for (String key : PARAMS.keySet()) {
//			PARAMS.remove(key);
//		}
//		PARAMS = null;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.debug("server stoping...");
		destory();
		LOGGER.debug("server stoped...");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LOGGER.debug("server starting...");
		init(event);
		LOGGER.debug("server started...");
	}

}
