package com.Fee.business.logInfo.service;

import com.Fee.business.logInfo.domain.LogInfo;
import com.Fee.common.enums.ContentTypeEnum;
import com.Fee.common.enums.WorkTypeEnum;

/**
 *  @author zhuan
 */
public interface LogInfoService {

   LogInfo getLogInfo(int logInfoId);
   
   LogInfo addLogInfo(LogInfo logInfo);
   
   int updateLogInfo(LogInfo logInfo);
   
   int deleteLogInfo(int id);
   
   int deleteLogInfo(String[] ids);
   
   /**
    * 添加对应操作日志
    * @param clazz
    */
   void addLog(WorkTypeEnum workType,ContentTypeEnum contentType,Object obj);
   
   /**
    * 添加对应操作日志
    * @param clazz
    */
   void addLog(WorkTypeEnum workType,ContentTypeEnum contentType,String remark,double price);
   
   /**
    * 添加对应的删除操作日志
    * @param clazz
    */
   void addLog(Class clazz,WorkTypeEnum workType, ContentTypeEnum contentType, String[] ids);

}
