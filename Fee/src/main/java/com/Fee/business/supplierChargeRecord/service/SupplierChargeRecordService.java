package com.Fee.business.supplierChargeRecord.service;

import com.Fee.business.supplierChargeRecord.domain.SupplierChargeRecord;

/**
 *  @author zhuan
 */
public interface SupplierChargeRecordService {

   SupplierChargeRecord getSupplierChargeRecord(int supplierChargeRecordId);
   
   SupplierChargeRecord addSupplierChargeRecord(SupplierChargeRecord supplierChargeRecord);
   
   int updateSupplierChargeRecord(SupplierChargeRecord supplierChargeRecord);
   
   int deleteSupplierChargeRecord(int id);
   
   int deleteSupplierChargeRecord(String[] ids);
}
