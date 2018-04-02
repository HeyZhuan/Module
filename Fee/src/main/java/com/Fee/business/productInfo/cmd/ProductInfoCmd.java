package com.Fee.business.productInfo.cmd;

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
import com.Fee.business.productInfo.domain.ProductInfo;
import com.Fee.business.productInfo.service.ProductInfoService;
import com.Fee.common.enums.ContentTypeEnum;
import com.Fee.common.enums.WorkTypeEnum;
import com.Fee.common.param.ParamUtils;
import com.Fee.common.service.BaseService;


@Controller
@RequestMapping("wareHouses/productInfo")
public class ProductInfoCmd {
	private static Logger log = LoggerFactory.getLogger(ProductInfoCmd.class);
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private BaseService baseService;
	@Autowired
	private LogInfoService logService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "wareHouses/productInfo/productInfoList";
	}

	/**
	 * 获取产品信息 数据
	 */
	@RequestMapping(value = "productInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> filterParamMap = ParamUtils.getParametersStartingWith(request);
		Cnd cnd = ParamUtils.getCnd(filterParamMap);
		int[] param = ParamUtils.getPageParam(request);
		Pager pager = baseService.creatPager(param[0], param[1]);
		QueryResult result = baseService.get(ProductInfo.class, cnd, pager);
		return ParamUtils.getEasyUIData(result);
	}

	/**
	 * 添加产品信息跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("wareHouses:productInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		ProductInfo productInfo = new ProductInfo();
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("action", "create");
		return "wareHouses/productInfo/productInfoForm";
	}

	/**
	 * 添加产品信息
	 * 
	 * @param productInfo
	 * @param model
	 */
	@RequiresPermissions("wareHouses:productInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Validated ProductInfo productInfo) {
		productInfoService.addProductInfo(productInfo);
		logService.addLog(WorkTypeEnum.ADD, ContentTypeEnum.PRODUCT, productInfo);
		return "success";
	}

	/**
	 * 修改产品信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wareHouses:productInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		ProductInfo productInfo = productInfoService.getProductInfo(id);
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("action", "update");
		return "wareHouses/productInfo/productInfoForm";
	}

	/**
	 * 修改产品信息
	 * 
	 * @param productInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wareHouses:productInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Validated @ModelAttribute @RequestBody ProductInfo productInfo) {
		productInfoService.updateProductInfo(productInfo);
		logService.addLog(WorkTypeEnum.UPDATE, ContentTypeEnum.PRODUCT, productInfo);
		return "success";
	}

	/**
	 * 删除产品信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("wareHouses:productInfo:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String[] ids) {
		productInfoService.deleteProductInfo(ids);
		logService.addLog(ProductInfo.class,WorkTypeEnum.DELETE, ContentTypeEnum.CATE, ids);
		return "success";
	}
}
