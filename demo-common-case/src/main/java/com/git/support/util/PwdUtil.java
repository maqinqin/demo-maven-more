package com.git.support.util;

import java.util.Random;

import com.git.support.constants.PubConstants;

public class PwdUtil {
	private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static char[] randomChars ="0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ~@#%^&*_+-={}[]\\:;'".toCharArray();
	/**
	 * 随机生成密码
	 */
	public static String getRandomPwd() {

		Random randGen = null;
		randGen = new Random();
		char [] randBuffer = new char[10];
		for (int i=0; i<randBuffer.length; i++) {
			randBuffer[i] = PwdUtil.numbersAndLetters[randGen.nextInt(PwdUtil.numbersAndLetters.length)];
			//randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];		          //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];		         }
		}
		return new String(randBuffer);
	}

	/**
	 * 随机生成密码
	 */
	public static String getRandomPwd2() {
		Random randGen = new Random();
		StringBuffer pwd = new StringBuffer();
		for (int i=0; i<8; i++) {
			pwd.append(PwdUtil.randomChars[randGen.nextInt(PwdUtil.randomChars.length)]);
		}
		int level=3;
		if(PwdUtil.checkPWD(level,pwd.toString())) {
			return pwd.toString();
		}
		return PwdUtil.getRandomPwd2();
	}

	/**
	 * 验证密码
	 * @param level
	 * @param pwd
	 * @return
	 */
	public static boolean checkPWD(int level,String pwd) {
		if((pwd==null)||pwd.equals("")) {
			return false;
		}
		boolean[] check= {false,false,false,false};
		for(int i=pwd.length()-1;i>=0;i--) {
			char c=pwd.charAt(i);
			if((c>='0')&&(c<='9')) {
				check[0]=true;
			}else if((c>='a')&&(c<='z')) {
				check[1]=true;
			}else if((c>='A')&&(c<='Z')) {
				check[2]=true;
			}else {
				check[3]=true;
			}
		}
		for(boolean chk:check) {
			if(chk) {
				level--;
			}
		}
		if(level>0) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 获取统一密码
	 */
	public static String getDefaultPwd() {
		String pwd = "iomp_pw123456";

		return pwd;
	}

	/**
	 * 加密
	 */
	public static String encryption(String src) {
		String requestValue = Md5Util
				.encryption(src, PubConstants.PASSWORD_KEY);

		return requestValue;
	}

	/**
	 * 解密
	 */
	public static String decryption(String src) {
		String requestValue = Md5Util
				.decryption(src, PubConstants.PASSWORD_KEY);

		return requestValue;
	}
}
