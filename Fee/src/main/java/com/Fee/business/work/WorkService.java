package com.Fee.business.work;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.order.domain.Order;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.business.work.supplier.bigboss.service.BigBossServiceImpl;

/**
 * 处理订单发送及回调、查询接口
 * @author Zhuan
 *
 */
@Service
public class WorkService {

	@Autowired
	private BigBossServiceImpl bigBoss;
	
	public BizResponse excute(Order order,SupplierProduct product,HttpServletRequest request,HttpServletResponse response,String simpleName){
		
		BizResponse biz=null;
		switch (simpleName) {
		case "BIGBOSS"://BigBoss
			biz=order!=null?bigBoss.handleBizReq(order, product):bigBoss.handleBizCallback(request, response);
			break;

		default:
			break;
		}
		return biz;
	}
	
}
