package com.Fee.business.wxUser.service;

import com.Fee.business.wxUser.domain.WxUser;

/**
 *  @author zhuan
 */
public interface WxUserService {

   WxUser getWxUser(int wxUserId);
   
   WxUser addWxUser(WxUser wxUser);
   
   int updateWxUser(WxUser wxUser);
   
   int deleteWxUser(int id);
   
   int deleteWxUser(String[] ids);
}
