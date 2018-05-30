package com.Fee.business.customer.service;

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.order.domain.Order;

/**
 *  @author zhuan
 */
public interface CustomerService {

   Customer getCustomer(int customerId);
   
   Customer addCustomer(Customer customer);
   
   int updateCustomer(Customer customer);
   
   int deleteCustomer(int id);
   
   int deleteCustomer(String[] ids);
   
  /**
   * 扣除采购商的钱以及添加采购商充值记录
   * @param order
   * @return
   */
   void consume(Order order);
}
