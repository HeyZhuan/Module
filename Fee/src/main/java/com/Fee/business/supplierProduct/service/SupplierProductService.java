package com.Fee.business.supplierProduct.service;

import java.util.List;

import com.Fee.business.order.domain.Order;
import com.Fee.business.supplierProduct.domain.SupplierProduct;

/**
 *  @author zhuan
 */
public interface SupplierProductService {

   SupplierProduct getSupplierProduct(int supplierProductId);
   
   SupplierProduct addSupplierProduct(SupplierProduct supplierProduct);
   
   int updateSupplierProduct(SupplierProduct supplierProduct);
   
   int deleteSupplierProduct(int id);
   
   int deleteSupplierProduct(String[] ids);
   
   /**
    * 获取可以充值的供货商产品
    * @param order
    * @return
    */
   SupplierProduct getChargeSupplierProduct(Order order);
   
}
