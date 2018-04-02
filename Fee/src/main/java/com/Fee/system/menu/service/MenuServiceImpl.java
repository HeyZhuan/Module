package com.Fee.system.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.service.BaseService;
import com.Fee.system.menu.domain.Menu;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private CommonDao commonDAO;
	
	@Override
	public List<Menu> getMenus(Object obj) {
		Cnd cnd = null;
		List<Menu> menus=null;
		if(obj!=null){
			cnd = Cnd.where("id", NutzType.IN.opt, obj);
			menus = commonDAO.query(Menu.class, cnd);
		}else{
			menus = new ArrayList<>();
		}
		
		return menus;
	}
	
	@Override
	public List<Menu> getMenus() {
		List<Menu> menus = commonDAO.query(Menu.class,null);
		return menus!=null &&  menus.size()>0 ? menus : null;
	}
	
	@Override
	public void addMenu(Menu menu) {
		commonDAO.insert(menu);
	}
	
	@Override
	public int updateMenu(Menu menu) {
		return commonDAO.updateIgnoreNull(menu);
	}
	@Override
	public Menu getIdByNameAndPid(String name, int pid){
		Menu fetch = commonDAO.fetch(Menu.class,  Cnd.where("name", NutzType.LIKE.opt, name).and("pid", NutzType.EQ.opt, pid));
		return fetch;
	}
}
