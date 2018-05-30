package com.Fee.business.callback.domain;

import com.Fee.common.enums.OrderStatusEnum;

/**
 * 供货商 返回状态
 * @author Zhuan
 *
 */
public class BizResponse {
	/**
	 * 订单状态
	 */
	private OrderStatusEnum statusType; //订单状态
	private String errorMsg;//错误信息（失败原因等信息）
	private String supplierOrderId;//供货商系统订单号
	private String reqOrderId;//我们的订单号
	private String supplierCode;//供货商id
	private String supplierName;//供货商名字
	
	public BizResponse() {
		
	}

	public BizResponse(OrderStatusEnum statusType, String errorMsg,
			String supplierOrderId, String reqOrderId) {
		this.statusType = statusType;
		this.errorMsg = errorMsg;
		this.supplierOrderId = supplierOrderId;
		this.reqOrderId = reqOrderId;
	}
	
	public BizResponse(String reqOrderId) {
		this.reqOrderId = reqOrderId;
	}


	public void setParam(OrderStatusEnum statusType,String errorMsg){
		this.statusType=statusType;
		this.errorMsg=errorMsg;
	}
	
	public void setParam(OrderStatusEnum statusType,String errorMsg,String orderId){
		this.statusType=statusType;
		this.errorMsg=errorMsg;
		this.reqOrderId=orderId;
	}
	
	public OrderStatusEnum getStatusType() {
		return statusType;
	}

	public void setStatusType(OrderStatusEnum statusType) {
		this.statusType = statusType;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public String getReqOrderId() {
		return reqOrderId;
	}

	public void setReqOrderId(String reqOrderId) {
		this.reqOrderId = reqOrderId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
