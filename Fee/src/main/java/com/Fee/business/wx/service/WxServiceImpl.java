package com.Fee.business.wx.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.wx.config.WxConfigEnum;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.math.DoubleUtil;
import com.Fee.common.str.StrUtils;

import weixin.popular.api.PayMchAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.paymch.Refundquery;
import weixin.popular.bean.paymch.RefundqueryResult;
import weixin.popular.bean.paymch.SecapiPayRefund;
import weixin.popular.bean.paymch.SecapiPayRefundResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;
import wx.boss.business.domain.WxCard;
import wx.boss.business.domain.WxOrder;
import wx.boss.business.domain.WxProduct;
import wx.boss.business.domain.WxPublicConfig;
import wx.boss.business.service.WxCacheService;
import wx.boss.common.common.ReqOrderIdUtil;
import wx.boss.common.enums.WxDataEnum;
import wx.boss.common.math.DecimalUtils;

@Service("WxService")
public class WxServiceImpl implements WxService {
	
	private static final Logger log = Logger.getLogger(WxServiceImpl.class);
	
	@Autowired
	private WxCacheService cacheService;

	@Override
	public String pay(WxOrder order,WxProduct product,WxPublicConfig con,WxCard card) {
		if(con!=null){
			Unifiedorder un=new Unifiedorder();
			un.setAppid(con.getAppid());
			un.setMch_id(con.getMchid());
			String unit = "流量";
			if(product.getUnit()==2){
				unit = "话费";
			}
			un.setBody("为"+order.getPhone()+" 充值"+product.getSize()+"元");
			un.setNonce_str(StrUtils.getRandomNum(30));
			un.setNotify_url(WxConfigEnum.CALL_BACK_URL.opt);//回调地址
			un.setOpenid(order.getOpenId());
			un.setOut_trade_no(ReqOrderIdUtil.getPhoneStrafficReqOrderId(order.getChargeTimes(), order.getId(), order.getReceiveTime()));//TODO 构造我们的订单号，和发送到cx 的订单号一致
			un.setSpbill_create_ip(WxDataEnum.IP.opt);
			un.setTotal_fee(DoubleUtil.mul(order.getSells(),"100").intValue()+"");
			un.setTrade_type(WxDataEnum.JSAPI.opt);
			LogUtils.printLog(log, order.getId()+"", "--支付--请求信息"+JsonUtils.obj2Json(un));
			UnifiedorderResult result = PayMchAPI.payUnifiedorder(un, con.getPaySecret());
			LogUtils.printLog(log, order.getId()+"", "--支付--返回信息"+JsonUtils.obj2Json(result));
			return result.getPrepay_id();
		}else{
			LogUtils.printLog(log, order.getId()+"", "配置为空：wxOpenId:"+order.getWxAppId()+","+cacheService.getWxConfig(order.getWxAppId()));
		}
		return null;
	}
	
	
	
	@Override
	public boolean reFund(WxPublicConfig con,WxOrder order) {
		SecapiPayRefund refund=new SecapiPayRefund();
		refund.setAppid(con.getAppid());
		refund.setMch_id(con.getMchid());
		refund.setNonce_str(StrUtils.getRandomNum(30));
		if(StringUtils.isNotBlank(order.getWxOrderId())){
			refund.setTransaction_id(order.getWxOrderId());
		}else{
			refund.setOut_trade_no(ReqOrderIdUtil.getPhoneStrafficReqOrderId(0, order.getId(), order.getReceiveTime()));
		}
		refund.setOut_refund_no(ReqOrderIdUtil.getPhoneStrafficReqOrderId(order.getChargeTimes(), order.getId(), order.getReceiveTime()));
		refund.setTotal_fee(DecimalUtils.mul(order.getSells(),"100").intValue());
		refund.setRefund_fee(DecimalUtils.mul(order.getSells(),"100").intValue());//TODO 若有卡券的时候，需测试是否需要减去代金券的价格
		refund.setOp_user_id(con.getMchid());
		LogUtils.printLog(log, order.getId()+"", "--退款--发送信息:"+JsonUtils.obj2Json(refund));
		SecapiPayRefundResult result = PayMchAPI.secapiPayRefund(refund, con.getPaySecret());
		LogUtils.printLog(log, order.getId()+"", "--退款--返回信息:"+JsonUtils.obj2Json(result));
		return StringUtils.equals(result.getResult_code(), "SUCCESS")?true:false; //退款申请是否成功提交
	}


	@Override
	public String queryReFund(WxPublicConfig con, WxOrder order) {
		Refundquery rq=new Refundquery();
		rq.setAppid(con.getAppid());
		rq.setMch_id(con.getMchid());
		rq.setNonce_str(StrUtils.getRandomNum(30));
		if(StringUtils.isNotBlank(order.getWxOrderId())){
			rq.setTransaction_id(order.getWxOrderId());
		}else{
			rq.setOut_trade_no(ReqOrderIdUtil.getPhoneStrafficReqOrderId(order.getChargeTimes(), order.getId(), order.getReceiveTime()));
		}
		LogUtils.printLog(log, order.getId()+"", "--查询退款--发送信息:"+JsonUtils.obj2Json(rq));
		RefundqueryResult result = PayMchAPI.payRefundquery(rq, con.getPaySecret());
		LogUtils.printLog(log, order.getId()+"", "--查询退款--返回信息:"+JsonUtils.obj2Json(result));
		
		if(StringUtils.equals(result.getResult_code(),"SUCCESS")){
			return result.getRefundqueryResultItems().get(0).getRefund_status();
		}else{
			return "FAIL";
		}
	}



	@Override
	public String getOauthUrl(String appid,String url,String state,boolean isAlarm) {
		String authUrl=SnsAPI.connectOauth2Authorize(appid, url, isAlarm, state);
		return authUrl;
	}



	@Override
	public boolean isFocusUser(String wxAppId, String openId) {
		boolean res=false;
		if(StringUtils.isNotBlank(wxAppId) && StringUtils.isNotBlank(openId)){
			User user = UserAPI.userInfo(TokenManager.getToken(wxAppId), openId);
			if(user!=null){
				res=user.getSubscribe()==0?true:false;
			}
		}
		return res;
	}
	
}
