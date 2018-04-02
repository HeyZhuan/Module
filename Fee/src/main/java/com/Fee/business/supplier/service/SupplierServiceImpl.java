package com.Fee.business.supplier.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.supplier.domain.Supplier;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class SupplierServiceImpl implements SupplierService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public Supplier getSupplier(int supplierId){
    	return commonDao.fetch(Supplier.class,Cnd.where("id", NutzType.EQ.opt, supplierId));
    }
    
    @Override
    public Supplier addSupplier(Supplier supplier){
   		return commonDao.insert(supplier);
    }
   
    @Override
    public int updateSupplier(Supplier supplier){
		if(supplier.getId()==1){
			//该供货商为总公司的信息，只允许修改备注和名字
			return commonDao.update(Supplier.class, Chain.make("remark", supplier.getRemark()).add("name", supplier.getName()), Cnd.where("id", NutzType.EQ.opt, 1));
		}
    	return commonDao.update(supplier);
    }
   
    @Override
    public int deleteSupplier(int id){
    	return commonDao.delete(Supplier.class,id);
    }
   
    @Override
    public int deleteSupplier(String[] ids){
    	return	commonDao.clear(Supplier.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
    
    @Override
	public List<Supplier> getAll() {
		return commonDao.query(Supplier.class, null);
	}
}
