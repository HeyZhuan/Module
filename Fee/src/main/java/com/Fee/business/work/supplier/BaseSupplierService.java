package com.Fee.business.work.supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.order.domain.Order;
import com.Fee.business.supplierProduct.domain.SupplierProduct;

/**
 * 发送供货商统一接口
 * @author Zhuan
 *
 */
public interface BaseSupplierService {

	
	/**
	 * 业务充值请求入口
	 * @param supplierApiReq
	 * @return
	 */
	public BizResponse handleBizReq(Order order,SupplierProduct product);
	
	/**
	 * 业务查询请求入口
	 * @param supplierApiReq
	 * @return
	 */
	public BizResponse handleQueryReq(Order order,SupplierProduct product);
	
	
	/**
	 * 业务回调入口
	 * @param request
	 * @param response
	 */
	public BizResponse handleBizCallback(HttpServletRequest request,HttpServletResponse response);
	
	
}
