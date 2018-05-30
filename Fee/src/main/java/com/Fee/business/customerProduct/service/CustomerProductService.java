package com.Fee.business.customerProduct.service;

import java.util.List;

import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.phoneNumber.domain.PhoneNumber;

/**
 *  @author zhuan
 */
public interface CustomerProductService {

   CustomerProduct getCustomerProduct(int customerProductId);
   
   List<CustomerProduct> getCupsByCustomerId(int customerId);
  
   CustomerProduct addCustomerProduct(CustomerProduct customerProduct);
   
   int updateCustomerProduct(CustomerProduct customerProduct);
   
   int deleteCustomerProduct(int id);
   
   int deleteCustomerProduct(String[] ids);
   
   
   PhoneNumber getPhoneData(String phone);
}
