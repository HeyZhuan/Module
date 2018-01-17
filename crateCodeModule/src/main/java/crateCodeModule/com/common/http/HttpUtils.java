package crateCodeModule.com.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class HttpUtils {
	
	private static final Logger log = Logger.getLogger(HttpUtils.class);

	
	public static String sendGet(String url, String encode) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			// 建立实际的连接
			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	
	/*public static String doHttpGet(HttpBuilder httpBuilder) {
		String result = "";
		BufferedReader in = null;
		try {
			URL conUrl = new URL(httpBuilder.getUrl() + "?" + httpBuilder.getData());
			HttpURLConnection conn = (HttpURLConnection) conUrl.openConnection();
			// 设置通用的请求属性
			// 设置通用的请求属性
			if( httpBuilder.getHttpHeadMap()!=null){
				Set<String> set = httpBuilder.getHttpHeadMap().keySet();
				for(String key: set){
					conn.setRequestProperty(key, httpBuilder.getHttpHeadMap().get(key));
				}
			}
			//设置超时属性信息
			conn.setConnectTimeout(httpBuilder.getConnectTimeOut());
			conn.setReadTimeout(httpBuilder.getReadTimeOut());
			// 建立实际的连接
			conn.connect();
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), httpBuilder.getEncode()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.info(e.getMessage(),e);
			}
		}
		return result;
	}*/

	/*public static String doHttpPost(HttpBuilder httpBuilder) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(httpBuilder.getUrl());
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			if( httpBuilder.getHttpHeadMap()!=null){
				Set<String> set = httpBuilder.getHttpHeadMap().keySet();
				for(String key: set){
					conn.setRequestProperty(key, httpBuilder.getHttpHeadMap().get(key));
				}
			}
			conn.setConnectTimeout(httpBuilder.getConnectTimeOut());
			conn.setReadTimeout(httpBuilder.getReadTimeOut());
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置请求编码值
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), httpBuilder.getEncode()));
			// 发送请求参数
			out.print(httpBuilder.getData());
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),httpBuilder.getEncode()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				log.info(e.getMessage(),e);
			}
		}
		return result;
	}*/

	

	/**
	 * 获取请求的参数信息
	 * @param request
	 * @return
	 */
	public static Map<String,String> getReqParams(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		if (paramNames.hasMoreElements()) {
			Map<String,String> map = new HashMap<String,String>();
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
			while(paramNames.hasMoreElements()){
				paramName = paramNames.nextElement();
				paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			return map;
		}
		return null;
	}
	
	public static Map<String,String> getReqParams(String reqParams) {
		String []arge = StringUtils.split(reqParams, "&");
		if(arge.length>0){
			Map<String,String> map = new HashMap<>();
			for(String str : arge){
				String []param = StringUtils.split(str, "=");
				if(param.length==2){
					map.put(param[0], param[1]);
				}
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 获取Post数据
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getReqPostString(HttpServletRequest request,Logger log){
		StringBuilder reqData = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try{
            reader = request.getReader();
            while((line = reader.readLine()) != null) {
            	reqData.append(line);
            }
            if(reqData.length() <= 0) {
                return null; 
            }
            String strJson = reqData.toString();
            reader.close();
            log.info("获取post数据: strJson=" +strJson);
            return strJson;
        }catch(IOException e) {
        	log.error("获取post数据错误:" + e.getMessage(),e);
        }
        return null;
	}
	/**
	 * 获取Post数据
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getReqPostString(HttpServletRequest request){
		StringBuilder reqData = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		try{
			reader = request.getReader();
			while((line = reader.readLine()) != null) {
				reqData.append(line);
			}
			if(reqData.length() <= 0) {
				return null; 
			}
			String strJson = reqData.toString();
			reader.close();
			log.info("获取post数据: strJson=" +strJson);
			return strJson;
		}catch(IOException e) {
			log.error("获取post数据错误:" + e.getMessage(),e);
		}
		return null;
	}
	
	  /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostWithContentType(String url, String param ,String content_type) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-type", "text/html");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	log.error(ex.getMessage(),ex);
            }
        }
        return result;
    }  
    
    
    
    
	 /**
	  * 获得对应的xx=yy&&aa==bb 字符串 字典排序
	  * @param sendReqMap
	  * @return
	  */
	 public static String getStrByMap(Map<String,Object> sendReqMap) {
		 String sign = "";
		 Object[] key_arr = sendReqMap.keySet().toArray();
		 for (Object k : key_arr) {
			 Object value = sendReqMap.get(k.toString());
			 sign += "&" + k + "=" + value;
		 }
		 if(sign.length()>0){
			 sign=sign.substring(1,sign.length());
		 }
		 return sign;
	 }
}
