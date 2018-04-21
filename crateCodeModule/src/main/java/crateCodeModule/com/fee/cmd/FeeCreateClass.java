 package crateCodeModule.com.fee.cmd;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import crateCodeModule.com.common.file.CommonUtils;
import crateCodeModule.com.common.file.FileUtil;
import crateCodeModule.com.fee.domain.Customer;
import crateCodeModule.com.fee.domain.CustomerProduct;
import crateCodeModule.com.galrami.util.FieldUtils;
import crateCodeModule.com.galrami.util.JspFieldName;

@Service
public class FeeCreateClass {
	
	//要生成的jsp文件路径
	private static final String listUrl="F:/git_work/Fee/src/main/webapp/WEB-INF/jsp/fee/";
	//要生成的Controller路径
	private static final String classUrl="F:/git_work/Fee/src/main/java/com/Fee/business/";
	
	//templete文件路径
	private static final String tempUrl="F:/git_work/crateCodeModule/src/main/java/crateCodeModule/com/wareHouses/templete/";
	
	
	public static Map<String, JSONObject> allMap=new LinkedHashMap<>();//所有字段
	public static Map<String, JSONObject> queryMap=new LinkedHashMap<>();//查询字段
	public static String prefix="";//用于package
	public static String prefixUrl="";//用于生成地址
	
	/**
	 * 构造步骤：
	 * 1、把要生成的实体类放到classes 中，并加上自定义的注解
	 * 2、运行程序
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String className="customerProduct"; //类名--英文--小写
		String chineseName="采购商产品信息";//
		Class clazz=CustomerProduct.class;// 实体类
		String tableName="fee_customer_product";
		String CLASS=CommonUtils.getFirstUpWord(className);
		int pid=56;//菜单的上一级目录
		prefix="";//例如 ： .app
		prefixUrl="";  //例如：   app/
		try {
			
			createService(className);
			createController(className,chineseName);
			createJsp(className,clazz);
			createDomain(className, chineseName, clazz, tableName);
			
			//生成对应权限
//			HttpUtils.sendGet("http://localhost:8080/WareHouses/data/addMenu?pid="+pid+"&name="+chineseName+"&className="+className, "UTF-8");
			//生成对应表
//			HttpUtils.sendGet("http://localhost:8080/WareHouses/data/createTable?className=com.Fee.business."+className+".domain."+CLASS, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 构造Domain
	 * @throws IOException 
	 * 
	 */
	public static void createDomain(String className,String chineseName,Class clazz,String tableName) throws IOException{
		String CLASS=CommonUtils.getFirstUpWord(className);
		String content = FileUtil.read(tempUrl+"Templete_Domain.txt",true);
		
		//获取所有字段
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer sb=new StringBuffer();
		for (Field field : fields) {
			String fieldName = field.getName();
			String typeName = field.getType().getName();
			
			if(typeName.contains("String")){
				sb.append("\tprivate String "+fieldName+";\r\n");
			}else{
				sb.append("\tprivate "+typeName +" "+fieldName+";\r\n");
			}
		}
		
		content=content.replace("@table", tableName).replace("@class", className).replace("@Name", chineseName).replace("@CLASS", CLASS).replace("@field", sb.toString());
		FileUtil.write(classUrl+prefixUrl+className+"/domain/"+CLASS+".java", content);
		
		System.out.println("---------构造Domain 成功");
	}
	
	
	/**
	 * 构造Controller
	 * @throws IOException 
	 * 
	 */
	public static void createController(String className,String chineseName) throws IOException{
		String CLASS=CommonUtils.getFirstUpWord(className);
		String content = FileUtil.read(tempUrl+"Templete_Controller.txt",true);
		content=content.replace("@CLASS", CLASS).replace("@class", className).replace("@Name", chineseName).replace("@prefix", prefix);
		FileUtil.write(classUrl+prefixUrl+className+"/cmd/"+CLASS+"Cmd.java", content);
		
		System.out.println("---------构造Controller 成功");
	}
	
	/**
	 * 构造Service
	 * @throws IOException 
	 * 
	 */
	public static void createService(String className) throws IOException{
		String CLASS=CommonUtils.getFirstUpWord(className);//首字母大写
		
		String content = FileUtil.read(tempUrl+"Templete_Service.txt",true);
		content=content.replace("@CLASS", CLASS).replace("@class", className).replace("@prefix", prefix);
		FileUtil.write(classUrl+prefixUrl+className+"/service/"+CLASS+"Service.java", content);
		
		
		content = FileUtil.read(tempUrl+"Templete_ServiceImpl.txt",true);
		content=content.replace("@CLASS", CLASS).replace("@class", className).replace("@prefix", prefix);
		FileUtil.write(classUrl+prefixUrl+className+"/service/"+CLASS+"ServiceImpl.java", content);
		System.out.println("---------构造Service 成功");
	}
	
	
	/**
	 * 构造List jsp
	 * @param className
	 * @param classNameChinese
	 * @throws IOException
	 */
	public static void createJsp(String className,Class clazz) throws IOException{
		
		constractQueryMap(clazz);
		String queryContent = createQueryContent();
		String fieldContent = createFieldContent();
		String url="${ctx}/wareHouses/"+className+"/"+className+"List";
		String content = FileUtil.read(tempUrl+"Templete_List.txt",true);
		content=content.replace("@query", queryContent).replace("@field", fieldContent).replace("@add", "wareHouses:"+className+":add")
				.replace("@update", "wareHouses:"+className+":update").replace("@delete", "wareHouses:"+className+":delete")
				.replace("@url_add", "wareHouses/"+className+"/create").replace("@url_update", "wareHouses/"+className+"/update").replace("@url_del", "wareHouses/"+className+"/delete").replace("@url", url);
		FileUtil.write(listUrl+prefix+className+"/"+className+"List.jsp", content);
		
		System.out.println("-------------构造List 成功");
		
		
		//构造add 与 update 页面
		String formContent = createFormContent(className);
		content = FileUtil.read(tempUrl+"Templete_Add.txt",true);
		url="${ctx}/mall/"+className;
		content=content.replace("@formContent", formContent).replace("@url", url).replace("@field", fieldContent);
		FileUtil.write(listUrl+prefix+className+"/"+className+"Form.jsp", content);
		
		System.out.println("-------------构造Add成功");
	}
	
