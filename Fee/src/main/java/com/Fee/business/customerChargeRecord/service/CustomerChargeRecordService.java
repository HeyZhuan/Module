package com.Fee.business.customerChargeRecord.service;

import com.Fee.business.customerChargeRecord.domain.CustomerChargeRecord;

/**
 *  @author zhuan
 */
public interface CustomerChargeRecordService {

   CustomerChargeRecord getCustomerChargeRecord(int customerChargeRecordId);
   
   CustomerChargeRecord addCustomerChargeRecord(CustomerChargeRecord customerChargeRecord);
   
   int updateCustomerChargeRecord(CustomerChargeRecord customerChargeRecord);
   
   int deleteCustomerChargeRecord(int id);
   
   int deleteCustomerChargeRecord(String[] ids);
}
