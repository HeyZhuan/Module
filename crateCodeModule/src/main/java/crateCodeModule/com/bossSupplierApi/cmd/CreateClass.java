package crateCodeModule.com.bossSupplierApi.cmd;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import crateCodeModule.com.common.file.CommonUtils;
import crateCodeModule.com.common.file.FileUtil;

@Service
public class CreateClass {
	
	//要生成的service文件路径
	private static final String serviceUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/java/xinxing/boss/supplier/supplierapi/phonetraffic/others/";
	//要生成的配置文件路径
	private static final String propertiesUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/webapp/WEB-INF/config/properties/supplier/";
	//PhonetrafficSupplierServiceImpl 类路径
	private static final String phoneTrafficSupplierUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/java/xinxing/boss/supplier/supplierapi/phonetraffic/service/PhonetrafficSupplierServiceImpl.java";
	//回调 SupplierCallback 路径
	private static final String supplierCallbackUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/java/xinxing/boss/supplier/api/callback/SupplierCallback.java";
	//commonContext.xml 的路径
	private static final String commonContextUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/webapp/WEB-INF/config/spring/common_Context.xml";
	//service.xml 路径
	private static final String serviceXmlUrl="F:/Eclipse_WorkSpace/BossSupplierApi Maven Webapp/src/main/webapp/WEB-INF/config/spring/service_Context.xml";
	
	//templete文件路径
	private static final String tempUrl="F:/git_work/crateCodeModule/src/main/java/crateCodeModule/com/bossSupplierApi/templete/";
	
	
	public static void main(String[] args) {
		String className="szgzx"; //类名--英文--小写
		String classNameChinese="深圳广众信";    //中文名
		
		try {
			createService(className, classNameChinese);
			createProperties(className);
			replacePhoneSupplier(className);
			replaceCallback(className, classNameChinese);
			replaceConfig(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造供应商service，impl，util 类
	 * @param className
	 * @param classNameChinese
	 * @throws IOException
	 */
	public static void createService(String className,String classNameChinese) throws IOException{
		//templete,Templete,TempleteChinese
		/*//生成对应的文件夹
		String url=serviceUrl+className;
		CommonUtils.directoryExist(url);
		String serviceUrl=url+"/service";
		CommonUtils.directoryExist(serviceUrl);
		String utilUrl=url+"/util";
		CommonUtils.directoryExist(utilUrl);*/
		
		String fName=CommonUtils.getFirstUpWord(className);
		String[] cName=new String[]{"Templete_Service.txt","Templete_ServiceImpl.txt","Templete_Util.txt"};
		String[] cNameAddress=new String[]{"/service/"+fName+"_Service.java","/service/"+fName+"_ServiceImpl.java","/util/"+fName+"_Util.java"};
		
		int i=0;
		for (String name : cName) {
			String content = FileUtil.read(tempUrl+name,true);
			content=content.replace("TempleteChinese", classNameChinese).replace("templete", className).replace("Templete", fName);
			FileUtil.write(serviceUrl+className+cNameAddress[i], content);
			i++;
		}
		System.out.println("生成Service,Impl,Util成功");
	}
	
	/**
	 * 构造配置文件
	 * @param className
	 * @throws IOException
	 */
	public static void createProperties(String className) throws IOException{
		String content = FileUtil.read(tempUrl+"templete_properties.txt",true);
		content=content.replace("templete", className).replace("tempapikey", CommonUtils.getRandomNum(20)).replace("TEMPLETE", className.toUpperCase());
		FileUtil.write(propertiesUrl+className+"_setting.properties", content);
		System.out.println("生成properties 成功");
	}
	
	/**
	 * 替换增加发送的注解及对应的入口--PhoneSupplier
	 * @param className
	 * @throws IOException 
	 */
	public static void replacePhoneSupplier(String className) throws IOException{
		
		//获取替换内容
		String replaceUrl=tempUrl+"PhonetrafficSupplierServiceImpl.txt";
		Map<String, String> contentMap = FileUtil.getReplaceContent(replaceUrl, className,"");
		String content = FileUtil.read(phoneTrafficSupplierUrl,true);
		content=FileUtil.replaceContent(content, contentMap);
		FileUtil.write(phoneTrafficSupplierUrl, content );
		System.out.println("替换 phoneTrafficSupplier 成功");
	}
	
	
	/**
	 * 回调内容的替换
	 * @param className
	 * @throws IOException 
	 */
	public static void replaceCallback(String className,String classChineseName) throws IOException{
		
		//获取替换内容
		String replaceUrl=tempUrl+"SupplierCallback.txt";
		Map<String, String> contentMap = FileUtil.getReplaceContent(replaceUrl, className,classChineseName);
		String content = FileUtil.read(supplierCallbackUrl,true);
		content=FileUtil.replaceContent(content, contentMap);
		FileUtil.write(supplierCallbackUrl, content );
		System.out.println("替换 phoneTrafficSupplier 成功");
	}
	
	/**
	 * xml 文件中的替换--包括commonComtext,junit,service
	 * @param className
	 * @throws IOException 
	 */
	public static void replaceConfig(String className) throws IOException{
		
		//获取替换内容
		String content = FileUtil.read(commonContextUrl,true);
		String beReplace="<value>/WEB-INF/config/properties/supplier/shby_setting</value>";
		String replace="<value>/WEB-INF/config/properties/supplier/"+className+"_setting</value>";
		content=content.replace(beReplace, beReplace+"\r\n\t\t"+replace);
		FileUtil.write(commonContextUrl, content );
		System.out.println("替换 commonContext 成功");
		
		content = FileUtil.read(serviceXmlUrl,true);
		beReplace="<bean id=\"Shle_Service\" class=\"xinxing.boss.supplier.supplierapi.phonetraffic.others.shle.service.Shle_ServiceImpl\"></bean>";
		replace="<bean id=\""+CommonUtils.getFirstUpWord(className)+"_Service\" class=\"xinxing.boss.supplier.supplierapi.phonetraffic.others."+className+".service."+CommonUtils.getFirstUpWord(className)+"_ServiceImpl\"></bean>";
		content=content.replace(beReplace, beReplace+"\r\n\t"+replace);
		FileUtil.write(serviceXmlUrl, content );
		System.out.println("替换 commonContext 成功");
		
	}
	
	
}
