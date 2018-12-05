package com.git.cloud.handler.automation.se.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.git.cloud.handler.automation.se.vo.SshExecShellVo;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

public class SshExecShell {
	
	private static Logger logger = LoggerFactory
			.getLogger(SshExecShell.class);
	
	private Connection conn;
	
	private Session session = null;
	
	private BufferedReader reader = null;
	
	private List<String> resultList = null;
	
	private List<String> errorList = null;
	
	private String status = null;
	
	
	public Connection getConnection(String ip, String user, String pwd, Boolean isKey, String keyFileUrl, String keyPWD) throws Exception {
		conn = new Connection(ip);
		try {
			conn.connect();
			boolean isAuth = false;
			isAuth = conn.authenticateWithPassword(user, pwd);
			if (!isAuth) {
				conn.close();
				throw new Exception("User(" + user + ")连接IP(" + ip
						+ ")SSH2认证失败！");
			}
		} catch (IOException e) {
			conn.close();
			throw new Exception("User(" + user + ")连接IP(" + ip
					+ ")SSH2失败！请联系系统管理员。" + e);
		}
		return conn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<List<String>> execShell(String ip,String user,String pwd,List<String> cmds) throws Exception {
		Connection con = null;
		List list = null;
		logger.info("ip:"+ip);
		logger.info("user:"+user);		
		logger.info("pwd:"+pwd);

		String charSet = "UTF-8";//ParameterService.getParameter("CharSet");
		if (charSet == null || "".equals(charSet)) {
			throw new Exception("get CharSet from property file Failed");
		}

		try {
			con = getConnection(ip, user, pwd, false, null, null);
		} catch (Exception e) {
			logger.info("SSH2创建连接失败", e);
			throw new Exception("SSH2创建连接失败！请联系系统管理员." + e.getMessage(), e);
		}

		try{
			list = execCommands(con,cmds,charSet,"");
		}catch(Exception e){
			logger.error("出现错误"+e);
			throw new Exception(e.getMessage());
		}finally{
			if(con!=null){
				con.close();
				con = null;
			}
		}
		return list;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List execCommands(Connection con, List cmd, String charSet,
			String execFlag) throws Exception {

		List ls = new ArrayList();

		if (con != null && cmd != null) {

			boolean hasError = false;
			StringBuilder errors = new StringBuilder("");

			for (int k = 0; k < cmd.size(); k++) {

				HashMap resultMap = new HashMap();
				List<String> detailInfoLs = new ArrayList<String>();
				List<String> errorInfoLs = new ArrayList<String>();
				resultMap.put("ECHO_INFO", detailInfoLs);
				resultMap.put("ERROR_INFO", errorInfoLs);
				ls.add(resultMap);

				String cmdStr = cmd.get(k).toString();
				Session session = con.openSession();

				try {
					session.execCommand(cmdStr);

					InputStream stdout = new StreamGobbler(session.getStdout());
					InputStream stderr = new StreamGobbler(session.getStderr());

					BufferedReader stdoutReader = new BufferedReader(
							new InputStreamReader(stdout, charSet));
					BufferedReader stderrReader = new BufferedReader(
							new InputStreamReader(stderr, charSet));

					while (true) {
						String line = stdoutReader.readLine();
						//logger.debug("stdoutLine:" + line);

						if (line == null)
							break;

						detailInfoLs.add(line);

					}

					stdoutReader.close();

					while (true) {
						String line = stderrReader.readLine();
						logger.debug("stderrLine:" + line);
						if (line == null)
							break;
						errorInfoLs.add(line);

					}
					stderrReader.close();

					String exitStatus = String
							.valueOf(session.getExitStatus() == null ? 0
									: session.getExitStatus().intValue());

					resultMap.put("EXIT_CODE", exitStatus);

					if (!"0".equals(exitStatus)) {
						hasError = true;
						logger.error("命令[" + cmdStr + "]执行失败,返回码为:"
								+ exitStatus);
						for (int ct = 0; ct < errorInfoLs.size(); ct++) {
							errors.append(errorInfoLs.get(ct) + ";");
						}
					}
					if ("dependent".equals(execFlag)) {

						break;
					}

				} catch (Exception e) {

					String msg = "SSH2命令执行失败！请联系系统管理员。";
					logger.error("异常exception",e);
					logger.error(msg, e);
					throw new Exception(msg + e.getMessage());

				} finally {

					if (session != null) {
						session.close();
						session = null;
					}
				}
			}
			if (hasError) {
				logger.error("执行失败信息：" + errors.toString());
			}
		} else {
			throw new Exception("SSH2连接或执行命令为null！");
		}

		return ls;

	}
	
	public HashMap<String, Object> execCommand(Connection con, String cmdStr, String charSet) throws Exception {

		HashMap<String, Object> resultMap = null;
		
		if (con != null && cmdStr != null) {
			boolean hasError = false;
			StringBuilder errors = new StringBuilder("");

			resultMap = new HashMap<String, Object>();
			List<String> detailInfoLs = new ArrayList<String>();
			List<String> errorInfoLs = new ArrayList<String>();
			resultMap.put("ECHO_INFO", detailInfoLs);
			resultMap.put("ERROR_INFO", errorInfoLs);

			Session session = con.openSession();

			try {
				session.execCommand(cmdStr);

				InputStream stdout = new StreamGobbler(session.getStdout());
				InputStream stderr = new StreamGobbler(session.getStderr());

				BufferedReader stdoutReader = new BufferedReader(
						new InputStreamReader(stdout, charSet));
				BufferedReader stderrReader = new BufferedReader(
						new InputStreamReader(stderr, charSet));

				while (true) {
					String line = stdoutReader.readLine();
					//logger.debug("stdoutLine:" + line);

					if (line == null)
						break;

					detailInfoLs.add(line);

				}

				stdoutReader.close();

				while (true) {
					String line = stderrReader.readLine();
					logger.debug("stderrLine:" + line);
					if (line == null)
						break;
					errorInfoLs.add(line);

				}
				stderrReader.close();

				String exitStatus = String
						.valueOf(session.getExitStatus() == null ? 0
								: session.getExitStatus().intValue());

				resultMap.put("EXIT_CODE", exitStatus);

				if (!"0".equals(exitStatus)) {
					hasError = true;
					logger.error("命令[" + cmdStr + "]执行失败,返回码为:"
							+ exitStatus);
					for (int ct = 0; ct < errorInfoLs.size(); ct++) {
						errors.append(errorInfoLs.get(ct) + ";");
					}
				}
			} catch (Exception e) {

				String msg = "SSH2命令执行失败！请联系系统管理员。";
				logger.error("异常exception",e);
				logger.error(msg, e);
				throw new Exception(msg + e.getMessage());

			} finally {

				if (session != null) {
					session.close();
					session = null;
				}
			}
			if (hasError) {
				logger.error("执行失败信息：" + errors.toString());
			}
		} else {
			throw new Exception("SSH2连接或执行命令为null！");
		}

		return resultMap;

	}
	/**
	 * 
	 * @param SshExecShellVo
	 * @throws Exception
	 * 
	 * Author: liyongjie
	 * Modify: add this method
	 * Date:   2013-08-20
	 */
	public List<String> execShellCmd(SshExecShellVo vo) throws Exception {
		Connection con = null;
		
		String chareSet = "UTF-8";
		if (chareSet == null || "".equals(chareSet)) {
			throw new Exception("Get CharSet From Property File Failed");
		}
		
		try {
			con = getConnection(vo.getIp(), vo.getUser(), vo.getPwd(), vo.getIsKey(), vo.getKeyFileUrl(), vo.getKeyPWD());
		} catch (Exception e) {
			logger.error("Connect to IP: " + vo.getIp() + ", UserName: " + vo.getUser() + " Fail." + e.toString());
			throw new Exception("SSH2 Connect Fail" + e.getMessage(), e);
		}
		
		try {
			String cmd = vo.getCmd();
			if (null == cmd || "".equals(cmd)) {
				throw new Exception("Command: " + cmd + " Wrong");
			}
			
			session = con.openSession();
			logger.info("Execute Script Commond is : [ " + cmd + " ]");
			session.execCommand(cmd);
			
			reader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout()), chareSet));
			String line = null;
			resultList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				resultList.add(line);
			}
			logger.info("Execute Script Info Message: " + resultList);
			
