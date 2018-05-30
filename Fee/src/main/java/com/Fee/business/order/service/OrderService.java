package com.Fee.business.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.order.domain.Order;
import com.Fee.common.enums.OrderPayMentStatusEnum;
import com.Fee.common.enums.OrderStatusEnum;
import com.alibaba.fastjson.JSONObject;

/**
 *  @author zhuan
 */
public interface OrderService {

   Order getOrder(int orderId);
   
   Order addOrder(Order order);
   
   int updateOrder(Order order);
   
   int deleteOrder(int id);
   
   int deleteOrder(String[] ids);
   
   /**
	 * 生成订单
	 * @param openId
	 * @param wxOpenId
	 * @param productId
	 * @param phone
	 * @return
	 */
	Order saveOrder(String openId,CustomerProduct product,String phone);
	
	
	/**
	 * 更新订单支付状态以及微信订单号
	 * @param status
	 * @param wxOrderId
	 * @return
	 */
	int updateOrderPayMentStatusAndWxOrder(OrderPayMentStatusEnum payMentStataus,String wxOrderId,String orderId,String failMsg);
	
	/**
	 * 更改订单状态
	 * @param orderstatus
	 * @param orderId
	 * @param biz
	 * @return
	 */
	int updateOrderStatusById(OrderStatusEnum orderstatus, int orderId, String failMsg);
	
	/**
	 * 充值操作
	 * @param order
	 * @return
	 */
	BizResponse charge(Order order);
	
	/**
	 * 处理供货商 返回的订单状态
	 * @param biz
	 */
	int dealOrder(BizResponse biz);
	
	
	List<JSONObject> getOrderByPhoneAndOpenId(String openId,String phone);
	
	/**
	 * 检查号码
	 * @param phone
	 * @return
	 */
	String checkPhone(String phone);
	
	/**
	 * 下载订单
	 * @param request
	 * @param response
	 */
	void download(List<Order> orders,HttpServletRequest request,HttpServletResponse response);
	
}
