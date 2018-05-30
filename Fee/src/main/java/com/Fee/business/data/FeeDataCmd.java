package com.Fee.business.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.common.db.CommonDao;
import com.Fee.common.log.LogUtils;
import com.Fee.common.service.BaseService;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.menu.domain.Menu;
import com.Fee.system.role.domain.RoleMenu;

/**
 * REST 入口
 * @author Zhuan
 *
 */
@Controller
@RequestMapping("data")
public class FeeDataCmd {

	private static Logger log = LoggerFactory.getLogger(FeeDataCmd.class);
	
	
	@Autowired
	private BaseService baseService;
	@Autowired
	private CommonDao dao;
	
	
	/**
	 * 删除菜单
	 */
	@RequestMapping(value = "work", method = RequestMethod.GET)
	@ResponseBody
	public void work() {
		
		//添加产品大类,慢充话费,10,50,100,500,1000
		int[] sizes=new int[]{10,50,100,500,1000};
		List<ProductCategory> cates=new ArrayList<>();
		for (int i : sizes) {
			
			ProductCategory cate=new ProductCategory();
			cate.setArea(1);
			cate.setName("广东移动慢充"+i+"元");
			cate.setProvince("广东");
			cate.setOperator("移动");
			cate.setSize(i);
			cate.setUnit(2);
			cate.setPrice(i);
			cate.setType(2);
			cates.add(cate);
		}
		
		dao.fastInsert(cates);
	
	}
	
	/**
	 * 添加菜单
	 */
	@RequestMapping(value = "addMenu", method = RequestMethod.GET)
	@ResponseBody
	public void addMenu(int pid,String name,String className) {
		
		Menu menu=new Menu();
		menu.setPid(pid);
		menu.setName(name);
		menu.setType(0);
		menu.setUrl("fee/"+className);
		menu.setStatus(1);
		menu.setAddTime(TimeUtils.getTimeStamp());
		
		RoleMenu rm=new RoleMenu();
		rm.setRoleId(1);
		rm.setAddTime(TimeUtils.getTimeStamp());
		Menu m1 = baseService.save(menu);
		rm.setMenuId(m1.getId());
		baseService.save(rm);
		
		//添加、修改、删除
		Menu m2=new Menu();
		m2.setPid(m1.getId());
		m2.setName("添加");
		m2.setType(1);
		m2.setUrl("fee:"+className+":add");
		m2.setStatus(1);
		m2.setAddTime(TimeUtils.getTimeStamp());
		Menu m2s = baseService.save(m2);
		rm.setMenuId(m2s.getId());
		baseService.save(rm);
		
		m2.setUrl("fee:"+className+":update");
		m2.setName("修改");
		m2s=baseService.save(m2);
		rm.setMenuId(m2s.getId());
		baseService.save(rm);
		
		m2.setUrl("fee:"+className+":delete");
		m2.setName("删除");
		m2s=baseService.save(m2);
		rm.setMenuId(m2s.getId());
		baseService.save(rm);
	}

	
	/**
	 * 添加菜单
	 */
	@RequestMapping(value = "createTable", method = RequestMethod.GET)
	@ResponseBody
	public void createTable(String className) {
		try {
			
			Class<?> clazz = Class.forName(className);
			dao.create(clazz, true);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "", e);
		}
	}
}
