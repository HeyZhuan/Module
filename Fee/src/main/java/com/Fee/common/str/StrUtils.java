package com.Fee.common.str;

import org.apache.commons.lang.StringUtils;

public class StrUtils {
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
