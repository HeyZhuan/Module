package com.Fee.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ReflectUtil {

	private static final Logger log  =  Logger.getLogger(ReflectUtil.class);
  
	/**
	 * 
	 * 通过反射，将类信息转为Map
	 * 
	 * @param model
	 * @return
	 */
    public static Map<String,Object> getObject2Map(Object model) {
    	Map<String,Object> objMap = new HashMap<>();
    	try {
    		 Field[] field = model.getClass().getDeclaredFields();
    	        for (int j = 0; j < field.length; j++) {
    	            String name = field[j].getName(); // 获取属性的名字
    	            String valName = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
    	            String type = field[j].getGenericType().toString();// 获取属性的类型
    	            Method m = model.getClass().getMethod("get" + valName);// 如果type是类类型，则前面包含"class "，后面跟类名
    	            Object value = null;
    	            switch(type){
    	            case "class java.lang.String":
    	            	value = (String) m.invoke(model);
    	            	break;
    	            case "class java.lang.Integer":
    	            	value = (Integer) m.invoke(model);
    	            	break;
    	            case "class java.lang.Short":
    	            	value = (Short) m.invoke(model);
    	            	break;
    	            case "class java.lang.Double":
    	            	value = (Double) m.invoke(model);
    	            	break;
    	            case "class java.lang.Boolean":
    	            	value = (Boolean) m.invoke(model);
    	            	break;
    	            case "class java.util.Date":
    	            	value = (Date) m.invoke(model);
    	            	break;
    	            case "class java.math.BigDecimal":
    	            	value = (BigDecimal)m.invoke(model);
    	            	break;
    	            case "int":
    	            	value = (int)m.invoke(model);
    	            	break;
    	            case "long":
    	            	value = (long)m.invoke(model);
    	            	break;
    	            case "float":
    	            	value = (float)m.invoke(model);
    	            	break;
    	            case "double":
    	            	value = (double)m.invoke(model);
    	            	break;
    	            }
    	            if(value!=null){
    	            	objMap.put(name, value);
    	            }
    	        }
		} catch (Exception e) {
			log.error("-error-" + e.getMessage(), e);
			log.info("-error-" + e.getMessage(), e);
		}
    	return objMap;
       
    }
	
}
