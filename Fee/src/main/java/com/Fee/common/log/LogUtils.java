package com.Fee.common.log;

import org.slf4j.Logger;

import com.Fee.common.json.JsonUtils;

public class LogUtils {
	
	
	/**
	 * 打印异常日志
	 * @param log
	 * @param id
	 * @param e
	 */
	public static void sendExceptionLog(Logger log,String id,Exception e){
		log.info("------"+id+"-----发生异常:"+e.getMessage(),e);
		log.error("------"+id+"-----发生异常:"+e.getMessage(),e);
	}
	
	/**
	 * 发送寻常日志
	 * @param log
	 * @param id
	 * @param param
	 */
	public static void sendLog(Logger log,Object id,String param){
		log.info("----"+JsonUtils.obj2Json(id)+"-----:   "+param);
	}
}
