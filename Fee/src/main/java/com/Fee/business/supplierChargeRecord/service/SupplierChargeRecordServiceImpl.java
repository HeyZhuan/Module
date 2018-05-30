package com.Fee.business.supplierChargeRecord.service;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.supplierChargeRecord.domain.SupplierChargeRecord;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class SupplierChargeRecordServiceImpl implements SupplierChargeRecordService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public SupplierChargeRecord getSupplierChargeRecord(int supplierChargeRecordId){
    	return commonDao.fetch(SupplierChargeRecord.class,Cnd.where("id", NutzType.EQ.opt, supplierChargeRecordId));
    }
    
    @Override
    public SupplierChargeRecord addSupplierChargeRecord(SupplierChargeRecord supplierChargeRecord){
   		return commonDao.insert(supplierChargeRecord);
    }
   
    @Override
    public int updateSupplierChargeRecord(SupplierChargeRecord supplierChargeRecord){
    	return commonDao.update(supplierChargeRecord);
    }
   
    @Override
    public int deleteSupplierChargeRecord(int id){
    	return commonDao.delete(SupplierChargeRecord.class,id);
    }
   
    @Override
    public int deleteSupplierChargeRecord(String[] ids){
    	return	commonDao.clear(SupplierChargeRecord.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
}
