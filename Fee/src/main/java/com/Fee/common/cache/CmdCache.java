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

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.common.enums.WxConfigEnum;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.service.BaseService;

import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.TokenManager;

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
		Class[] clazzs=new Class[]{Customer.class,Supplier.class,ProductCategory.class,CustomerProduct.class,SupplierProduct.class};
		
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
			log.info("----------------缓存初始化完成----------------");
			
			//初始化微信
			initWx();
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 初始化微信相关内容
	 * @param configs
	 */
	private void initWx(){
		//初始化微信公众号 自动刷新Token 
		TokenManager.setDaemon(false);
			
		//初始化Token,小程序不需初始化
		TokenManager.init(WxConfigEnum.APP_ID.opt, WxConfigEnum.APP_SECRET.opt);
		//初始化 退款证书地址
		LocalHttpClient.initMchKeyStore(WxConfigEnum.MCH_ID.opt,WxConfigEnum.APP_CERT_ADRESS.opt);
		log.info("----------------微信初始化完成----------------");
			
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
	
	/**
	 * 获取缓存数据
	 * @param t 传一个对象进来
	 * @return 
	 */
	public static <T> T getCache(int key,T t){
		
		try {
			
			String name=t.getClass().getSimpleName().substring(0,1).toLowerCase()+t.getClass().getSimpleName().substring(1);
			Map<Integer,T> map = (Map) sysParamMap.get(name+"Map");
			
			return map.get(key);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "", e);
		}
		
		return null;
	}
	
	/**
	 * 获取缓存数据
	 * @param t 传一个对象进来
	 * @return 
	 */
	public static <T> Map<Integer,T> getCacheMap(String key,T t){
		
		try {
			
			return (Map)sysParamMap.get(key);
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, "", e);
		}
		
		return null;
	}
	
	
}
