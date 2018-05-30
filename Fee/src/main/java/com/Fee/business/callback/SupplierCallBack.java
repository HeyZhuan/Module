package com.Fee.business.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.order.service.OrderService;
import com.Fee.business.work.WorkService;
/**
 * 供应商回调入口
 * @author Zhuan
 *
 */
@Controller
public class SupplierCallBack {

	
	@Autowired
	private OrderService orderService;
	@Autowired
	private WorkService workService;
	
	/**
	 * cx 回调
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{simpleName}/call_back.do")
	public void callback(@PathVariable("simpleName") String simpleName,HttpServletRequest request,HttpServletResponse response){
		BizResponse callback = workService.excute(null,null,request, response,simpleName);
		orderService.dealOrder(callback);
	}
}
