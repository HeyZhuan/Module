package com.Fee.common.security;

import java.util.Random;

/**
 * 生成密码-密钥
 * 
 * @author
 * 
 */
public class PawUtils {

	private static final char[] NumberStr = "0123456789".toCharArray();
	private static final char[] LetterStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] numAndLetStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	/**
	 * 获取字符串数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(NumberStr[ran.nextInt(10)]);
		}
		return sb.toString();
	}

	/**
	 * 获取字母字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomLetter(int length) {
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(LetterStr[ran.nextInt(52)]);
		}
		return sb.toString();
	}

	/**
	 * 获取数字字母字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumAndLetter(int length) {
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(numAndLetStr[ran.nextInt(62)]);
		}
		return sb.toString();
	}

}
