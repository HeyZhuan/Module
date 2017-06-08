package crateCodeModule.com.common.file;

import java.io.File;
import java.util.Random;

/**
 * 通用的工具类
 * @author Zhuan
 *
 */
public class CommonUtils {

	/**
	 * 获取首字母大写的单词
	 * @param name
	 * @return
	 */
	public static String getFirstUpWord(String name){
		return name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toUpperCase()) ;
	}
	
	/**
	 * 对应的目录是否存在,不存在的创建该目录
	 * @param url
	 * @return
	 */
	public static void directoryExist(String url){
		File file=new File(url);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 获取对应长度的随机数
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length){
		char[] numAndLetStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(numAndLetStr[ran.nextInt(62)]);
		}
		return sb.toString();
	}
}
