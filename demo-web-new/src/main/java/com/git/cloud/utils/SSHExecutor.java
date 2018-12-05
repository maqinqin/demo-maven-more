package com.git.cloud.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 
 * <p>
 * 执行ssh工具类
 * 
 * @author liufei
 * @version 1.0 2013-5-22
 * @see
 */
public class SSHExecutor {
	private static Logger logger = LoggerFactory.getLogger(SSHExecutor.class);
	public static void executeNoResult(String cmd, String host, String user, String password, int port)
			throws Exception {
		ChannelExec channelExec = null;
		Session session = null;
		JSch jsch = null;
		try {
			jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

			channelExec = (ChannelExec) session.openChannel("exec");

			channelExec.setCommand(cmd);
			channelExec.connect();
			// 等待10秒钟
			Thread.sleep(10 * 1000);

		} catch (Exception e) {
			throw e;
		} finally {
			if (channelExec != null && !channelExec.isClosed()) {
				channelExec.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
	}

	public static String execute(String cmd, String host, String user, String password, int port) throws Exception {
		StringBuffer buf = new StringBuffer();
		ChannelExec channelExec = null;
		Session session = null;
		JSch jsch = null;
		BufferedReader reader = null;
		BufferedReader errorReader = null;
		try {
			jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setTimeout(60000); // 设置timeout时间
			session.connect();

			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setInputStream(null);
			channelExec.setErrStream(null);
			InputStream in = channelExec.getInputStream();
			InputStream error = channelExec.getErrStream();
			
			channelExec.setCommand(cmd);
			channelExec.connect();

			errorReader = new BufferedReader(new InputStreamReader(error, Charset.forName("utf-8")));
			String errorLine = null;
			while ((errorLine = errorReader.readLine()) != null) {
				buf.append(errorLine).append("\n");
			}
			

			reader = new BufferedReader(new InputStreamReader(in, Charset.forName("utf-8")));
			String line = null;
//			int lineNum = 0;
			while ((line = reader.readLine()) != null) {
				buf.append(line).append("\n");
//				lineNum++;
//				System.out.println("line:" + lineNum + "###" + line);
			}
			error.close();
			in.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (errorReader != null) {
				errorReader.close();
			}
			if (channelExec != null && !channelExec.isClosed()) {
				channelExec.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}

		return buf.toString();
	}

	public static void main(String[] args) {
		try {
			// String cmd =
			// "openssl123 x509 -in /etc/vmware/ssl/rui.crt -fingerprint -sha1 -noout";
			// String cmd =
			// "grep 52:54:00:ca:cb:62 /var/lib/libvirt/dnsmasq/default.leases;ls -l"
			// ;
			String cmd = "ls -l /home/libvirt/images/trest";
			// String cmd = "adb -lasdfs /";
			// String cmd =
			// /*"esxcli network ip interface tag add -i vmk0 -t Management";*/
			// "esxcli network ip interface tag add -i vmk0 -t Management;";
			/* "esxcli network ip interface tag get -i vmk0"; */
			/* "esxcli network ip interface tag remove -i vmk0 -t Management"; */
			String result = execute(cmd, "10.100.101.1", "root", "1q2w3e4r", 22);
			if (result != null && !result.equalsIgnoreCase("")) {
				System.out.print("******" + result);
				// result = result.substring(result.indexOf("=")+1);
			}
			// System.out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("1---------------");
			logger.error("异常exception",e);
			System.out.println("2---------------");

		}
	}
}
