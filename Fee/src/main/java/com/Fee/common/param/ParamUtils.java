package com.Fee.common.param;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.Fee.common.log.LogUtils;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.resource.Constants;
import com.Fee.common.time.TimeUtils;



public class ParamUtils {
	private static Logger log = LoggerFactory.getLogger(ParamUtils.class);
	
	
	/**
	 * 获取分页参数
	 * @param request
	 * @return
	 */
	public static int[] getPageParam(HttpServletRequest request){
		int[] str=new int[2];
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数
		if(StringUtils.isNotBlank(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotBlank(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));
		
		str[0]=pageNo;
		str[1]=pageSize;
		return str;
	}
	
	/**
	 * 获取easyui分页数据
	 * @param page
	 * @return map对象
	 */
	public static <T> Map<String, Object> getEasyUIData(QueryResult result){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", result.getList());
		map.put("total", result.getPager().getRecordCount());
		return map;
	}
	
	/**
	 * 获取easyui分页数据,带上footer 数据
	 * @param page
	 * @return map对象
	 */
	public static <T> Map<String, Object> getEasyUIDataWityFooter(QueryResult result,List footer){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", result.getList());
		map.put("total", result.getPager().getRecordCount());
		map.put("footer", footer);
		return map;
	}
	
	
	/**
	 * 根据传来的参数构造条件
	 * @return
	 */
	public static Cnd getCnd(Map<String,Object> filterParamMap){
		Cnd cnd=Cnd.where("1", NutzType.EQ.opt, 1);
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			
			//排序
			if("sort".equals(entry.getKey()) ){
				String[] split = String.valueOf(entry.getValue()).split(",");
				String order=split[0];
				String orderBy=split[1];
				
				if("asc".equals(order))
					cnd.asc(orderBy);
				else if("desc".equals(order))
					cnd.desc(orderBy);
				
				continue;
			}
			
			
			String name = entry.getKey();
			String value =String.valueOf(entry.getValue());
			//如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				//进行分解，构造条件
				String[] str = name.split("_");
				switch(str[0]){
				
					case "EQ":
						cnd=cnd.and(str[1], NutzType.EQ.opt, value);break;
					case "LT":
						cnd=cnd.and(str[1], NutzType.LT.opt, value);break;
					case "LE":
						cnd=cnd.and(str[1], NutzType.LE.opt, value);break;
					case "GT":
						cnd=cnd.and(str[1], NutzType.GT.opt, value);break;
					case "GE":
						cnd=cnd.and(str[1], NutzType.GE.opt, value);break;
					case "LIKE":
						cnd=cnd.and(str[1], NutzType.LIKE.opt, "%"+value+"%");break;
					case "IN":
						cnd=cnd.and(str[1], NutzType.IN.opt, entry.getValue());break;
					case "NOEQUAL":
						cnd=cnd.and(str[1], NutzType.NOEQUAL.opt, value);break;
					case "ISNULL":
						Criteria cri = Cnd.cri();
						SqlExpressionGroup expressionGroup = cri.where().andIsNull(str[1]);
						cnd=cnd.and(expressionGroup);
				}
			}
		}
		
		return cnd;
	}
	
	
	/**
	 * 取得参数值构造成map
	 * 
	 * 
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		String orderBy="id";	//排序字段
		String order="desc";	//排序顺序,默认按照降序排列
		
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
				} else if (values.length > 1) {
					params.put(paramName, values);
				} else {
					try {
						String param = null;
						//本地，服务器的乱码解决
						if (Constants.getBoolean("isEncodeDebug") == null) {
							param = values[0];
						} else {
							if (Constants.getBoolean("isEncodeDebug")) {
								param = URLDecoder.decode(
										new String(values[0].getBytes("ISO-8859-1"), "UTF-8"),
										"UTF-8");
							} else {
								param = URLDecoder.decode(values[0], "UTF-8");
							}
						}
						//没有值的跳过
						if(StringUtils.isBlank(param)){
							continue;
						}
						//判断in 的情况，  以逗号分割  如 ： IN_id:1,2,3
						String[] split = param.split(",");
						if(split.length>1){
							params.put(paramName, split);
						}else{
							params.put(paramName, param);
						}
						
						//对时间的情况进行处理
						if(paramName.contains("Time")){
							Long time = TimeUtils.timeStrToLong(values[0]);
							if(time!=null){
								params.put(paramName, time);
							}
						}
					} catch (UnsupportedEncodingException e) {
						LogUtils.sendExceptionLog(log, paramName+":"+values, e);
					}
				}
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("sort")))
			orderBy=request.getParameter("sort").toString();
		if(StringUtils.isNotEmpty(request.getParameter("order")))
			order=request.getParameter("order").toString();
			params.put("sort", order+","+orderBy);

		return params;
	}
	
	
}
