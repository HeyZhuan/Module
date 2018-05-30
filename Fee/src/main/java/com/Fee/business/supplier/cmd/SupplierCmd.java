package com.Fee.business.supplier.cmd;

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

import com.Fee.business.logInfo.service.LogInfoService;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplier.service.SupplierService;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/supplier")
public class SupplierCmd {
	private static Logger log = LoggerFactory.getLogger(SupplierCmd.class);
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private BaseService baseService;
	@Autowired
	private LogInfoService logService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/supplier/supplierList";
	}

	/**
	 * 获取供应商信息 数据
	 */
	@RequestMapping(value = "supplierList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
		Cnd cnd = ParamUtils.getCnd(filterParamMap);
		int[] param = ParamUtils.getPageParam(request);
		Pager pager = baseService.creatPager(param[0], param[1]);
		QueryResult result = baseService.get(Supplier.class, cnd, pager);
		return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加供应商信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:supplier:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		model.addAttribute("action", "create");
		return "fee/supplier/supplierForm";
	}

	/**
	 * 添加供应商信息
	 * 
	 * @param supplier
	 * @param model
	 */
	@RequiresPermissions("fee:supplier:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated Supplier supplier) {
		Supplier s = supplierService.addSupplier(supplier);
		CmdCache.refreshCache(s);
		return "success";
	}

	/**
	 * 修改供应商信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplier:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Supplier supplier = supplierService.getSupplier(id);
		model.addAttribute("supplier", supplier);
		model.addAttribute("action", "update");
		return "fee/supplier/supplierForm";
	}

	/**
	 * 修改供应商信息
	 * 
	 * @param supplier
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplier:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody Supplier supplier) {
		supplierService.updateSupplier(supplier);
		CmdCache.refreshCache(supplier);
		return "success";
	}

	/**
	 * 删除供应商信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:supplier:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		supplierService.deleteSupplier(ids);
		CmdCache.refreshCache(ids);
		return "success";
	}
}
