package com.Fee.business.supplierAudit.service;

import java.sql.Connection;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.customerAudit.domain.CustomerAudit;
import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplierAudit.domain.SupplierAudit;
import com.Fee.business.supplierChargeRecord.domain.SupplierChargeRecord;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.user.util.UserUtil;

/**
 *  @author zhuan
 */
 @Service
public class SupplierAuditServiceImpl implements SupplierAuditService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public SupplierAudit getSupplierAudit(int supplierAuditId){
    	return commonDao.fetch(SupplierAudit.class,Cnd.where("id", NutzType.EQ.opt, supplierAuditId));
    }
    
    @Override
    public SupplierAudit addSupplierAudit(SupplierAudit supplierAudit){
    	double price = Math.abs(supplierAudit.getPrice());
    	price=supplierAudit.getType()==1?-price:price;
    	supplierAudit.setPrice(price);
    	supplierAudit.setAddTime(TimeUtils.getTimeStamp());
    	supplierAudit.setAddUser(UserUtil.getUserName());
   		return commonDao.insert(supplierAudit);
    }
   
    @Override
    public void updateSupplierAudit(final SupplierAudit supplierAudit){
	//审核,注资、授信还款正数，扣款负数
    	
    	Trans.exec(Connection.TRANSACTION_REPEATABLE_READ, new Atom() {

			@Override
			public void run() {
				
				String auditName = UserUtil.getUserName();
				
				if(supplierAudit.getAuditStatus()!=0){
					
					
					int i=commonDao.update(SupplierAudit.class, Chain.make("auditStatus", supplierAudit.getAuditStatus()).add("auditUser", auditName).add("auditTime", TimeUtils.getTimeStamp()), Cnd.where("id", NutzType.EQ.opt, supplierAudit.getId()).and("auditStatus", NutzType.EQ.opt, 0));
					
					if(i==0){
						throw new RuntimeException("该注资已审核过");
					}
					
				}else{
					throw new RuntimeException("审核类型错误");
				}
				
				
				//审核通过才进行修改余额
				if(supplierAudit.getAuditStatus()==1){
					
					//更新采购商余额
					SupplierAudit sa=commonDao.fetch(SupplierAudit.class, Cnd.where("id", NutzType.EQ.opt, supplierAudit.getId()));
					double price = sa.getPrice();
					int j = commonDao.update(Supplier.class, Chain.makeSpecial("balance", price>=0?"+"+Math.abs(price):"-"+Math.abs(price)), Cnd.where("id", NutzType.EQ.opt, sa.getSupplierId()));
					
					
					if(j==0){
						throw new RuntimeException("修改采购商余额异常");
					}
					
					SupplierChargeRecord scr=new SupplierChargeRecord(sa.getSupplierId(),sa.getType(),sa.getPrice());
					commonDao.insert(scr);
				}
				
			}
    		
    	});
    }
   
    @Override
    public int deleteSupplierAudit(int id){
    	return commonDao.delete(SupplierAudit.class,id);
    }
   
    @Override
    public int deleteSupplierAudit(String[] ids){
    	return	commonDao.clear(SupplierAudit.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
}
