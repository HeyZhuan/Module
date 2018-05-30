package com.Fee.business.supplierChargeRecord.cmd;

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

import com.Fee.business.supplierChargeRecord.domain.SupplierChargeRecord;
import com.Fee.business.supplierChargeRecord.service.SupplierChargeRecordService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/supplierChargeRecord")
public class SupplierChargeRecordCmd {
	private static Logger log = LoggerFactory.getLogger(SupplierChargeRecordCmd.class);
	
	@Autowired
	private SupplierChargeRecordService supplierChargeRecordService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/supplierChargeRecord/supplierChargeRecordList";
	}

	/**
	 * 获取供货商充值记录信息 数据
	 */
	@RequestMapping(value = "supplierChargeRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(SupplierChargeRecord.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加供货商充值记录信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:supplierChargeRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		SupplierChargeRecord supplierChargeRecord = new SupplierChargeRecord();
		model.addAttribute("supplierChargeRecord", supplierChargeRecord);
		model.addAttribute("action", "create");
		return "fee/supplierChargeRecord/supplierChargeRecordForm";
	}

	/**
	 * 添加供货商充值记录信息
	 * 
	 * @param supplierChargeRecord
	 * @param model
	 */
	@RequiresPermissions("fee:supplierChargeRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated SupplierChargeRecord supplierChargeRecord) {
		supplierChargeRecordService.addSupplierChargeRecord(supplierChargeRecord);
		return "success";
	}

	/**
	 * 修改供货商充值记录信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierChargeRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		SupplierChargeRecord supplierChargeRecord = supplierChargeRecordService.getSupplierChargeRecord(id);
		model.addAttribute("supplierChargeRecord", supplierChargeRecord);
		model.addAttribute("action", "update");
		return "fee/supplierChargeRecord/supplierChargeRecordForm";
	}

	/**
	 * 修改供货商充值记录信息
	 * 
	 * @param supplierChargeRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierChargeRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody SupplierChargeRecord supplierChargeRecord) {
		supplierChargeRecordService.updateSupplierChargeRecord(supplierChargeRecord);
		return "success";
	}

	/**
	 * 删除供货商充值记录信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:supplierChargeRecord:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		supplierChargeRecordService.deleteSupplierChargeRecord(ids);
		return "success";
	}
}
