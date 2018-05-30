package com.Fee.business.supplierProduct.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nutz.dao.Cnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.order.domain.Order;
import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.db.CommonDao;
import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;

/**
 *  @author zhuan
 */
 @Service
public class SupplierProductServiceImpl implements SupplierProductService{
	 private static final Logger log = LoggerFactory.getLogger(SupplierProductServiceImpl.class);

	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public SupplierProduct getSupplierProduct(int supplierProductId){
    	return commonDao.fetch(SupplierProduct.class,Cnd.where("id", NutzType.EQ.opt, supplierProductId));
    }
    
    @Override
    public SupplierProduct addSupplierProduct(SupplierProduct supplierProduct){
    	ProductCategory cate = CmdCache.getCache(supplierProduct.getCategoryId(), new ProductCategory());
    	supplierProduct.setPrice(cate.getPrice());
    	supplierProduct.setAddTime(TimeUtils.getTimeStamp());
    	
    	if(supplierProduct.getCost()==0){
    		supplierProduct.setCost(cate.getPrice());
    	}
    	
   		return commonDao.insert(supplierProduct);
    }
   
    @Override
    public int updateSupplierProduct(SupplierProduct supplierProduct){
    	return commonDao.update(supplierProduct);
    }
   
    @Override
    public int deleteSupplierProduct(int id){
    	return commonDao.delete(SupplierProduct.class,id);
    }
   
    @Override
    public int deleteSupplierProduct(String[] ids){
    	return	commonDao.clear(SupplierProduct.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public SupplierProduct getChargeSupplierProduct(Order order) {
		
		/**
		 * 获取供货商产品逻辑
		 * 1、根据采购商产品大类获取对应的供货商产品大类
		 * 2、排序，优先级越大排越前，成本越低排越前
		 */
		CustomerProduct cup = CmdCache.getCache(order.getCusProductId(), new CustomerProduct());
		List<SupplierProduct> sups = commonDao.query(SupplierProduct.class, Cnd.where("status", NutzType.EQ.opt, 1).and("categoryId", NutzType.EQ.opt, cup.getCategoryId()));
		
		Collections.sort(sups, new Comparator<SupplierProduct>() {

			@Override
			public int compare(SupplierProduct s1, SupplierProduct s2) {
				 int flag= (s1.getPriority() == s2.getPriority() ? 0 : (s1.getPriority() > s2.getPriority()?-1:1));
				 if(flag==0){
					  double s1Cost = s1.getCost();
					  double s2Cost = s2.getCost();
					  int rs = (s1Cost == s2Cost?0:(s1Cost > s2Cost?1:-1));
					  return rs;
				 }else{
				   return flag;
				 }  
			}
			
		});
		
		StringBuilder sb=new StringBuilder(order.getId()+"_获取到的供货商产品:");
		for (SupplierProduct sup : sups) {
			sb.append(sup.getId()+"_"+sup.getName()+",");
		}
		LogUtils.sendLog(log, order.getId(), sb.toString());
		
		for (SupplierProduct sup : sups) {
			
			Supplier supplier = CmdCache.getCache(sup.getSupplierId(), new Supplier());
			if(supplier.getStatus()==1){
				return sup;
			}
		}
		
		return null;
	}
}
