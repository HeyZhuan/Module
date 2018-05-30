package com.Fee.business.customerAudit.service;

import com.Fee.business.customerAudit.domain.CustomerAudit;

/**
 *  @author zhuan
 */
public interface CustomerAuditService {

   CustomerAudit getCustomerAudit(int customerAuditId);
   
   CustomerAudit addCustomerAudit(CustomerAudit customerAudit);
   
   void updateCustomerAudit(CustomerAudit customerAudit);
   
   int deleteCustomerAudit(int id);
   
   int deleteCustomerAudit(String[] ids);
}
