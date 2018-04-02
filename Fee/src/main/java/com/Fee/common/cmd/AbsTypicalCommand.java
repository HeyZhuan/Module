package com.Fee.common.cmd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.Fee.common.json.JsonUtils;
import com.Fee.common.xml.XmlUtils;
import com.imlianai.common.cmd.TypicalCommand;

public abstract class AbsTypicalCommand extends TypicalCommand {

	private static Logger logger = Logger.getLogger(AbsTypicalCommand.class);
	/**
	 * 返回客户端JSON数据
	 * 
	 * @param response
	 * @param resultMap
	 *            数据体
	 * @param description
	 *            描述
	 * @return
	 */
	protected String responseJson(HttpServletResponse response, Map<Object, Object> resultMap, String description) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String strJson = JsonUtils.obj2Json(resultMap);
			if(strJson != null) {
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Length", strJson.getBytes("UTF-8").length + "");
			}
			logger.info(description + " Json:" + strJson);
			pw.print(strJson);
			pw.flush();
			pw.close();
		} catch(IOException e) {
			logger.error("AbsTypicalCommand.error:" + e.getMessage(),e);
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}
	
	/**
	 * 返回客户端JSON数据
	 * @param response
	 * @param json 数据体
	 * @param description 描述
	 * @return
	 */
	protected String responseJson(HttpServletResponse response, String json, String description) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if(json != null) {
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Length", json.getBytes("UTF-8").length + "");
			}
			logger.info(description + " Json:" + json);
			pw.print(json);
			pw.flush();
			pw.close();
		} catch(IOException e) {
			logger.error("AbsTypicalCommand.error:" + e.getMessage(),e);
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}
	/**
	 * 参数转化为XML
	 * @param response
	 * @param obj
	 * @param description
	 * @return
	 */
	protected String responseTmall(HttpServletResponse response,Object obj, String description) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String strJson = XmlUtils.Marshal(obj);
			if(strJson != null) {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Length", strJson.getBytes("UTF-8").length + "");
			}
			logger.info(description + " xml:" + strJson);
			pw.print(strJson);
			pw.flush();
			pw.close();
		} catch(IOException e) {
			logger.error("responseTmall.error:" + e.getMessage(),e);
		} catch (JAXBException e) {
			logger.error("responseTmall.error:" + e.getMessage(),e);
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}
	
}
