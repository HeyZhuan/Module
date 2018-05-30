package com.Fee.business.wx.service;

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.order.domain.Order;

/**
 * 用于微信支付
 * @author Zhuan
 *
 */
public interface WxService {

	/**
	 * 预支付接口
	 * @param appId
	 * @param order
	 * @param body
	 * @return 返回prepayId 
	 */
	public String pay(Order order,CustomerProduct product);
	
	/**
	 * 退款申请提交接口
	 * @param con
	 * @param order
	 * @return
	 */
	public boolean reFund(Order order);
	
	
	/**
	 * 退款查询接口
	 * 设为定时任务，每天查一次即可， 退回微信需20分钟，银行卡需3天
	 * @param con
	 * @param order
	 * @return
	 */
	public String queryReFund(Order order);
	
	
	/**
	 * 获取静默授权的url
	 * @param appid
	 * @param url
	 * @param state
	 * @param isAlarm  false 静默授权
	 * @return
	 */
	public String getOauthUrl(String appid,String url,String state,boolean isAlarm);
	
	/**
	 * 是否为新关注用户
	 * @param wxAppId
	 * @param openId
	 * @return
	 */
	public boolean isFocusUser(String wxAppId,String openId);
}
