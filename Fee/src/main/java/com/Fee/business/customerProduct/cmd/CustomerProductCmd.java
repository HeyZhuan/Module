package com.Fee.business.customerProduct.cmd;

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

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.customerProduct.service.CustomerProductService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/customerProduct")
public class CustomerProductCmd {
	private static Logger log = LoggerFactory.getLogger(CustomerProductCmd.class);
	
	@Autowired
	private CustomerProductService customerProductService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/customerProduct/customerProductList";
	}

	/**
	 * 获取采购商产品信息 数据
	 */
	@RequestMapping(value = "customerProductList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(CustomerProduct.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加采购商产品信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:customerProduct:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		CustomerProduct customerProduct = new CustomerProduct();
		model.addAttribute("customerProduct", customerProduct);
		model.addAttribute("action", "create");
		return "fee/customerProduct/customerProductForm";
	}

	/**
	 * 添加采购商产品信息
	 * 
	 * @param customerProduct
	 * @param model
	 */
	@RequiresPermissions("fee:customerProduct:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated CustomerProduct customerProduct) {
		customerProductService.addCustomerProduct(customerProduct);
		return "success";
	}

	/**
	 * 修改采购商产品信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerProduct:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CustomerProduct customerProduct = customerProductService.getCustomerProduct(id);
		model.addAttribute("customerProduct", customerProduct);
		model.addAttribute("action", "update");
		return "fee/customerProduct/customerProductForm";
	}

	/**
	 * 修改采购商产品信息
	 * 
	 * @param customerProduct
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerProduct:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody CustomerProduct customerProduct) {
		customerProductService.updateCustomerProduct(customerProduct);
		return "success";
	}

	/**
	 * 删除采购商产品信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:customerProduct:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		customerProductService.deleteCustomerProduct(ids);
		return "success";
	}
}
