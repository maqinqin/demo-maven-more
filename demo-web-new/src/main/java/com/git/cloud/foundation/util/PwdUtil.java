package com.git.cloud.foundation.util;

import java.util.HashMap;
import java.util.Random;

/**
 * 加密解密字符串
 * @author liangshuang
 * @date 2014-9-17 上午11:06:27
 * @version v1.0
 *
 */
public class PwdUtil {
	
	private static String PASSWORD_KEY="ICMSCLOUD";
	/**
	 * 随机生成密码
	 */
	public static String getRandomPwd() {
		
		Random randGen = null;
		randGen = new Random();		               
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		 char [] randBuffer = new char[10];		        
		 for (int i=0; i<randBuffer.length; i++) {		             
			 randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];		          //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];		         }
		 }
		return new String(randBuffer);
	}

	/**
	 * 获取统一密码
	 */
	public static String getDefaultPwd() {
		String pwd = "icms_pw123456";

		return pwd;
	}

	/**
	 * 加密
	 */
	public static String encryption(String src) {
		String requestValue = Md5Util
				.encryption(src, PASSWORD_KEY);

		return requestValue;
	}

	/**
	 * 解密
	 */
	public static String decryption(String src) {
		String requestValue = Md5Util
				.decryption(src, PASSWORD_KEY);

		return requestValue;
	}
	
	public static void main(String[] args){
		System.out.println(PwdUtil.encryption("password"));
//		System.out.println(PwdUtil.encryption("Huawei@123"));
		System.out.println(PwdUtil.decryption("UCLj9wiP5Zz9LtfpfjeTG9TZenN5fng1"));
		//jxpCCtd3AZAZeb0VS14vQNTZenN5fng1
	}
}
