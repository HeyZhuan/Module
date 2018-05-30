package com.Fee.business.customerAudit.cmd;

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

import com.Fee.business.customerAudit.domain.CustomerAudit;
import com.Fee.business.customerAudit.service.CustomerAuditService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/customerAudit")
public class CustomerAuditCmd {
	private static Logger log = LoggerFactory.getLogger(CustomerAuditCmd.class);
	
	@Autowired
	private CustomerAuditService customerAuditService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/customerAudit/customerAuditList";
	}

	/**
	 * 获取采购商注资审核信息 数据
	 */
	@RequestMapping(value = "customerAuditList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(CustomerAudit.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加采购商注资审核信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:customerAudit:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		CustomerAudit customerAudit = new CustomerAudit();
		model.addAttribute("customerAudit", customerAudit);
		model.addAttribute("action", "create");
		return "fee/customerAudit/customerAuditForm";
	}

	/**
	 * 添加采购商注资审核信息
	 * 
	 * @param customerAudit
	 * @param model
	 */
	@RequiresPermissions("fee:customerAudit:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated CustomerAudit customerAudit) {
		customerAuditService.addCustomerAudit(customerAudit);
		return "success";
	}

	/**
	 * 修改采购商注资审核信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerAudit:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CustomerAudit customerAudit = customerAuditService.getCustomerAudit(id);
		model.addAttribute("customerAudit", customerAudit);
		model.addAttribute("action", "update");
		return "fee/customerAudit/customerAuditForm";
	}

	/**
	 * 修改采购商注资审核信息
	 * 
	 * @param customerAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerAudit:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody CustomerAudit customerAudit) {
		try {
			customerAuditService.updateCustomerAudit(customerAudit);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "success";
	}

	/**
	 * 删除采购商注资审核信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:customerAudit:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		customerAuditService.deleteCustomerAudit(ids);
		return "success";
	}
}
