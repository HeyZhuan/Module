package com.Fee.business.supplierProduct.cmd;

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

import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.business.supplierProduct.service.SupplierProductService;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("fee/supplierProduct")
public class SupplierProductCmd {
	private static Logger log = LoggerFactory.getLogger(SupplierProductCmd.class);
	
	@Autowired
	private SupplierProductService supplierProductService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "fee/supplierProduct/supplierProductList";
	}

	/**
	 * 获取供应商产品信息 数据
	 */
	@RequestMapping(value = "supplierProductList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
			Cnd cnd = ParamUtils.getCnd(filterParamMap);
			int[] param = ParamUtils.getPageParam(request);
			Pager pager = baseService.creatPager(param[0], param[1]);
			QueryResult result = baseService.get(SupplierProduct.class, cnd, pager);
			return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加供应商产品信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("fee:supplierProduct:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		SupplierProduct supplierProduct = new SupplierProduct();
		model.addAttribute("supplierProduct", supplierProduct);
		model.addAttribute("action", "create");
		return "fee/supplierProduct/supplierProductForm";
	}

	/**
	 * 添加供应商产品信息
	 * 
	 * @param supplierProduct
	 * @param model
	 */
	@RequiresPermissions("fee:supplierProduct:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated SupplierProduct supplierProduct) {
		SupplierProduct sp = supplierProductService.addSupplierProduct(supplierProduct);
		CmdCache.refreshCache(sp);
		return "success";
	}

	/**
	 * 修改供应商产品信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierProduct:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		SupplierProduct supplierProduct = supplierProductService.getSupplierProduct(id);
		model.addAttribute("supplierProduct", supplierProduct);
		model.addAttribute("action", "update");
		return "fee/supplierProduct/supplierProductForm";
	}

	/**
	 * 修改供应商产品信息
	 * 
	 * @param supplierProduct
	 * @param model
	 * @return
	 */
	@RequiresPermissions("fee:supplierProduct:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody SupplierProduct supplierProduct) {
		supplierProductService.updateSupplierProduct(supplierProduct);
		CmdCache.refreshCache(supplierProduct);
		return "success";
	}

	/**
	 * 删除供应商产品信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("fee:supplierProduct:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		supplierProductService.deleteSupplierProduct(ids);
		CmdCache.refreshCache(ids);
		return "success";
	}
}
