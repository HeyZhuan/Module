package crateCodeModule.com.galrami.cmd;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import crateCodeModule.com.common.file.CommonUtils;
import crateCodeModule.com.common.file.FileUtil;
import crateCodeModule.com.common.http.HttpUtils;
import crateCodeModule.com.galrami.classes.AppUserScoreChangeRecord;
import crateCodeModule.com.galrami.util.FieldUtils;
import crateCodeModule.com.galrami.util.JspFieldName;

@Service
public class CreateClass {
	
	//要生成的jsp文件路径
	private static final String listUrl="F:/Eclipse_WorkSpace/glareme-parent/glareme-admin/src/main/webapp/WEB-INF/views/mall/";
	//要生成的Service路径
	private static final String serviceUrl="F:/Eclipse_WorkSpace/glareme-parent/glareme-core/src/main/java/com/glareme/core/service/business/service/";
	//要生成的Controller路径
	private static final String controllerUrl="F:/Eclipse_WorkSpace/glareme-parent/glareme-admin/src/main/java/com/glareme/admin/mall/";
	//要生成的System_context路径
	private static final String system_contextUrl="F:/Eclipse_WorkSpace/glareme-parent/glareme-admin/src/main/webapp/WEB-INF/config/spring/system_Context.xml";
	
	//templete文件路径
	private static final String tempUrl="F:/git_work/crateCodeModule/src/main/java/crateCodeModule/com/galrami/templete/";
	
	
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
		String className="appUserScoreChangeRecord"; //类名--英文--小写
		String chineseName="微信小程序分享获取分数记录";//
		Class clazz=AppUserScoreChangeRecord.class;// 实体类
		int pid=502;//菜单的上一级目录
		prefix=".app";
		prefixUrl="app/";
		try {
			
			createService(className);
			createController(className,chineseName);
			createJsp(className,clazz);
			replaceSystem_Context(className);
			
			//生成对应权限
			HttpUtils.sendGet("http://localhost:8080/glareme-admin/data/addMenu?pid="+pid+"&name="+chineseName+"&className="+className, "UTF-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void replaceSystem_Context(String className) throws IOException{
		String CLASS=CommonUtils.getFirstUpWord(className);
		//替换system_contenxt 中的内容，添加注解
		String content = FileUtil.read(system_contextUrl,true);
		String replaceContent="<bean class=\"com.glareme.core.service.business.service.app.appArticleCategory.AppArticleCategoryServiceImpl\"></bean>\r\n"+
					"\t<bean class=\"com.glareme.core.service.business.service"+prefix+"."+className+"."+CLASS+"ServiceImpl\"></bean>";
		content=content.replace("<bean class=\"com.glareme.core.service.business.service.app.appArticleCategory.AppArticleCategoryServiceImpl\"></bean>", replaceContent);
		FileUtil.write(system_contextUrl, content);
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
		FileUtil.write(controllerUrl+prefixUrl+className+"/"+CLASS+"Controller.java", content);
		
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
		FileUtil.write(serviceUrl+prefixUrl+className+"/"+CLASS+"Service.java", content);
		
		
		content = FileUtil.read(tempUrl+"Templete_ServiceImpl.txt",true);
		content=content.replace("@CLASS", CLASS).replace("@class", className).replace("@prefix", prefix);
		FileUtil.write(serviceUrl+prefixUrl+className+"/"+CLASS+"ServiceImpl.java", content);
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
		String url="${ctx}/mall/"+className+"/"+className+"List";
		String content = FileUtil.read(tempUrl+"Templete_List.txt",true);
		content=content.replace("@query", queryContent).replace("@url", url).replace("@field", fieldContent).replace("@add", "mall:"+className+":add")
				.replace("@update", "mall:"+className+":update").replace("@add", "mall:"+className+":delete")
				.replace("@addUrl", "mall/"+className+"/create").replace("@updateUrl", "mall/"+className+"/update").replace("@deleteUrl", "mall/"+className+"/delete");
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
			String str="\t<td><input name=\""+entry.getKey()+"\" type=\"text\" value=\"${"+className+"."+entry.getKey()+"}\" class=\"easyui-validatebox\" data-options=\"required:'required'\"/></td>\r\n";
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
				 obj.put("queryType", FieldUtils.getQueryType(true,name,field));
			 }else{
				 obj.put("queryType",  FieldUtils.getQueryType(false,name,field));
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
			String str="<input type=\"text\" name=\""+obj.getString("queryType")+"\" class=\"easyui-validatebox\"  value=\"\"  data-options=\"prompt: '"+obj.getString("fieldName")+"'\"/>";
			if(isSelect==1){
				str="<select name='"+obj.getString("queryType")+"'>"+
							"<option value=\"\">"+obj.getString("fieldName")+"</option>"+
							"<option value=\"1\">是</option>"+
							"<option value=\"0\">否</option>"+
							"</select>";
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
