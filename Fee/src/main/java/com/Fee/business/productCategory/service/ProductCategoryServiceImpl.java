package com.Fee.business.productCategory.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.common.db.CommonDao;
import com.Fee.common.nutz.NutzType;

/**
 *  @author zhuan
 */
 @Service
public class ProductCategoryServiceImpl implements ProductCategoryService{


	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public ProductCategory getProductCategory(int productInfoId){
    	return commonDao.fetch(ProductCategory.class,Cnd.where("id", NutzType.EQ.opt, productInfoId));
    }
    
    @Override
    public ProductCategory addProductCategory(ProductCategory productInfo){
   		return commonDao.insert(productInfo);
    }
   
    @Override
    public int updateProductCategory(ProductCategory productInfo){
    	return commonDao.update(productInfo);
    }
   
    @Override
    public int deleteProductCategory(int id){
    	return commonDao.delete(ProductCategory.class,id);
    }
   
    @Override
    public int deleteProductCategory(String[] ids){
    	return	commonDao.clear(ProductCategory.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public List<ProductCategory> getAll() {
		return commonDao.query(ProductCategory.class, null);
	}
}
