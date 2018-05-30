package com.Fee.business.customer.service;

import java.sql.Connection;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord.CustomerChargeRecordEnum;
import com.Fee.business.customerChargeRecord.service.CustomerChargeRecordService;
import com.Fee.business.order.domain.Order;
import com.Fee.common.db.CommonDao;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.service.UserService;

/**
 *  @author zhuan
 */
 @Service
public class CustomerServiceImpl implements CustomerService{


	@Autowired
	private CommonDao commonDao;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerChargeRecordService ccrService;
    
    @Override
   	public Customer getCustomer(int customerId){
    	return commonDao.fetch(Customer.class,Cnd.where("id", NutzType.EQ.opt, customerId));
    }
    
    @Override
    public Customer addCustomer(Customer customer){
    	
    	User user=new User(customer);
    	userService.addUser(user);
		userService.addUserRole(user.getId(), 3);//TODO 采购商的角色固定为3
   		return commonDao.insert(customer);
    }
   
    @Override
    public int updateCustomer(Customer customer){
    	return commonDao.update(customer);
    }
   
    @Override
    public int deleteCustomer(int id){
    	return commonDao.delete(Customer.class,id);
    }
   
    @Override
    public int deleteCustomer(String[] ids){
    	return	commonDao.clear(Customer.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public void consume(final Order order) {
		
    	Trans.exec(Connection.TRANSACTION_REPEATABLE_READ, new Atom() {

			@Override
			public void run() {
				
				//扣减采购商余额
				int res = commonDao.update(Customer.class, Chain.makeSpecial("balance", "-"+order.getPrice()), Cnd.where("id", NutzType.EQ.opt, order.getCustomerId()).and("balance", NutzType.GE.opt, order.getPrice()));
				if(res==0){
					throw new RuntimeException("采购商余额不足");
				}
				
				//添加对应采购商充值记录
				CustomerChargeRecord ccr=new CustomerChargeRecord();
				ccr.setAddTime(TimeUtils.getTimeStamp());
				ccr.setCustomerId(order.getCustomerId());
				ccr.setOrderId(order.getId()+"");
				ccr.setPrice(order.getPrice());
				ccr.setType(CustomerChargeRecordEnum.CHARGE_KOUKUAN.opt);

				CustomerChargeRecord ccr1 = ccrService.addCustomerChargeRecord(ccr);
				if(ccr1==null || ccr1.getId()==0){
					throw new RuntimeException("采购商充值记录添加失败,ccr:"+JsonUtils.obj2Json(ccr));
				}
			}
    		
    	});
	}
}
