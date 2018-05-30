package com.Fee.business.supplier.service;

import java.sql.Connection;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;
import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord.CustomerChargeRecordEnum;
import com.Fee.business.order.domain.Order;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplierChargeRecord.domain.SupplierChargeRecord;
import com.Fee.business.supplierChargeRecord.service.SupplierChargeRecordService;
import com.Fee.common.db.CommonDao;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;

/**
 *  @author zhuan
 */
 @Service
public class SupplierServiceImpl implements SupplierService{


	@Autowired
	private CommonDao commonDao;
	@Autowired
	private SupplierChargeRecordService scrService;
    
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

	@Override
	public void consume(final Order order) {
		Trans.exec(Connection.TRANSACTION_REPEATABLE_READ, new Atom() {

			@Override
			public void run() {
				
				//扣减供货商余额
				int res = commonDao.update(Supplier.class, Chain.makeSpecial("balance", "-"+order.getPrice()), Cnd.where("id", NutzType.EQ.opt, order.getCustomerId()));
				if(res==0){
					throw new RuntimeException("供货商扣减余额异常");
				}
				
				//添加对应采购商充值记录
				SupplierChargeRecord ccr=new SupplierChargeRecord();
				ccr.setAddTime(TimeUtils.getTimeStamp());
				ccr.setSupplierId(order.getSupplierId());
				ccr.setOrderId(order.getId());
				ccr.setPrice(order.getPrice());
				ccr.setType(CustomerChargeRecordEnum.CHARGE_KOUKUAN.opt);

				SupplierChargeRecord ccr1 = scrService.addSupplierChargeRecord(ccr);
				if(ccr1==null || ccr1.getId()==0){
					throw new RuntimeException("供货商充值记录添加失败,ccr:"+JsonUtils.obj2Json(ccr));
				}
			}
    		
    	});
		
	}
    
    
}
