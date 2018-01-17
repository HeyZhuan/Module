package crateCodeModule.com.galrami.util;

import java.math.BigDecimal;
import java.util.Date;

public class FieldUtils {

	/**
	 * 获取运算类型
	 * @param clazz
	 * @return
	 */
	public static String getCalCulateType(Object obj){
		
		if(obj  instanceof String){
			return "LIKE";
		}
		return "EQ";
	}
	
	/**
	 * 获取运算类型
	 * @param clazz
	 * @return
	 */
	public static String getCalCulateType(boolean flag){
		
		if(flag){
			return "LIKE";
		}
		return "EQ";
	}
	
	/**
	 * 获取字段类型
	 * @param clazz
	 * @return
	 */
	public static String getType(String typeName){
		
		
		switch (typeName) {
		case "java.lang.Integer":return "MI";
		case "java.lang.String":return "SS";
		case "java.lang.Long":return "ML";
		case "java.lang.Double":return "MD";
		case "java.math.BigDecimal":return "MBD";
		case "java.util.Date":return "TD";

		default:
			return "SS";
		}
	}
	
	
	/**
	 * 获取运算类型
	 * @param clazz
	 * @return
	 */
	public static String getQueryType(boolean flag,String typeName,String name){
		return getCalCulateType(flag)+"_"+getType(typeName)+"_"+name;
	}
	
	
	public static void main(String[] args) {
			String name=BigDecimal.class.getName();
			System.out.println(name);
			
		
	}
	
}
