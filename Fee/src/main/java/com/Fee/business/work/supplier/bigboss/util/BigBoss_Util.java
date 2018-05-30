package com.Fee.business.work.supplier.bigboss.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.Fee.business.order.domain.Order;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.security.MD5_HexUtil;


public class BigBoss_Util {

	private static final Logger log = Logger.getLogger(BigBoss_Util.class);
	private static final int MERCHANT=10272;
	private static final int CLIENT=10000;
	private static final String APIKEY="hseyiCWsLtmyaXj";

	public static String getSendStr(Order order,SupplierProduct product,String orderId) {
		Map<String, Object> map = new HashMap<>();
		String accountVal = order.getPhone();
		
		String version = "V100";
		Long ts = System.currentTimeMillis();
		map.put("accountVal", accountVal);
		map.put("merchant", MERCHANT);
		map.put("clientId", CLIENT);
		map.put("product", product.getApiCode());
		map.put("outTradeNo", orderId);
		map.put("version", version);
		map.put("ts", ts);
		map.put("sign", MD5_HexUtil.md5Hex(getSign4Act(map,APIKEY)).toLowerCase());
		return JsonUtils.obj2Json(map);
	}

	public static String getSign(Map<String, Object> map) {

		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);

		StringBuffer sb = new StringBuffer();

		for (String key : list) {
			sb.append(key + map.get(key));
		}
		sb.append(APIKEY);
		return sb.toString();

	}

	public static String getSign4Act(Map<String, Object> map,String apikey) {

		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);

		StringBuffer sb = new StringBuffer();

		for (String key : list) {
			sb.append(key + map.get(key));
		}
		sb.append(apikey);
		return sb.toString();

	}
}
