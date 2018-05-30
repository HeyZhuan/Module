package com.Fee.business.supplierAudit.cmd;

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

import com.Fee.business.supplierAudit.domain.SupplierAudit;
import com.Fee.business.supplierAudit.service.SupplierAuditService;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/supplierAudit")
public class SupplierAuditCmd {
	private static Logger log = LoggerFactory.getLogger(SupplierAuditCmd.class);
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/supplierAudit/supplierAuditList";
	}

	/**
	 * 获取供货商注资审核信息 数据
	 */
	@RequestMapping(value = "supplierAuditList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(SupplierAudit.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加供货商注资审核信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:supplierAudit:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		SupplierAudit supplierAudit = new SupplierAudit();
		model.addAttribute("supplierAudit", supplierAudit);
		model.addAttribute("action", "create");
		return "fee/supplierAudit/supplierAuditForm";
	}

	/**
	 * 添加供货商注资审核信息
	 * 
	 * @param supplierAudit
	 * @param model
	 */
	@RequiresPermissions("fee:supplierAudit:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated SupplierAudit supplierAudit) {
		supplierAuditService.addSupplierAudit(supplierAudit);
		return "success";
	}

	/**
	 * 修改供货商注资审核信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierAudit:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		SupplierAudit supplierAudit = supplierAuditService.getSupplierAudit(id);
		model.addAttribute("supplierAudit", supplierAudit);
		model.addAttribute("action", "update");
		return "fee/supplierAudit/supplierAuditForm";
	}

	/**
	 * 修改供货商注资审核信息
	 * 
	 * @param supplierAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierAudit:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody SupplierAudit supplierAudit) {
		supplierAuditService.updateSupplierAudit(supplierAudit);
		return "success";
	}

	/**
	 * 删除供货商注资审核信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:supplierAudit:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		supplierAuditService.deleteSupplierAudit(ids);
		return "success";
	}
}
