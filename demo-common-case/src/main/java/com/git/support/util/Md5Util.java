package com.git.support.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class Md5Util {

	/**
	 * 加密
	 */
	public static String encryption(String src, String key) {
		String requestValue = "";
		try {

			byte[] enKey = getEnKey(key);
			byte[] src2 = src.getBytes("UTF-16LE");
			byte[] encryptedData = Encrypt(src2, enKey);

			String base64String = getBase64Encode(encryptedData);
			String base64Encrypt = filter(base64String);
			requestValue = getURLEncode(base64Encrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	/**
	 * 解密
	 */
	public static String decryption(String src, String spkey) {
		String requestValue = "";
		try {

			String URLValue = getURLDecoderdecode(src);
//			BASE64Decoder base64Decode = new BASE64Decoder();
//			byte[] base64DValue = base64Decode.decodeBuffer(URLValue);
			Decoder decoder = Base64.getDecoder();
			byte[] base64DValue = decoder.decode(URLValue);
			requestValue = deCrypt(base64DValue, spkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	public static void main(String[] arg) {
		String input = "123456";
		System.out.println("input----" + input);
		String input_md5 = Md5Util.encryption(input, "vmware");
		System.out.println("input_md5----" + input_md5);
		String input_md52 = Md5Util.encryption(input, "root");
		System.out.println("input_md5----" + input_md52);
		String output = Md5Util.decryption(input_md5, "CCBCLOUD");
		System.out.println("output----" + output);
	}

	private static byte[] md5(String strSrc) {
		byte[] returnByte = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnByte = md5.digest(strSrc.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnByte;
	}

	private static byte[] getEnKey(String spKey) {
		byte[] desKey = null;
		try {
			byte[] desKey1 = md5(spKey);
			desKey = new byte[24];
			int i = 0;
			while (i < desKey1.length && i < 24) {
				desKey[i] = desKey1[i];
				i++;
			}
			if (i < 24) {
				desKey[i] = 0;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desKey;
	}

	private static byte[] Encrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}

	private static String getBase64Encode(byte[] src) {
		String requestValue = "";
		try {
//			BASE64Encoder base64en = new BASE64Encoder();
//			requestValue = base64en.encode(src);
			Encoder encoder = Base64.getEncoder();
			requestValue = encoder.encodeToString(src);
			// System.out.println(requestValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	private static String filter(String str) {
		String output = null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13)
				sb.append(str.subSequence(i, i + 1));
		}
		output = new String(sb);
		return output;
	}

	private static String getURLEncode(String src) {
		String requestValue = "";
		try {
			requestValue = URLEncoder.encode(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	private static String getURLDecoderdecode(String src) {
		String requestValue = "";
		try {
			requestValue = URLDecoder.decode(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	private static String deCrypt(byte[] debase64, String spKey) {
		String strDe = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede");
			byte[] key = getEnKey(spKey);
			DESedeKeySpec dks = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey sKey = keyFactory.generateSecret(dks);
			cipher.init(Cipher.DECRYPT_MODE, sKey);
			byte ciphertext[] = cipher.doFinal(debase64);
			strDe = new String(ciphertext, "UTF-16LE");
		} catch (Exception ex) {
			strDe = "";
			ex.printStackTrace();
		}
		return strDe;
	}

}
