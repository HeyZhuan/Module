package com.Fee.business.customerChargeRecord.service;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class CustomerChargeRecordServiceImpl implements CustomerChargeRecordService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public CustomerChargeRecord getCustomerChargeRecord(int customerChargeRecordId){
    	return commonDao.fetch(CustomerChargeRecord.class,Cnd.where("id", NutzType.EQ.opt, customerChargeRecordId));
    }
    
    @Override
    public CustomerChargeRecord addCustomerChargeRecord(CustomerChargeRecord customerChargeRecord){
   		return commonDao.insert(customerChargeRecord);
    }
   
    @Override
    public int updateCustomerChargeRecord(CustomerChargeRecord customerChargeRecord){
    	return commonDao.update(customerChargeRecord);
    }
   
    @Override
    public int deleteCustomerChargeRecord(int id){
    	return commonDao.delete(CustomerChargeRecord.class,id);
    }
   
    @Override
    public int deleteCustomerChargeRecord(String[] ids){
    	return	commonDao.clear(CustomerChargeRecord.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
}
