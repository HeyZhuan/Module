package com.Fee.business.productCategory.service;

import java.util.List;

import com.Fee.business.productCategory.domain.ProductCategory;

/**
 *  @author zhuan
 */
public interface ProductCategoryService {

   ProductCategory getProductCategory(int productInfoId);
   
   ProductCategory addProductCategory(ProductCategory productInfo);
   
   int updateProductCategory(ProductCategory productInfo);
   
   int deleteProductCategory(int id);
   
   int deleteProductCategory(String[] ids);
   
   List<ProductCategory> getAll();
}
