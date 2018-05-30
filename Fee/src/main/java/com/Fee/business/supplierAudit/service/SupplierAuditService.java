package com.Fee.business.supplierAudit.service;

import com.Fee.business.supplierAudit.domain.SupplierAudit;

/**
 *  @author zhuan
 */
public interface SupplierAuditService {

   SupplierAudit getSupplierAudit(int supplierAuditId);
   
   SupplierAudit addSupplierAudit(SupplierAudit supplierAudit);
   
   void updateSupplierAudit(SupplierAudit supplierAudit);
   
   int deleteSupplierAudit(int id);
   
   int deleteSupplierAudit(String[] ids);
}
