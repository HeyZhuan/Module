package xinxing.boss.supplier.supplierapi.phonetraffic.others.templete.util;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import xinxing.boss.supplier.api.domain.SupplierChargeRequest;
import xinxing.boss.supplier.api.domain.SupplierQueryRequest;
import xinxing.boss.supplier.common.encry.MD5_HexUtil;
import xinxing.boss.supplier.common.http.HttpUtils;
import xinxing.boss.supplier.common.resource.Constants;
import xinxing.boss.supplier.supplierapi.domain.BizDomain;
import xinxing.boss.supplier.supplierapi.domain.BizResponse;

public class Templete_Util {
	/**
	 * 
	 * 构建发送信息
	 * 
	 * @param scr
	 * @param order_orderId
	 * @return
	 */
	public static String buildSendReq(SupplierChargeRequest scr) {
		String order_head = scr.getHeadName();
		String order_phone = scr.getAccountVal();// 手机号
		String order_productCode = scr.getBizCode();// 流量包编码
		String order_orderId = scr.getPlatformTransId();// 订单xxid
		String[] split_productCode = order_productCode.split(",");//
//		String  = Constants.getString(order_head+"templete_".trim()).trim();
		
		Map<String, String> map=new HashMap<>();
		map.put("", );
		map.put("", );
		map.put("", );
		
		
		
		String sendReq = JsonUtils.obj2Json(sendReqMap);
//		String sendReq = HttpUtils.getStrByMapOrderByABC(sendReqMap);
		return sendReq;
	}

	public static String buildQueryReq(SupplierQueryRequest sqr) {
		BizResponse br = null;
		String order_orderId = sqr.getPlatformTransId();
		String supplierOrderId = sqr.getSupplierTransId();
//		String  = Constants.getString(order_head+"templete_".trim()).trim();
		Map<String, String> map=new HashMap<>();
		map.put("", );
		map.put("", );
		map.put("", );
		
		String sendReq = HttpUtils.getStrByMapOrderByABC(sendReqMap);
		return sendReq;
	}
	public static String getErrorMsg(String code) {
		Map<String, String> map = new HashMap<>();
		map.put("".trim(),"".trim()); 
		return map.get(code) == null ? "" : map.get(code);
	}

}
