package com.Fee.business.order.service;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.customer.service.CustomerService;
import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.customerProduct.service.CustomerProductService;
import com.Fee.business.order.domain.Order;
import com.Fee.business.phoneNumber.domain.PhoneNumber;
import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.business.supplier.domain.Supplier;
import com.Fee.business.supplier.service.SupplierService;
import com.Fee.business.supplierProduct.domain.SupplierProduct;
import com.Fee.business.supplierProduct.service.SupplierProductService;
import com.Fee.business.work.WorkService;
import com.Fee.business.wx.service.WxService;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.db.CommonDao;
import com.Fee.common.enums.OrderPayMentStatusEnum;
import com.Fee.common.enums.OrderStatusEnum;
import com.Fee.common.enums.WxConfigEnum;
import com.Fee.common.json.JsonUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.msg.ModelMsgUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.alibaba.fastjson.JSONObject;


/**
 *  @author zhuan
 */
 @Service
public class OrderServiceImpl implements OrderService{
	 private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	 

	@Autowired
	private CommonDao commonDao;
	@Autowired
	private CustomerProductService cupService;
	@Autowired
	private WorkService workService;
	@Autowired
	private SupplierProductService supplierProductService;
	@Autowired
	private WxService wxService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SupplierService supplierService;
	
    
	
	//订单状态限制：不允许修改成功或失败的订单
	private static final SqlExpression OrderStatusSqlExp = Cnd.exps("status", NutzType.LT.opt, OrderStatusEnum.ORDER_SUCCESS.opt).or("status",
			NutzType.GT.opt, OrderStatusEnum.ORDER_FAILE.opt);
	
	//支付状态限制：不允许修改支付状态为成功或失败的订单
	private static final SqlExpression OrderPayMentStatusSqlExp = Cnd.exps("payStatus", NutzType.LT.opt, OrderPayMentStatusEnum.PAY_SUCCESS.opt).or("payStatus",
			NutzType.GT.opt, OrderPayMentStatusEnum.PAY_FAILE.opt);
	
    @Override
   	public Order getOrder(int orderId){
    	return commonDao.fetch(Order.class,Cnd.where("id", NutzType.EQ.opt, orderId));
    }
    
    @Override
    public Order addOrder(Order order){
   		return commonDao.insert(order);
    }
   
    @Override
    public int updateOrder(Order order){
    	return commonDao.update(order);
    }
   
    @Override
    public int deleteOrder(int id){
    	return commonDao.delete(Order.class,id);
    }
   
