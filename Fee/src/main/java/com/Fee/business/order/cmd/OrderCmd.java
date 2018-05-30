package com.Fee.business.order.cmd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.business.order.domain.Order;
import com.Fee.business.order.service.OrderService;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/order")
public class OrderCmd {
	private static Logger log = LoggerFactory.getLogger(OrderCmd.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/order/orderList";
	}

	/**
	 * 获取订单信息 数据
	 */
	@RequestMapping(value = "orderList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
		Cnd cnd = ParamUtils.getCnd(filterParamMap);
		int[] param = ParamUtils.getPageParam(request);
		Pager pager = baseService.creatPager(param[0], param[1]);
		QueryResult result = baseService.get(Order.class, cnd, pager);
		return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:order:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Order order = new Order();
		model.addAttribute("order", order);
		model.addAttribute("action", "create");
		return "fee/order/orderForm";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param order
	 * @param model
	 */
	@RequiresPermissions("fee:order:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated Order order) {
		orderService.addOrder(order);
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:order:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Order order = orderService.getOrder(id);
		model.addAttribute("order", order);
		model.addAttribute("action", "update");
		return "fee/order/orderForm";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:order:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody Order order) {
		orderService.updateOrder(order);
		return "success";
	}

	/**
	 * 删除订单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:order:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		orderService.deleteOrder(ids);
		return "success";
	}
	
	
	/**
	 * 删除订单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object>  filterParamMap= HttpUtils.getReqParams(request.getQueryString());
		Cnd cnd = ParamUtils.getCnd(filterParamMap);
		List<Order> orders = baseService.get(Order.class, cnd);
		orderService.download(orders, request, response);
	}
}