			reader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr()), chareSet));
			errorList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				errorList.add(line);
			}
			logger.info("Execute Script Error Message: " + errorList);
			
			status = String.valueOf(session.getExitStatus()==null?0:session.getExitStatus().intValue());
			if (Integer.parseInt(status) != 0 || !errorList.isEmpty()) {
				logger.error("Execute Script Return Code:" + status);
				throw new Exception("Execute Script Return Code : " + status
						+ ", Error Message: " + errorList);
			}
			
			logger.info("Execute Script Success, Return Code:" + status);
		} catch (IOException e) {
			logger.error("Execute Scripte Fail", e);
			throw new Exception("Execute Script Fail" + e.getMessage(), e);
		} finally {
			try {
				if(reader != null){
					reader.close();
					reader = null;
				}
				if(session != null){
					session.close();
					session = null;
				}
				if(con != null){
					con.close();
					con = null;
				}
			} catch (IOException e) {
				logger.error("IO Error",e);
				throw new Exception("Finally Close Connection Failed." + e.getMessage(), e);	
			}
		}
		
		return resultList;
	}
	

	public static void main(String[] args){
		SshExecShell ssh = new SshExecShell();
		String ip = "128.192.162.69";
		String user = "root";
		String pwd = "password";

		ip ="128.192.161.82";
		user = "admin";
		pwd = "admin123";
		List<String> cmd_list = new ArrayList<String>();
//		cmd_list.add("echo $PATH");
//		cmd_list.add("raidcom");
//		cmd_list.add("echo $HORCMINST");
//		cmd_list.add("/HORCM/usr/bin/raidcom -login maintenance raid-mainte -s 55579");
//		cmd_list.add("raidcom modify host_grp -port CL1-A HG_test01 -host_mode HP-UX");
//		cmd_list.add("raidcom -logout");
		try {
		List l = ssh.execShell(ip, user, pwd, cmd_list);
		System.out.println(l.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}
}
