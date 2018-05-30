package com.Fee.business.customer.cmd;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.customer.service.CustomerService;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;
import com.Fee.common.str.StrUtils;
import com.Fee.common.time.TimeUtils;


@Controller
@RequestMapping("fee/customer")
public class CustomerCmd {
	private static Logger log = LoggerFactory.getLogger(CustomerCmd.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/customer/customerList";
	}

	/**
	 * 获取采购商信息 数据
	 */
	@RequestMapping(value = "customerList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(Customer.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加采购商信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:customer:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("action", "create");
		return "fee/customer/customerForm";
	}

	/**
	 * 添加采购商信息
	 * 
	 * @param customer
	 * @param model
	 */
	@RequiresPermissions("fee:customer:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated Customer customer) {
		customer.setAddTime(TimeUtils.getTimeStamp());
		customer.setApiKey(StrUtils.getRandomStr(30));
		customer.setBalance(0);
		Customer cus = customerService.addCustomer(customer);
		CmdCache.refreshCache(cus);
		return "success";
	}

	/**
	 * 修改采购商信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customer:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Customer customer = customerService.getCustomer(id);
		model.addAttribute("customer", customer);
		model.addAttribute("action", "update");
		return "fee/customer/customerForm";
	}

	/**
	 * 修改采购商信息
	 * 
	 * @param customer
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customer:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody Customer customer) {
		customerService.updateCustomer(customer);
		CmdCache.refreshCache(customer);
		return "success";
	}

	/**
	 * 删除采购商信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:customer:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		customerService.deleteCustomer(ids);
		CmdCache.refreshCache(ids);
		return "success";
	}
}
