package com.Fee.system.role.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.dao.Cnd;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.role.domain.RoleMenu;
import com.Fee.system.user.realm.UserRealm;

@Service
public class RoleServiceImpl implements RoleService {
	private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private CommonDao commonDao;
	
	@Override
	public List<Integer> getRoleMenuIds(Object obj) {
		List<RoleMenu> list =null;
		if(obj!=null){
			if(obj instanceof List){
				//List 
				if(((List) obj).size()>0){
					list = commonDao.query(RoleMenu.class, Cnd.where("roleId", NutzType.IN.opt, obj));
				}
			}else{
				//Integer
				if((Integer)obj!=0){
					list = commonDao.query(RoleMenu.class, Cnd.where("roleId", NutzType.IN.opt, obj));
				}
			}
		}
		List<Integer> menusIds = null;
		if(list!=null&&list.size()>0){
			menusIds = new ArrayList<Integer>();
			for(RoleMenu menu : list){
				menusIds.add(menu.getMenuId());
			}
		}
		return menusIds;
	}

	@Override
	public void updateRoleMenu(final int roleId,final List<Integer> newMenus) {
		Trans.exec(new Atom() {
			@Override
			public void run() {
				commonDao.clear(RoleMenu.class, Cnd.where("roleId", NutzType.EQ.opt, roleId));
				for(int menuId : newMenus){
					RoleMenu roleMenu = new RoleMenu(menuId, roleId,TimeUtils.getTimeStamp());
					commonDao.fastInsert(roleMenu);
				}
			}
		});
	}

	@Override
	public void clearUserPermissionCache(PrincipalCollection pc) {
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}

	@Override
	public void deleteRoleMenu(int menuId) {
		commonDao.clear(RoleMenu.class, Cnd.where("menuId", NutzType.EQ.opt, menuId));
	}

}
