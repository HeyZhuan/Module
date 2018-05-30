package com.Fee.business.customerProduct.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.business.customer.domain.Customer;
import com.Fee.business.customerProduct.domain.CustomerProduct;
import com.Fee.business.phoneNumber.domain.PhoneNumber;
import com.Fee.business.productCategory.domain.ProductCategory;
import com.Fee.common.cache.CmdCache;
import com.Fee.common.db.CommonDao;
import com.Fee.common.http.HttpUtils;
import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.time.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *  @author zhuan
 */
 @Service
public class CustomerProductServiceImpl implements CustomerProductService{
	 private static final Logger log = LoggerFactory.getLogger(CustomerProductServiceImpl.class);

	@Autowired
	private CommonDao commonDao;
    
    @Override
   	public CustomerProduct getCustomerProduct(int customerProductId){
    	return commonDao.fetch(CustomerProduct.class,Cnd.where("id", NutzType.EQ.opt, customerProductId));
    }
    
    @Override
	public List<CustomerProduct> getCupsByCustomerId(int customerId) {

    	Customer customer = CmdCache.getCache(customerId, new Customer());
    	if(customer!=null){
    		return commonDao.query(CustomerProduct.class, Cnd.where("customerId", NutzType.EQ.opt, customerId).and("status", NutzType.EQ.opt, 1));
    	}
    	
		return null;
	}

	@Override
    public CustomerProduct addCustomerProduct(CustomerProduct cp){
    	
    	ProductCategory cate = CmdCache.getCache(cp.getCategoryId(),new ProductCategory());
    	cp.setPrice(cate.getPrice());
    	cp.setAddTime(TimeUtils.getTimeStamp());
    	cp.setName(cate.getName());
    	if(cp.getSells()==0){
    		cp.setSells(cate.getPrice());
    	}
   		return commonDao.insert(cp);
    }
   
    @Override
    public int updateCustomerProduct(CustomerProduct customerProduct){
    	return commonDao.update(customerProduct);
    }
   
    @Override
    public int deleteCustomerProduct(int id){
    	return commonDao.delete(CustomerProduct.class,id);
    }
   
    @Override
    public int deleteCustomerProduct(String[] ids){
    	return	commonDao.clear(CustomerProduct.class, Cnd.where("id", NutzType.IN.opt, ids));
    }

	@Override
	public PhoneNumber getPhoneData(String phone) {
		PhoneNumber pn = commonDao.fetch(PhoneNumber.class, Cnd.where("phone", NutzType.EQ.opt, phone.trim().substring(0, 7)));
		
		if(pn==null){
			try {
				
				pn=getPhoneByBaiDu(phone);
			} catch (Exception e) {
				LogUtils.sendExceptionLog(log, phone+"，获取百度号码信息异常", e);
			}
		}
		return pn;
	}
	
	public PhoneNumber getPhoneByBaiDu(String phone){
		String cbString="jQuery110209368391615143867_1524645719497";
		String url="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="+phone+"&co=&resource_id=6004&t="+System.currentTimeMillis()+"&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb="+cbString+"&_="+System.currentTimeMillis();
		
		Map<String, String> map=new HashMap<>();
		map.put("accept", "*/*");
		map.put("connection", "Keep-Alive");
		map.put("content-type","application/json;charset=gbk");
		map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36 LBBROWSER");
		
		LogUtils.sendLog(log, phone, "请求百度请求信息:"+url);
		String string = HttpUtils.sendGet(url, map);
		LogUtils.sendLog(log, phone, "请求百度返回信息:"+string);
		string=string.replace(cbString, "").replace("(", "").replace(")", "").replace("/**/", "").replace(";", "");
		JSONObject obj = JSON.parseObject(string);
		String data = obj.getString("data");
		JSONArray array = JSON.parseArray(data);
		String city = array.getJSONObject(0).getString("city");
		String province = array.getJSONObject(0).getString("prov");
		String operator = array.getJSONObject(0).getString("type");
		
		PhoneNumber pn=new PhoneNumber();
		pn.setCity(city);
		pn.setProvince(province);
		pn.setOperator(operator);
		return commonDao.insert(pn);
	}
}
