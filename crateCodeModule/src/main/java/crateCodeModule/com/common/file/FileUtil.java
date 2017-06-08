package crateCodeModule.com.common.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

/**
 * 文件工具类
 * @author Zhuan
 *
 */
public class FileUtil {
	
	/**
	 * 读取文件内容
	 * @param fileAddress 文件地址
	 * @return
	 * @throws IOException 
	 */
	public static String read(String fileAddress,boolean addEnter) throws IOException{
		if(StringUtils.isNotBlank(fileAddress)){
			File file=new File(fileAddress);
			if(file.exists()){
				Scanner in = new Scanner(new File(fileAddress));
				StringBuffer sb = new StringBuffer();
				while (in.hasNext()) {
					String str = in.nextLine();
					if (addEnter) {
						sb.append(str + "\r\n");
					} else {
						sb.append(str.trim());
					}
				}
				in.close();
				return sb.toString();
			}
		}else{
			System.out.println("文件读取，地址："+fileAddress+",地址为空");
		}
		return "";
	}
	
	
	
	/**
	 * 写入文件内容
	 * @param fileAddress 文件地址
	 * @return
	 * @throws IOException 
	 */
	public static void write(String fileAddress,String content) throws IOException{
		if(StringUtils.isNotBlank(fileAddress)){
			File file=new File(fileAddress);
			
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
		}else{
			System.out.println("文件写入，地址："+fileAddress+",地址为空");
		}
	}
	
	/**
	 * 替换对用的内容
	 * @param content 内容
	 * @param map 替换的元素
	 * @return
	 */
	public static String replaceContent(String content,Map<String, String> map){
		for (Entry<String, String> entry: map.entrySet()) {
			content=content.replace(entry.getKey(), entry.getValue());
		}
		return content;
	}
	
	/**
	 * 获取替换的元素
	 * @param address
	 * @return  key 为要替换的旧元素，value 为替换的新元素
	 * @throws IOException 
	 */
	public static Map<String,String> getReplaceContent(String address,String className,String chineseName) throws IOException{
		
		String fileContent = FileUtil.read(address,false);
		String[] contents = fileContent.split("replace");
		String[] oldContents=contents[0].split("fengexian");//被替换的内容
		String[] newContents=contents[1].split("fengexian");//替换的新内容
		Map<String,String> contentMap=new HashMap<>();
		for (int i = 0; i < oldContents.length; i++) {
			String newCon = newContents[i];
			newCon=addEnter(newCon);
			newCon=newCon.replace("templete", className)
					.replace("TEMP", className.toUpperCase())
					.replace("Templete",CommonUtils.getFirstUpWord(className))
					.replace("tempChinese", chineseName);//替换掉对应的模板信息
			newCon=oldContents[i]+"\r\n"+newCon+"\r\n";//旧内容与新内容结合进行替代旧内容
			contentMap.put(oldContents[i].trim(), newCon);
		}
		return contentMap;
	}
	
	private static String addEnter(String content){
		String[] strs = content.split("enter");
		StringBuilder sb=new StringBuilder();
		if(strs.length>1){
			for (String string : strs) {
				if(string.contains("tab")){
					string=string.replace("tab", "");
					sb.append("\t"+string+"\r\n");
				}else{
					sb.append(string+"\r\n");
				}
			}
		}else{
			sb.append(strs[0]);
		}
		return sb.toString();
	}
}
