package com.Fee.business.wx.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.order.domain.Order;
import com.Fee.common.enums.WxConfigEnum;
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

@Service("WxService")
public class WxServiceImpl implements WxService {
	
	private static final Logger log = LoggerFactory.getLogger(WxServiceImpl.class);

	@Override
	public String pay(Order order,CustomerProduct product) {
		Unifiedorder un=new Unifiedorder();
		un.setAppid(WxConfigEnum.APP_ID.opt);
		un.setMch_id(WxConfigEnum.MCH_ID.opt);
		un.setBody("为"+order.getPhone()+" 充值"+product.getPrice()+"元");
		un.setNonce_str(StrUtils.getRandomStr(30));
		un.setNotify_url(WxConfigEnum.DOMAIN_NAME.opt+WxConfigEnum.CALL_BACK_URL.opt);//回调地址
		un.setOpenid(order.getOpenId());
		un.setOut_trade_no(order.getSendOrderId());//TODO 构造我们的订单号，和发送到cx 的订单号一致
		un.setSpbill_create_ip(WxConfigEnum.IP.opt);
		un.setTotal_fee(DoubleUtil.mul(order.getPrice(),"100").intValue()+"");
		un.setTrade_type(WxConfigEnum.JSAPI.opt);
		LogUtils.sendLog(log, order.getId()+"", "--支付--请求信息"+JsonUtils.obj2Json(un));
		UnifiedorderResult result = PayMchAPI.payUnifiedorder(un, WxConfigEnum.PAY_SECRET.opt);
		LogUtils.sendLog(log, order.getId()+"", "--支付--返回信息"+JsonUtils.obj2Json(result));
		return result.getPrepay_id();
	}
	
	
	
	@Override
	public boolean reFund(Order order) {
		SecapiPayRefund refund=new SecapiPayRefund();
		refund.setAppid(WxConfigEnum.APP_ID.opt);
		refund.setMch_id(WxConfigEnum.MCH_ID.opt);
		refund.setNonce_str(StrUtils.getRandomStr(30));
		if(StringUtils.isNotBlank(order.getWxOrderId())){
			refund.setTransaction_id(order.getWxOrderId());
		}else{
			refund.setOut_trade_no(order.getSendOrderId());
		}
		refund.setOut_refund_no(order.getSendOrderId());
		refund.setTotal_fee(DoubleUtil.mul(order.getPrice(),"100").intValue());
		refund.setRefund_fee(DoubleUtil.mul(order.getPrice(),"100").intValue());
		refund.setOp_user_id(WxConfigEnum.MCH_ID.opt);
		LogUtils.sendLog(log, order.getId()+"", "--退款--发送信息:"+JsonUtils.obj2Json(refund));
		SecapiPayRefundResult result = PayMchAPI.secapiPayRefund(refund, WxConfigEnum.PAY_SECRET.opt);
		LogUtils.sendLog(log, order.getId()+"", "--退款--返回信息:"+JsonUtils.obj2Json(result));
		return StringUtils.equals(result.getResult_code(), "SUCCESS")?true:false; //退款申请是否成功提交
	}


	@Override
	public String queryReFund(Order order) {
		Refundquery rq=new Refundquery();
		rq.setAppid(WxConfigEnum.APP_ID.opt);
		rq.setMch_id(WxConfigEnum.MCH_ID.opt);
		rq.setNonce_str(StrUtils.getRandomStr(30));
		if(StringUtils.isNotBlank(order.getWxOrderId())){
			rq.setTransaction_id(order.getWxOrderId());
		}else{
			rq.setOut_trade_no(order.getSendOrderId());
		}
		LogUtils.sendLog(log, order.getId()+"", "--查询退款--发送信息:"+JsonUtils.obj2Json(rq));
		RefundqueryResult result = PayMchAPI.payRefundquery(rq, WxConfigEnum.PAY_SECRET.opt);
		LogUtils.sendLog(log, order.getId()+"", "--查询退款--返回信息:"+JsonUtils.obj2Json(result));
		
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
