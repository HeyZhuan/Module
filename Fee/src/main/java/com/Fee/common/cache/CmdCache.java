package com.Fee.common.cache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.Fee.business.productInfo.domain.ProductInfo;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.service.BaseService;

/**
 * 货物，产品分类，客户，供货商 缓存进applicationContext 中，以方便前台直接使用
 * @author Zhuan
 *
 */
@Component
public class CmdCache implements ServletContextAware{

	private static final Logger log = LoggerFactory.getLogger(CmdCache.class);
	private static ServletContext webContext=null;
	// 参数总Map
	public static Map<String, Object> sysParamMap = new HashMap<String, Object>();
		
	@Autowired
	private BaseService baseService;
	

	@Override
	public void setServletContext(ServletContext context) {
		
		webContext=context;
		Class[] clazzs=new Class[]{ProductInfo.class,Supplier.class};
		
		try {
			
			for (Class clazz : clazzs) {
				List list = baseService.getAll(clazz);
				
				Map map=new HashMap<>();
				for (Object obj : list) {
					Field id = obj.getClass().getDeclaredField("id");
					id.setAccessible(true);
					map.put(id.get(obj), obj);
				}
				
				//首字母小写
				String name=clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1);
				
				sysParamMap.put(name+"Map", map);
				sysParamMap.put(name+"MapJson", JsonUtils.obj2Json(map));
			}
			context.setAttribute("sysParam",sysParamMap );
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
	}

	/**
	 * 更新缓存
	 * @param t 需要更新的对象
	 */
	public static void refreshCache(Object obj){
		
		try {
			
			Class clazz = obj.getClass();
			String name=clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1);
			Map map = (Map) sysParamMap.get(name+"Map");
			
			if(map!=null){
				Field id = clazz.getDeclaredField("id");
				id.setAccessible(true);
				Object object = id.get(obj);
				map.put(object, obj);
				sysParamMap.put(name+"MapJson", JsonUtils.obj2Json(map));
			}
			
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "", e);
		}
		
	}
	
	
	/**
	 * 更新缓存
	 * @param t 需要更新的对象
	 */
	public static void refreshCache(String[] ids,Class clazz){
		
		try {
			
			String name=clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1);
			Map map = (Map) sysParamMap.get(name+"Map");
			
			for (String id : ids) {
				map.remove(Integer.parseInt(id));
			}
			sysParamMap.put(name+"MapJson", JsonUtils.obj2Json(map));
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "", e);
		}
		
	}
	
	
}
