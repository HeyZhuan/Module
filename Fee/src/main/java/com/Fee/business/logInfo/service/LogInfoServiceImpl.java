package com.Fee.business.logInfo.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.logInfo.domain.LogInfo;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.db.CommonDao;
import com.Fee.common.enums.ContentTypeEnum;
import com.Fee.common.enums.WorkTypeEnum;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.user.util.UserUtil;

/**
 *  @author zhuan
 */
 @Service
public class LogInfoServiceImpl implements LogInfoService{
	 private static Logger logger = LoggerFactory.getLogger(LogInfoServiceImpl.class);

	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public LogInfo getLogInfo(int logInfoId){
    	return commonDao.fetch(LogInfo.class,Cnd.where("id", NutzType.EQ.opt, logInfoId));
    }
    
    @Override
    public LogInfo addLogInfo(LogInfo logInfo){
   		return commonDao.insert(logInfo);
    }
   
    @Override
    public int updateLogInfo(LogInfo logInfo){
    	return commonDao.update(logInfo);
    }
   
    @Override
    public int deleteLogInfo(int id){
    	return commonDao.delete(LogInfo.class,id);
    }
   
    @Override
    public int deleteLogInfo(String[] ids){
    	return	commonDao.clear(LogInfo.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public void addLog(WorkTypeEnum workType, ContentTypeEnum contentType, Object obj) {
		/*try {
			
			LogInfo log=new LogInfo();
			log.setName(UserUtil.getUserName());
			log.setContentType(contentType.opt);
			log.setWorkType(workType.opt);
			log.setAddTime(TimeUtils.getTimeStamp());
			if(obj!=null){
				log.setRemark(JsonUtils.obj2Json(obj));
				CmdCache.refreshCache(obj);
			}
			commonDao.insert(log);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(logger, "添加日志异常", e);
		}*/
	}
	
	@Override
	public void addLog(WorkTypeEnum workType, ContentTypeEnum contentType, String remark,double price) {
		/*try {
			
			LogInfo log=new LogInfo();
			log.setName(UserUtil.getUserName());
			log.setContentType(contentType.opt);
			log.setWorkType(workType.opt);
			log.setAddTime(TimeUtils.getTimeStamp());
			if(remark!=null){
				log.setRemark(remark);
			}
			if(price!=0){
				log.setPrice(price);
			}
			commonDao.insert(log);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(logger, "添加日志异常", e);
		}*/
	}

	@Override
	public void addLog(Class clazz,WorkTypeEnum workType, ContentTypeEnum contentType, String[] ids) {
		/*try {
			
			LogInfo log=new LogInfo();
			log.setName(UserUtil.getUserName());
			log.setContentType(contentType.opt);
			log.setWorkType(workType.opt);
			log.setAddTime(TimeUtils.getTimeStamp());
			List list = commonDao.query(clazz, Cnd.where("id", NutzType.IN.opt, ids));
			logger.info(UserUtil.getUserName()+"_删除了以下数据:"+JsonUtils.obj2Json(list));
			log.setRemark(JsonUtils.obj2Json(ids));
			CmdCache.refreshCache(ids);
			commonDao.insert(log);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(logger, "添加日志异常", e);
		}*/
		
	}
}
