package com.Fee.business.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Fee.business.callback.domain.BizResponse;
import com.Fee.business.order.domain.Order;
import com.Fee.business.order.service.OrderService;
import com.Fee.common.enums.OrderPayMentStatusEnum;
import com.Fee.common.enums.WxConfigEnum;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.msg.ModelMsgUtils;

import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.util.MapUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;


@Controller
public class WxCallback {
	private static final Logger log = Logger.getLogger(WxCallback.class);
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/weixinpay_callback.do")
	public void weixinPayCallBack(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String postData = HttpUtils.getReqPostString(request, log);
		log.info("微信支付回调信息:"+postData);
		try {
			if(StringUtils.isNotBlank(postData)){
				
				MchPayNotify notify = XMLConverUtil.convertToObject(MchPayNotify.class, postData);
				Map<String,String> map = MapUtil.objectToMap(notify);
				boolean isSignOk = SignatureUtil.validateSign(map, WxConfigEnum.PAY_SECRET.opt);
				
				if(isSignOk){
					String wxOrderId = map.get("transaction_id")==null?"":map.get("transaction_id");
					String ourOrderId = map.get("out_trade_no")==null?"":map.get("out_trade_no");
					String status = map.get("result_code")==null?"":map.get("result_code");
					String errCode=map.get("err_code")==null?"":map.get("err_code");
					String errMsg=map.get("err_code_des")==null?"":map.get("err_code_des");
					int our_order_id=Order.getOwnOrderId(ourOrderId);
					
					Order order = orderService.getOrder(our_order_id);
					OrderPayMentStatusEnum enum1="SUCCESS".equals(status)?OrderPayMentStatusEnum.PAY_SUCCESS:"FAIL".equals(status)?OrderPayMentStatusEnum.PAY_FAILE:OrderPayMentStatusEnum.PAY_MANUAL;
					int i = orderService.updateOrderPayMentStatusAndWxOrder(enum1, wxOrderId, our_order_id+"",errCode+errMsg);
					if(i>0&&enum1==OrderPayMentStatusEnum.PAY_SUCCESS){
						
						
						//支付成功通知模板消息发送
						ModelMsgUtils.sendModelMsg(new String[]{}, new String[]{}, "", order.getOpenId());
						
						BizResponse res = orderService.charge(order);
						orderService.dealOrder(res);
					}
				}else{
					LogUtils.sendLog(log, postData, "校验失败");
				}
				
				String resMsg="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
				PrintWriter writer = response.getWriter();
				writer.write(resMsg);
				writer.flush();
				writer.close();
				
				
			}
		} catch (Exception e) {
			LogUtils.sendExceptionLog(log, postData, e);
		}
	}
}
