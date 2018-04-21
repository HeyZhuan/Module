package com.Fee.business.productInfo.service;

import java.util.List;

import com.Fee.business.productInfo.domain.ProductInfo;

/**
 *  @author zhuan
 */
public interface ProductInfoService {

   ProductInfo getProductInfo(int productInfoId);
   
   ProductInfo addProductInfo(ProductInfo productInfo);
   
   int updateProductInfo(ProductInfo productInfo);
   
   int deleteProductInfo(int id);
   
   int deleteProductInfo(String[] ids);
   
   List<ProductInfo> getAll();
}
