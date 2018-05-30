package com.Fee.business.work.supplier.bigboss.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.order.domain.Order;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.business.work.supplier.BaseSupplierService;
import com.Fee.business.work.supplier.bigboss.util.BigBoss_Util;
import com.Fee.common.enums.OrderStatusEnum;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.log.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 大Boss
 * @author Zhuan
 *
 */
@Service
public class BigBossServiceImpl implements BaseSupplierService{

	private static final Logger log = Logger.getLogger(BigBossServiceImpl.class);
	private static final String URL="http://api.lljypt.com/capi/trade.charge";
	
	@Override
	public BizResponse handleBizReq(Order order, SupplierProduct product) {
		String order_orderId = order.getSendOrderId();
		BizResponse br = new BizResponse(order_orderId);
		try {
			String sendStr = BigBoss_Util.getSendStr(order,product,order_orderId);
			LogUtils.sendLog(log, order.getId()+"", "发送参数"+sendStr);
			String sendRes = HttpUtils.sendPost(URL, sendStr, "UTF-8");
			LogUtils.sendLog(log, order.getId()+"", "发送返回参数"+sendRes);

			if (StringUtils.isNotBlank(sendRes)) {
				JSONObject obj = JSON.parseObject(sendRes);
				int status = obj.getInteger("rspCode");
				String msg=obj.getString("rspMsg");
				
				if (status == 0) {
					// 提交成功
					br.setParam(OrderStatusEnum.ORDER_WAIT, msg);
				} else {
					br.setParam(OrderStatusEnum.ORDER_FAILE, msg);
				}
			} else {
				br.setParam(OrderStatusEnum.ORDER_MANUAL, "供货商返回值为空");
			}
		} catch (Exception e) {
			br.setParam(OrderStatusEnum.ORDER_MANUAL, "发送订单异常，请找技术人员");
			LogUtils.sendExceptionLog(log, order.getId()+"商铺发送异常", e);
		}

		return br;
	}

	@Override
	public BizResponse handleQueryReq(Order order, SupplierProduct product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BizResponse handleBizCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("进入大Boss_行至远商铺回调");
		String postData = HttpUtils.getReqPostString(request, log);
		log.info("大Boss_行至远商铺回调数据:" + postData);
		String providerOrderId="";
		BizResponse br = new BizResponse();
		
		try {
			if (StringUtils.isNotBlank(postData)) {
				Map<String, Object> reqParams = JSON.parseObject(postData);
				String code = reqParams.get("status") != null ? reqParams.get("status").toString() : "";
				String msg = reqParams.get("failReason") != null ? reqParams.get("failReason").toString() : "";
				providerOrderId = reqParams.get("outTradeNo") != null ? reqParams.get("outTradeNo").toString() : "";
			
				
				if (StringUtils.equals(code, "4")) {
					br.setParam(OrderStatusEnum.ORDER_SUCCESS, msg, providerOrderId);
				} else {
					br.setParam(OrderStatusEnum.ORDER_FAILE, msg, providerOrderId);
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("OK");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			br.setParam(OrderStatusEnum.ORDER_MANUAL, "回调异常，请联系技术人员", providerOrderId);
			LogUtils.sendExceptionLog(log, providerOrderId+"发送异常", e);
		}
		
		return br;
	}

}
