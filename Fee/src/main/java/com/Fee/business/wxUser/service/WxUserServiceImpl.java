package com.Fee.business.wxUser.service;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.wxUser.domain.WxUser;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class WxUserServiceImpl implements WxUserService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public WxUser getWxUser(int wxUserId){
    	return commonDao.fetch(WxUser.class,Cnd.where("id", NutzType.EQ.opt, wxUserId));
    }
    
    @Override
    public WxUser addWxUser(WxUser wxUser){
   		return commonDao.insert(wxUser);
    }
   
    @Override
    public int updateWxUser(WxUser wxUser){
    	return commonDao.update(wxUser);
    }
   
    @Override
    public int deleteWxUser(int id){
    	return commonDao.delete(WxUser.class,id);
    }
   
    @Override
    public int deleteWxUser(String[] ids){
    	return	commonDao.clear(WxUser.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
}
