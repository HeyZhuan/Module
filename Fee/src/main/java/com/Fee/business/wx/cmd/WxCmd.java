package com.Fee.business.wx.cmd;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.business.productInfo.domain.ProductInfo;
import com.Fee.business.wx.config.WxConfigEnum;
import com.Fee.business.wx.domain.TextMessage;
import com.Fee.common.check.WxCheckUtil;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.resource.Constants;
import com.Fee.common.service.BaseService;
import com.Fee.common.str.StrUtils;
import com.Fee.common.time.TimeUtils;
import com.Fee.common.xml.XmlUtils;
import com.Fee.system.user.domain.User;
import com.imlianai.common.info.PackageMsg;

import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JsUtil;
import weixin.popular.util.SignatureUtil;
/**
 * 用于微信的支付 及  静默授权的请求
 * @author Zhuan
 *
 */
@Controller
@RequestMapping("wx")
public class WxCmd {
	
	private static final Logger log = LoggerFactory.getLogger(WxCmd.class);
	private static final String URL="http://llhfcz.sjllb.com";//"http://1x5146t734.iok.la/WxService";
	

	@Autowired
	private BaseService baseService;
	
	/**
	 * wx 页面跳转入口  微信/非微信浏览器
	 * @param type 0流量 1话费
	 * @param wxAppId 微信appId
	 * @param openId 用户openId,前台localstoage 存储，第二次跳转不需再次获取，TODO 不同公众号登陆时存储的是否为同一个
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/re")
	public void re(String type,String wxAppId,String openId,HttpServletRequest request,HttpServletResponse response){

		try {
			
			if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(wxAppId)){
				
				String url=StringUtils.equals(type, "0")?Constants.getString("weixin_charge_traffic_url"):Constants.getString("weixin_charge_fee_url");
				LogUtils.sendLog(log, "获取openId跳转地址", url);
				response.sendRedirect(url);
			}else{
				LogUtils.sendLog(log, "微信跳转数据为空", "type:"+type+",wxAppId:"+wxAppId+",openId:"+openId);
				throw new RuntimeException("数据错误");
			}
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "微信跳转异常:type:"+type+",wxAppId:"+wxAppId+",openId:"+openId, e);
			throw new RuntimeException("数据异常");
		}
		
	}
	
	
	/**
	 * 微信获取openId并跳转到对应页面
	 * @param state
	 * @param code
	 * @param response
	 */
	@RequestMapping(value = "/getOpenId")
	public void getOpenId(String state,String code,HttpServletResponse response){
		String url="";
		String openId="";
		try {
			if(StringUtils.isNotBlank(state) && StringUtils.isNotBlank(code)){
				SnsToken token = SnsAPI.oauth2AccessToken(WxConfigEnum.APP_ID.opt,WxConfigEnum.APP_SECRET.opt , code);
				openId=token==null?"":token.getOpenid();
				LogUtils.sendLog(log, "微信获取openId参数为空", "state:"+state+",code:"+code);
			}
			LogUtils.sendLog(log, openId, "微信获取openId 跳转地址:"+url);
			response.sendRedirect(WxConfigEnum.CHARGE_HTML_RUL.opt);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "微信获取openId参数异常:state:"+state+",code:"+code, e);
			throw new RuntimeException("微信获取openId参数异常");
		}
	}
	
	/**
	 *  生成订单
	 * @param openId 用户openId
	 *
	 * @return
	 */
	@RequestMapping(value = "/pay")
	@ResponseBody
	public Map<Object, Object> pay(String phone,String productId,String openId) {
		log.info("微信支付接收的参数为:phone:"+phone+",productId:"+productId+",openId:"+openId);
		Map<String, String> map=new HashMap<>();
		Map<Object, Object> resMap=new HashMap<>();
		if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(openId)){
			
				
				//获取产品
				ProductInfo product = baseService.get(ProductInfo.class, Integer.parseInt(productId));
				LogUtils.sendLog(log, phone, "需充值的产品:"+product==null?"":JsonUtils.obj2Json(product));
				
				
				WxOrder order =null;
				order = orderService.saveOrder(openId, wxAppId, product, phone,labelId,card);
				
				try {
					if(order!=null){
						
						//更新用户信息
						userService.updateUserInfoByOrder(order);					
						
						//进行支付
						String prepayId = wxService.pay(order, product,config,null);
						
						//返回对应信息到前台进行校验
						map.put("appId", config.getAppid());
						map.put("timeStamp", TimeUtils.getTimeStamp()+"");
						map.put("nonceStr", StrUtils.getRandomStr(18));
						map.put("package", "prepay_id="+prepayId);
						map.put("signType", "MD5");
						String sign = SignatureUtil.generateSign(map, config.getPaySecret());
						map.put("sign", sign.toUpperCase());
						map.put("wxAppId", wxAppId);
						map.put("type", product.getType()+"");
						resMap = PackageMsg.getRightOperCode(map);
					}else{
						LogUtils.printLog(log, phone, "订单生成失败");
						resMap = PackageMsg.getErrorMsg("订单生成失败，请联系客服");
					}
					
				} catch (Exception e) {
					LogUtils.printExceptionLog(log, phone, "支付或验证发生异常",e);
					resMap = PackageMsg.getErrorMsg("支付失败，请联系客服");
				}
		}
		 log.info("map:"+JsonUtils.obj2Json(resMap));
		return resMap;
		}
	
	
	/**
	 * 接收微信的推送事件(验证)
	 */
	@RequestMapping(value = "messageFromWx", method = RequestMethod.GET)
	@ResponseBody
	public String checkWx(HttpServletRequest request) {
		String string="success";
		Map<String, String> params = HttpUtils.getReqParams(request);
		if(params!=null&&params.size()>0){
			if(WxCheckUtil.checkSignature(params.get("signature"), params.get("timestamp"), params.get("nonce"))){
				string=params.get("echostr");
			}
		}
		return string;
	}
	
	
	/**
	 * 接收微信的推送事件---目前只处理关注事件
	 */
	@RequestMapping(value = "messageFromWx", method = RequestMethod.POST)
	@ResponseBody
	public void msgFromWx(HttpServletRequest request, PrintWriter out) {
		String str = HttpUtils.getReqPostString(request, log);
		LogUtils.printLog(log, "微信事件接收消息：",str);
		Map<String,String> map = XmlUtils.parseXML(new String[]{"ToUserName","FromUserName","Event"}, str);
		String wxId = map.get("ToUserName");
		String openId =map.get("FromUserName");
		WxUser u2=null;
		long start = System.currentTimeMillis();
		log.info("接受时间:"+start);
		try {
			String type = map.get("Event");
			if(StringUtils.equals(type, "subscribe")){
				
			}
				
			boolean isToKF=true;//是否把信息转到多客服
			//对公众号接收消息做判断 活动条件判断
			Map<String,String> msgMap = XmlUtils.parseXML(new String[]{"ToUserName","FromUserName","Content","MsgType"}, str);
			String content = msgMap.get("Content");
		
			
		
		} catch (Exception e) {
			LogUtils.printExceptionLog(log, "", "消息推送发生异常"+str, e);
		}
		return;
	}

	
	public String processRequest(Map<String,String> map,String content) {
		try{
		// 发送方帐号（一个OpenID）
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");
		// 默认回复一个"success"
		String responseMessage = "success";
		// 对消息进行处理
		// 文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setMsgType(msgType);
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(System.currentTimeMillis() +"");
		textMessage.setContent(content);
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		responseMessage = xstream.toXML(textMessage);
		
		return responseMessage;
		}catch (Exception e) {
			LogUtils.printExceptionLog(log, "", "消息推送发生异常", e);
		}
		return "";
	}
	

	public String processRequest(Map<String,String> map,String url,boolean isVaild) {
		try{
		log.info(JsonUtils.obj2Json(map));
		// 发送方帐号（一个OpenID）
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");
		// 默认回复一个"success"
		String responseMessage = "success";
		// 对消息进行处理
		// 文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setMsgType(msgType);
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(System.currentTimeMillis() + "");
		if (isVaild) {
			textMessage.setContent(" <a href='" + url + "'>点击参与</a>");
			log.info("微信活动 参与: " + JsonUtils.obj2Json(map));
		} else {
			textMessage.setContent("抱歉,您不具备参加该活动的条件");
			log.info("微信活动 不参与: " + JsonUtils.obj2Json(map));
		}
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		responseMessage = xstream.toXML(textMessage);
		return responseMessage;
		}catch (Exception e) {
			LogUtils.printExceptionLog(log, "", "消息推送发生异常", e);
		}
		return null;
	}

}
