package com.Fee.business.wx.cmd;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.customerProduct.service.CustomerProductService;
import com.Fee.business.order.domain.Order;
import com.Fee.business.order.service.OrderService;
import com.Fee.business.wx.service.WxService;
import com.Fee.common.check.WxCheckUtil;
import com.Fee.common.enums.WxConfigEnum;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.resource.Constants;
import com.Fee.common.service.BaseService;
import com.Fee.common.str.StrUtils;
import com.Fee.common.time.TimeUtils;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.common.info.PackageMsg;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
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
	

	@Autowired
	private BaseService baseService;
	@Autowired
	private WxService wxService;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * wx 页面跳转入口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/re")
	public void re(HttpServletRequest request,HttpServletResponse response){

		try {
			String address=WxConfigEnum.DOMAIN_NAME.opt+"/wx/getOpenId";
			String url = wxService.getOauthUrl(WxConfigEnum.APP_ID.opt, address, "", false);
			response.sendRedirect(url);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log,"", e);
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
		String openId="";
		try {
			if(StringUtils.isNotBlank(code)){
				SnsToken token = SnsAPI.oauth2AccessToken(WxConfigEnum.APP_ID.opt,WxConfigEnum.APP_SECRET.opt , code);
				openId=token==null?"":token.getOpenid();
				LogUtils.sendLog(log, "微信获取openId参数为空", "state:"+state+",code:"+code);
			}
			String url = WxConfigEnum.DOMAIN_NAME.opt+WxConfigEnum.CHARGE_HTML_RUL.opt+"?openId="+openId;
			LogUtils.sendLog(log, openId, "微信获取openId 跳转地址:"+url);
			response.sendRedirect(url);
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
				CustomerProduct product = baseService.get(CustomerProduct.class, Integer.parseInt(productId));
				LogUtils.sendLog(log, phone, "需充值的产品:"+product==null?"":JsonUtils.obj2Json(product));
				
				
				Order order =null;
				order = orderService.saveOrder(openId, product, phone);
				
				try {
					if(order!=null){
						
						//进行支付
						String prepayId = wxService.pay(order, product);
						
						//返回对应信息到前台进行校验
						map.put("appId", WxConfigEnum.APP_ID.opt);
						map.put("timeStamp", TimeUtils.getTimeStamp()+"");
						map.put("nonceStr", StrUtils.getRandomStr(18));
						map.put("package", "prepay_id="+prepayId);
						map.put("signType", "MD5");
						String sign = SignatureUtil.generateSign(map, WxConfigEnum.PAY_SECRET.opt);
						map.put("sign", sign.toUpperCase());
						resMap = PackageMsg.getRightOperCode(map);
					}else{
						LogUtils.sendLog(log, phone, "订单生成失败");
						resMap = PackageMsg.getErrorMsg("订单生成失败，请联系客服");
					}
					
				} catch (Exception e) {
					LogUtils.sendExceptionLog(log, phone,e);
					resMap = PackageMsg.getErrorMsg("支付失败，请联系客服");
				}
		}
		 log.info("map:"+JsonUtils.obj2Json(resMap));
		return resMap;
		}
	
	
	/**
	 * 获取产品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	@ResponseBody
	public List<JSONObject> getProduct(int customerId) {
		List<CustomerProduct> cups = customerProductService.getCupsByCustomerId(customerId);
		List<JSONObject> objList=new ArrayList<>();
		if(cups!=null){
			for (CustomerProduct cup : cups) {
				JSONObject obj=new JSONObject();
				obj.put("id", cup.getId());
				obj.put("price", cup.getPrice());
				obj.put("sells", cup.getSells());
				objList.add(obj);
			}
		}else{
			LogUtils.sendLog(log, customerId, "获取产品为空");
		}
		return objList;
	}
	
	/**
	 * 获取产品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkPhone", method = RequestMethod.GET)
	@ResponseBody
	public String checkPhone(String phone) {
		return orderService.checkPhone(phone);
	}
	
	/**
	 * 获取订单
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "getOrders", method = RequestMethod.GET)
	@ResponseBody
	public List<JSONObject> getOrders(String openId,String phone) {
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(phone)){
			return orderService.getOrderByPhoneAndOpenId(openId, phone);
		}
		log.info("查询参数为空: openId:"+(StringUtils.isBlank(openId)?"":openId)+"__phone"+(StringUtils.isBlank(phone)?"":phone));
		return new ArrayList<>();
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
		/*String str = HttpUtils.getReqPostString(request, log);
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
		return;*/
	}

	
	/*public String processRequest(Map<String,String> map,String content) {
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
	*/

}