	public static String createFormContent(String className){

		StringBuilder sb=new StringBuilder();
		
		for (Map.Entry<String, JSONObject> entry: allMap.entrySet()) {
			
			
			JSONObject obj = entry.getValue();
			int isSelect = obj.getIntValue("isSelect");
			String str="\t<td><input name=\""+entry.getKey()+"\" type=\"text\" value=\"${"+className+"."+entry.getKey()+"}\" class=\"easyui-validatebox\" /></td>\r\n";
			if(isSelect==1){
				str="\t<td>\r\n "+"<select name=\""+entry.getKey()+"\">\r\n<option value=\"\">请选择</option>\r\n</select>"+" \r\n</td>\r\n";
			}
			
			if(StringUtils.equals("id", entry.getKey())){
				str="<input name=\""+entry.getKey()+"\" type=\"hidden\" value=\"${"+className+"."+entry.getKey()+"}\" class=\"easyui-validatebox\"/>";
				sb.append(str+"\r\n");
			}else{
				
				String trS="<tr>\r\n";
				String trE="</tr>\r\n";
				String tdS="\t<td>";
				String tdE="\t</td>\r\n";
				
				sb.append(trS+tdS+obj.getString("fieldName")+":"+tdE+str+trE);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取类的查询构造内容
	 * @param className
	 * @throws IOException
	 */
	public static void constractQueryMap(Class clazz) throws IOException{
		
		//通过反射获取所有字段
		Field[] fields = clazz.getDeclaredFields(); 
		 for(Field f : fields){  
			 
			 f.setAccessible(true);
			 String field = f.getName();
			 String name = f.getType().getName();
			 JSONObject obj=new JSONObject();
			 /**
				 * 规则定义：M表示Math代表数字类型
				 * 规则定义：S表示String代表字符串类型
				 * 规则定义：T表示Time代表时间类型
				 * @author LCM
				 *
				 */
			 
			 
			 if(field.contains("name")){
				 obj.put("queryType", FieldUtils.getCalCulateType(true)+"_"+field);
			 }else{
				 obj.put("queryType",  FieldUtils.getCalCulateType(false)+"_"+field);
			 }
			 
			 
             //获取字段中包含fieldMeta的注解  
			 JspFieldName meta = f.getAnnotation(JspFieldName.class);
             if(meta!=null){  
            	 
            	 obj.put("fieldName", meta.fildName());
            	 obj.put("isSelect", meta.isSelect());
            	 if(meta.needQuery()==1){
            		 queryMap.put(field, obj);
            	 }
             }  
             allMap.put(field,obj);
         } 
		
	}
	
	/**
	 * 构造查询内容
	 * @return
	 */
	public static String createQueryContent(){
		StringBuilder sb=new StringBuilder();
		for (Map.Entry<String, JSONObject> entry: queryMap.entrySet()) {
			JSONObject obj = entry.getValue();
			int isSelect = obj.getIntValue("isSelect");
			String str="\t<input type=\"text\" name=\""+obj.getString("queryType")+"\" class=\"easyui-validatebox\"  value=\"\"  data-options=\"prompt: '"+obj.getString("fieldName")+"'\"/>";
			if(isSelect==1){
				str="\t<select name='"+obj.getString("queryType")+"'>\r\n"+
							"\t\t<option value=\"\">"+obj.getString("fieldName")+"</option>\r\n"+
							"\t\t<option value=\"1\">是</option>\r\n"+
							"\t\t<option value=\"0\">否</option>\r\n"+
							"\t</select>";
			}
			sb.append(str+"\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * 构造字段内容
	 * @return
	 */
	public static String createFieldContent(){
		StringBuilder sb=new StringBuilder();
		for (Map.Entry<String, JSONObject> entry: allMap.entrySet()) {
			JSONObject obj = entry.getValue();
			int isSelect = obj.getIntValue("isSelect");
			String str="{ field : '"+entry.getKey()+"', title : '"+obj.getString("fieldName")+"'},";
			if(isSelect==1){
				str="{field : '"+entry.getKey()+"',title : '"+obj.getString("fieldName")+"',"+
										"formatter : function(value, row, index) {}"+									
						"},";
			}
			sb.append(str+"\r\n");
		}
		return sb.toString();
	}
	
}
