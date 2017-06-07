package xinxing.boss.supplier.supplierapi.phonetraffic.others.templete.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xinxing.boss.supplier.api.domain.SupplierChargeRequest;
import xinxing.boss.supplier.api.domain.SupplierQueryRequest;
import xinxing.boss.supplier.supplierapi.domain.BizResponse;

public interface Templete_Service {
	/**
	 * 业务请求入口
	 * @param supplierApiReq
	 * @return
	 */
	public BizResponse handleBizReq(SupplierChargeRequest scr);
	
	
	/**
	 * 业务查询入口
	 * @param supplierApiReq
	 * @return
	 */
	public BizResponse handleBizQuery(SupplierQueryRequest sqr);
	
	/**
	 * 业务回调入口
	 * @param request
	 * @param response
	 */
	public void handleBizCallback(HttpServletRequest request,HttpServletResponse response,String headName);
}