    @Override
    public int deleteOrder(String[] ids){
    	return	commonDao.clear(Order.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public Order saveOrder(String openId, CustomerProduct product, String phone) {
		
		if(StringUtils.isNotBlank(openId)&& StringUtils.isNotBlank(phone)&&product!=null){
			
			try {
				
				//获取号码对应的省份与城市
				PhoneNumber phoneNumber=null;
				try {
					phoneNumber = cupService.getPhoneData(phone);
				} catch (Exception e) {
					LogUtils.sendExceptionLog(log, phone+",获取号码信息失败:"+JsonUtils.obj2Json(phoneNumber), e);
				}
				
				if(phoneNumber!=null){
					
					Order order=new Order();
					order.setOpenId(openId);
					order.setWxAppId(WxConfigEnum.APP_ID.opt);
					order.setPrice(product.getSells());
					order.setPhone(phone);
					order.setCusProductId(product.getId());
					order.setCustomerId(product.getCustomerId());
					order.setAddTime(TimeUtils.getTimeStamp());
					order.setProvince(phoneNumber.getProvince());
					return commonDao.insert(order);
				}
					
			} catch (Exception e) {
				LogUtils.sendExceptionLog(log, phone+"订单添加失败:openId:"+openId+",product:"+JsonUtils.obj2Json(product), e);
			}
		}
		return null;
	}
	
	
	@Override
	public int updateOrderPayMentStatusAndWxOrder(OrderPayMentStatusEnum payMentStataus, String wxOrderId,String orderId,String failMsg) {
		
		Chain chain = Chain.make("payStatus", payMentStataus.opt);
		Cnd cnd=Cnd.where("id", NutzType.EQ.opt, orderId).and(OrderPayMentStatusSqlExp).and(OrderStatusSqlExp);
		if(StringUtils.isNotBlank(wxOrderId)){
			chain.add("wxOrderId", wxOrderId);
		}
		
		if(StringUtils.isNotBlank(failMsg)){
			chain.add("failReason", failMsg);
		}
		
		//若修改的支付状态不为退款成功和失败时，则条件变为支付状态必须为成功
		if(payMentStataus!=OrderPayMentStatusEnum.PAY_SUCCESS &&  payMentStataus!=OrderPayMentStatusEnum.PAY_FAILE){
			cnd=Cnd.where("id", NutzType.EQ.opt, orderId).and("payStatus", NutzType.IN.opt, new int[]{OrderPayMentStatusEnum.PAY_SUCCESS.opt});
		}
		
		return commonDao.update(Order.class,chain , cnd);
	}
	
	@Override
	public int updateOrderStatusById(OrderStatusEnum orderstatus, int orderId, String failMsg) {
		Chain chain=Chain.make("status", orderstatus.opt);
		
		if(StringUtils.isNotBlank(failMsg)){
			chain.add("failReason", failMsg);
		}
		
		//成功或失败时加上完成时间
		if(orderstatus==OrderStatusEnum.ORDER_SUCCESS || orderstatus== OrderStatusEnum.ORDER_FAILE){
			chain.add("finishTime", TimeUtils.getTimeStamp());
		}
		return commonDao.update(Order.class, chain, Cnd.where("id", NutzType.EQ.opt, orderId).and("payStatus", NutzType.IN.opt,new int[]{OrderPayMentStatusEnum.PAY_SUCCESS.opt}).and(OrderStatusSqlExp));
	}

	@Override
	public BizResponse charge(Order order) {
		
		BizResponse br=new BizResponse();

		//添加黑名单判断
		boolean isBlack=false;//blackService.isBlack(order);

		if(!isBlack){
			
			//扣除采购商价钱及添加采购记录
			try {
				customerService.consume(order);
			} catch (Exception e) {
				//更新为手工处理
				br=new BizResponse(OrderStatusEnum.ORDER_MANUAL, e.getMessage(), "", order.getSendOrderId());
				return br;
			}
			
			SupplierProduct sup = supplierProductService.getChargeSupplierProduct(order);
			if(sup==null){
				LogUtils.sendLog(log, order.getId(), "获取不到供货商产品");
				br=new BizResponse(OrderStatusEnum.ORDER_MANUAL, "获取不到供货商产品,请查看产品/供货商状态", null, null);
			}else{
				
				order.setSupProductId(sup.getId());
				order.setSupplierId(sup.getSupplierId());
				
				commonDao.update(Order.class, Chain.make("supplierId", sup.getSupplierId()).add("supProductId", sup.getId()), Cnd.where("id", NutzType.EQ.opt, order.getId()));
				
				try {
					//更新订单供货商信息，扣除供货商钱,并添加记录
					supplierService.consume(order);
				} catch (Exception e) {
					br=new BizResponse(OrderStatusEnum.ORDER_MANUAL, e.getMessage(), "", order.getSendOrderId());
					return br;
				}
				Supplier supplier = CmdCache.getCache(sup.getSupplierId(), new Supplier());
				br=workService.excute(order, sup,null,null,supplier.getSimpleName());
			}
		}else{
			/*br=constractBlackBiz(br,order);
			SMSMessageUtils.sendMessage(WxMsgContentEnums.WX_BLACK_LIST_ORDER,baseService,true,new String[]{order.getPhone(),order.getOpenId(),order.getId()+""});*/
		}
		return br;
	}
	
	
	
	@Override	
	public int dealOrder(BizResponse biz) {
		String orderId_str = biz.getReqOrderId();
		OrderStatusEnum status = biz.getStatusType();
		int i=0;
		if(StringUtils.isNotBlank(orderId_str)&& status!=null){
			int orderId = Order.getOwnOrderId(orderId_str);
			//更改订单状态
			i = updateOrderStatusById(status, orderId, biz.getErrorMsg());
			Order order = getOrder(orderId);
			if(i!=0){
				
				if(status==OrderStatusEnum.ORDER_FAILE){
					//失败，修改状态
					if(order!=null){
						boolean flag = wxService.reFund(order);
						if(flag){
							updateOrderPayMentStatusAndWxOrder(OrderPayMentStatusEnum.PAY_REFUND_APPLICATION_SUCCESS, null,order.getId()+"",null);
						}
					}
				}
				
				//若为手工处理，则进行微信通知
				if(status==OrderStatusEnum.ORDER_MANUAL){
//					SMSMessageUtils.sendMessage(WxMsgContentEnums.WX_MANUAL_ORDER, baseService,false, order.getPhone(),order.getId()+"");
				}
				
				
				//发送充值成功/失败 模板通知
				ModelMsgUtils.sendModelMsg(new String[]{}, new String[]{}, "", order.getOpenId());
				
				//TODO 处理供货商充值记录，返回钱等
			}else{
				LogUtils.sendLog(log, orderId+"", "订单状态更改失败:"+JsonUtils.obj2Json(biz));
			}
		}
		return i;
	}

	@Override
	public List<JSONObject> getOrderByPhoneAndOpenId(String openId, String phone) {
		List<JSONObject> list=new ArrayList<>();
		List<Order> orders = commonDao.query(Order.class, Cnd.where("openId", NutzType.EQ.opt, openId).and("phone", NutzType.EQ.opt, phone).and("payStatus", NutzType.IN.opt, new int[]{1,2,4,5,6,7,8,9}));
		
		for (Order order : orders) {
			JSONObject obj=new JSONObject();
			obj.put("phone", order.getPhone());
			
			String status="";
			if(order.getPayStatus()==1){
				status= order.getStatus()==3?"成功":order.getStatus()==4?"失败":"充值中";
			}else{
				status= order.getPayStatus()==5 || order.getPayStatus()==7?"退款成功":order.getPayStatus()==8?"退款失败":"退款中";
			}
			
			obj.put("status",status);
			list.add(obj);
		}
		
		return list;
	}

	@Override
	public String checkPhone(String phone) {
		
		if(StringUtils.isNotBlank(phone)){
			PhoneNumber pn = commonDao.fetch(PhoneNumber.class,Cnd.where("phone", NutzType.EQ.opt, phone.substring(0,7)));
			if(pn!=null && StringUtils.equals(pn.getOperator(), "移动") && StringUtils.equals(pn.getProvince(), "广东")){
				return "success";
			}
		}else{
			log.info("检查号码请求为空");
		}
		return "fail";
	}

	@Override
	public void download(List<Order> orders,HttpServletRequest request, HttpServletResponse response)  {
		
		
		//获取所有字段名
		StringBuilder sb=new StringBuilder();
		//添加标题
		sb.append("号码,规格\r\n");
		Map<Integer, ProductCategory> cates = CmdCache.getCacheMap("productCategoryMap", new ProductCategory());
		
		//添加内容
		for (Order order : orders) {
			
			ProductCategory cate = cates.get(order.getCusProductId());
			sb.append(order.getPhone()+","+cate.getSize()+ProductCategory.getUnitName(cate.getUnit())+"\r\n");
		}
		
		try {
			
			response.setContentType("multipart/form-data");
			response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("订单", "UTF-8")+".csv");
			PrintWriter writer = response.getWriter();
			writer.write(sb.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.info("下载异常"+e.getMessage(),e);
		}
	}
	
	
	
}
