package com.Fee.business.customerChargeRecord.cmd;

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

import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.business.customerChargeRecord.service.CustomerChargeRecordService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/customerChargeRecord")
public class CustomerChargeRecordCmd {
	private static Logger log = LoggerFactory.getLogger(CustomerChargeRecordCmd.class);
	
	@Autowired
	private CustomerChargeRecordService customerChargeRecordService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/customerChargeRecord/customerChargeRecordList";
	}

	/**
	 * 获取采购商充值信息记录 数据
	 */
	@RequestMapping(value = "customerChargeRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(CustomerChargeRecord.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加采购商充值信息记录跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:customerChargeRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		CustomerChargeRecord customerChargeRecord = new CustomerChargeRecord();
		model.addAttribute("customerChargeRecord", customerChargeRecord);
		model.addAttribute("action", "create");
		return "fee/customerChargeRecord/customerChargeRecordForm";
	}

	/**
	 * 添加采购商充值信息记录
	 * 
	 * @param customerChargeRecord
	 * @param model
	 */
	@RequiresPermissions("fee:customerChargeRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated CustomerChargeRecord customerChargeRecord) {
		customerChargeRecordService.addCustomerChargeRecord(customerChargeRecord);
		return "success";
	}

	/**
	 * 修改采购商充值信息记录跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerChargeRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CustomerChargeRecord customerChargeRecord = customerChargeRecordService.getCustomerChargeRecord(id);
		model.addAttribute("customerChargeRecord", customerChargeRecord);
		model.addAttribute("action", "update");
		return "fee/customerChargeRecord/customerChargeRecordForm";
	}

	/**
	 * 修改采购商充值信息记录
	 * 
	 * @param customerChargeRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:customerChargeRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody CustomerChargeRecord customerChargeRecord) {
		customerChargeRecordService.updateCustomerChargeRecord(customerChargeRecord);
		return "success";
	}

	/**
	 * 删除采购商充值信息记录
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:customerChargeRecord:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		customerChargeRecordService.deleteCustomerChargeRecord(ids);
		return "success";
	}
}
