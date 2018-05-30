package com.Fee.common.str;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class StrUtils {
	
	
	/**
	 * 获取随机字符
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
	    Random random = new Random();   
	    StringBuilder sb = new StringBuilder();   
	    int lenth = base.length();
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(lenth);   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	
	/**
	 * 字符串sStr是否包含由split分隔的tStr
	 * @param sStr
	 * @param tStr
	 * @param split
	 * @return
	 */
	public static boolean isStrContain(String sStr,String tStr,String split){
		if(StringUtils.isBlank(sStr)){
			return false;
		}
		String[] splitStrs = StringUtils.split(sStr,split);
		for (int i = 0, len = splitStrs.length; i < len; i++) {
			if (StringUtils.equals(splitStrs[i], tStr)) {
				return true;
			}
		}
		return false;
	}
}
