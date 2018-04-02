package com.Fee.business.productInfo.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.productInfo.domain.ProductInfo;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class ProductInfoServiceImpl implements ProductInfoService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public ProductInfo getProductInfo(int productInfoId){
    	return commonDao.fetch(ProductInfo.class,Cnd.where("id", NutzType.EQ.opt, productInfoId));
    }
    
    @Override
    public ProductInfo addProductInfo(ProductInfo productInfo){
   		return commonDao.insert(productInfo);
    }
   
    @Override
    public int updateProductInfo(ProductInfo productInfo){
    	return commonDao.update(productInfo);
    }
   
    @Override
    public int deleteProductInfo(int id){
    	return commonDao.delete(ProductInfo.class,id);
    }
   
    @Override
    public int deleteProductInfo(String[] ids){
    	return	commonDao.clear(ProductInfo.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public List<ProductInfo> getAll() {
		return commonDao.query(ProductInfo.class, null);
	}
}
