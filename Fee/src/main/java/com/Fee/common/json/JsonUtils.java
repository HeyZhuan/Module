package com.Fee.common.json;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	/***
	 * java对象转json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj) {
		if(obj!=null){
			String str = JSON.toJSONString(obj);
			return str;
		}
		return "";
	}

	/***
	 * json转Java对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * json转List
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);

	}

	public static List<Map<String, Object>> getMapList(List<?> list) {
		return JsonUtils.json2Obj(JsonUtils.obj2Json(list), List.class);
	}

	public static Map<String, Object> o2Map(Object o) {
		return JsonUtils.json2Obj(JsonUtils.obj2Json(o), Map.class);
	}
}
