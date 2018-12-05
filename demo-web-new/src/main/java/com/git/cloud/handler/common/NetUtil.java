/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <p>
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-6-21
 * @see
 */
public class NetUtil {

	/**
	 * Get host IP address
	 * 
	 * @return IP Address
	 */
	public static InetAddress getAddress() throws Exception {
		try {
			for (Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces(); interfaces.hasMoreElements();) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback()
						|| networkInterface.isVirtual()
						|| !networkInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = networkInterface
						.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) addresses
							.nextElement();
					if (inetAddress.getHostAddress().indexOf(".") > -1) {
						return inetAddress;
					}
				}
			}
		} catch (SocketException e) {
			throw e;
		}
		return null;
	}

	/**
	 * ping linux
	 * 
	 * @param ip
	 * @return true:成功连通,false:失败
	 */
	public static boolean doPingForLinux(String ip, int pingCount)
			throws Exception {
		try {
			String result = Utils.invokeLocalCmd("ping -c " + pingCount + " "
					+ ip);
			String startStr = "transmitted, ";
			String endStr = " received,";
			int pos1 = result.indexOf(startStr);
			int pos2 = result.indexOf(endStr, pos1);
			String receivedCount = result.substring(pos1 + startStr.length(),
					pos2);
			int count = Integer.parseInt(receivedCount);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Utils.printExceptionStack(e);
			throw e;
		}
	}

	/**
	 * 获取网段
	 * 
	 * @param ip
	 * @param mask
	 * @return
	 */
	public static String getNetAddr(String ip, String mask) {
		String[] aryIp = ip.split("\\.");
		String[] aryMask = mask.split("\\.");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int oneip = Integer.valueOf(aryIp[i]);
			int onemask = Integer.valueOf(aryMask[i]);
			sb.append(String.valueOf(oneip & onemask));
			if (i != 3) {
				sb.append(".");
			}
		}
		return sb.toString();
	}

	public static boolean isIPV4(String str) {
		String regex = "^(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		String str = "2.2.2.3";
		System.out.println(NetUtil.isIPV4(str));
		str = "10.220.20.6";
		System.out.println(NetUtil.isIPV4(str));
		str = "10.220.20.203";
		System.out.println(NetUtil.isIPV4(str));
		str = "fe80::250:56ff:fe94:effa";
		System.out.println(NetUtil.isIPV4(str));
		
	}
}
