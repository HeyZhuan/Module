package com.Fee.business.customerAudit.service;

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
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.user.util.UserUtil;

/**
 *  @author zhuan
 */
 @Service
public class CustomerAuditServiceImpl implements CustomerAuditService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public CustomerAudit getCustomerAudit(int customerAuditId){
    	return commonDao.fetch(CustomerAudit.class,Cnd.where("id", NutzType.EQ.opt, customerAuditId));
    }
    
    @Override
    public CustomerAudit addCustomerAudit(CustomerAudit customerAudit){
    	
    	double price = Math.abs(customerAudit.getPrice());
    	price=customerAudit.getType()==1?-price:price;
    	customerAudit.setPrice(price);
    	customerAudit.setAddTime(TimeUtils.getTimeStamp());
    	customerAudit.setAddUser(UserUtil.getUserName());
    	
   		return commonDao.insert(customerAudit);
    }
   
    @Override
    public void updateCustomerAudit(final CustomerAudit customerAudit){
    	//审核,注资、授信还款正数，扣款负数
    	
    	Trans.exec(Connection.TRANSACTION_REPEATABLE_READ, new Atom() {

			@Override
			public void run() {
				
				String auditName = UserUtil.getCurrentUser().getName();
				
				if(customerAudit.getAuditStatus()!=0){
					
					
					int i=commonDao.update(CustomerAudit.class, Chain.make("auditStatus", customerAudit.getAuditStatus()).add("auditUser", auditName).add("auditTime", TimeUtils.getTimeStamp()), Cnd.where("id", NutzType.EQ.opt, customerAudit.getId()).and("auditStatus", NutzType.EQ.opt, 0));
					
					if(i==0){
						throw new RuntimeException("该注资已审核过");
					}
					
				}else{
					throw new RuntimeException("审核类型错误");
				}
				
				
				//审核通过才进行修改余额
				if(customerAudit.getAuditStatus()==1){
					
					//更新采购商余额
					CustomerAudit ca=commonDao.fetch(CustomerAudit.class, Cnd.where("id", NutzType.EQ.opt, customerAudit.getId()));
					double price = ca.getPrice();
					int j = commonDao.update(Customer.class, Chain.makeSpecial("balance", price>=0?"+"+Math.abs(price):"-"+Math.abs(price)), Cnd.where("id", NutzType.EQ.opt, ca.getCustomerId()));
					
					
					if(j==0){
						throw new RuntimeException("修改采购商余额异常");
					}
					
					CustomerChargeRecord ccr=new CustomerChargeRecord(ca.getCustomerId(),ca.getPrice(),ca.getType());
					commonDao.insert(ccr);
				}
				
			}
    		
    	});
    }
   
    @Override
    public int deleteCustomerAudit(int id){
    	return commonDao.delete(CustomerAudit.class,id);
    }
   
    @Override
    public int deleteCustomerAudit(String[] ids){
    	return	commonDao.clear(CustomerAudit.class, Cnd.where("id", NutzType.IN.opt, ids));
    }
}
