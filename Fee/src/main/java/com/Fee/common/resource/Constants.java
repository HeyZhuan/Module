package com.Fee.common.resource;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 取Properties数据
 * 
 * @author AIXIANG
 * 
 */
public class Constants {
	private static ReloadableResourceBundleMessageSource messageSource;

	private static Locale zh_CN = new Locale("zh", "CN");
	
	public static final String CX_CUSTOMER_ID="10027";
	

	public static String getString(String key) {
		String result = null;
		try {
			result = messageSource.getMessage(key, null, null, zh_CN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Integer getInteger(String key) {
		Integer result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Integer.parseInt(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Double getDouble(String key) {
		Double result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Double.parseDouble(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean getBoolean(String key) {
		Boolean result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Boolean.parseBoolean(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		Constants.messageSource = messageSource;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getString("adminPath");
	}

	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getString("frontPath");
	}

	public static void initLog() {
		Logger log = Logger.getLogger(Constants.class);
		PropertyConfigurator
				.configure("F:/Users/admin/Workspaces/MyEclipse Professional 2014/XinXingBossAdmin Maven Webapp/src/main/webapp/WEB-INF/config/log4j/log4j.properties");
		log.debug("Start of the main() in TestLog4j");
		log.info("Just testing a log message with priority set to INFO");
		log.warn("Just testing a log message with priority set to WARN");
		log.error("Just testing a log message with priority set to ERROR");
		log.fatal("Just testing a log message with priority set to FATAL");
		log.log(Priority.WARN, "Testing a log message use a alternate form");
		log.debug("test");
	}

}
