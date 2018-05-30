package com.Fee.business.supplier.service;

import java.util.List;

import com.Fee.business.order.domain.Order;
import com.Fee.business.supplier.domain.Supplier;

/**
 *  @author zhuan
 */
public interface SupplierService {

   Supplier getSupplier(int supplierId);
   
   Supplier addSupplier(Supplier supplier);
   
   int updateSupplier(Supplier supplier);
   
   int deleteSupplier(int id);
   
   int deleteSupplier(String[] ids);
   
   List<Supplier> getAll();
   
   void consume(Order order);
}
