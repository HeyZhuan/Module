package com.Fee.common.msg;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Fee.common.enums.WxConfigEnum;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;

import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.support.TokenManager;

/**
 * 模板消息发送工具类
 * @author Zhuan
 *
 */
public class ModelMsgUtils {
	
	 private static final Logger log = LoggerFactory.getLogger(ModelMsgUtils.class);
	
	/**
	 * 发送模板消息
	 * @param name
	 * @param data
	 * @param templateId
	 * @param openId
	 */
	public static void sendModelMsg(String[] name,String[] data,String templateId,String openId){
		
		
		try {
			LinkedHashMap<String, TemplateMessageItem> configData = getNecessaryData(name,data);
			
			String token = TokenManager.getToken(WxConfigEnum.APP_ID.opt);
			
			TemplateMessage message=new TemplateMessage();
			message.setData(configData);
			message.setTemplate_id(templateId);
			message.setTouser(openId);
			message.setUrl(WxConfigEnum.CHARGE_HTML_RUL.opt);
			TemplateMessageResult result = MessageAPI.messageTemplateSend(token, message);
			LogUtils.sendLog(log,openId,"模板信息返回消息:"+JsonUtils.obj2Json(result));
			
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, openId+"模板消息发送异常", e);
		}
		
	}
	
	/**
	 * 构造对应的模板信息内容
	 * @param name
	 * @param data
	 * @return
	 */
	public static LinkedHashMap<String, TemplateMessageItem> getNecessaryData(String[] name,String[] data){
		
		LinkedHashMap<String, TemplateMessageItem> necessaryDataMap=new LinkedHashMap<>();
		if(name!=null && data!=null && name.length==data.length){
			//获取必要的参数
			for (int i = 0; i < name.length; i++) {
				TemplateMessageItem item=new TemplateMessageItem(data[i], "#000000");
				necessaryDataMap.put(name[i],item);
			}
		}
		return necessaryDataMap;
	}
	
}
