package com.git.cloud.reports.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.reports.model.po.CreateReportParamPo;
import com.git.cloud.reports.service.IReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
public class ReportAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1058008856401096521L;
	
	@Autowired
	private IReportService reportService;
	private String id;
	private CreateReportParamPo crpo ;
	
	
	public CreateReportParamPo getCrpo() {
		return crpo;
	}
	public void setCrpo(CreateReportParamPo crpo) {
		this.crpo = crpo;
	}
	//定义一个成员变量
	private String message;
	private CreateReportParamPo createReportParam;
	
	public CreateReportParamPo getCreateReportParam() {
		return createReportParam;
	}
	public void setCreateReportParam(CreateReportParamPo createReportParam) {
		this.createReportParam = createReportParam;
	}
	//提供get/set方法
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@SuppressWarnings("unchecked")
	public void save(){
		String[] params = this.getRequest().getParameterValues("params[]");
		String type = this.getRequest().getParameter("type");
		String reportId = this.getRequest().getParameter("id");
		String result = reportService.save(params,type,reportId);
	}
	
	
	public String showReport(){
		if(id.contains("?")){
			id = id.substring(0, id.indexOf("?"));
		}
		this.setCreateReportParam(reportService.showReport(id));
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void searchForHtml(){
		Map parameters = new HashMap();
		Map parameMap = JSONObject.fromObject(this.getRequest().getParameter("paramMap"));
		Map reportMap = (Map) parameMap.get("reportMap");
		Map conMap = (Map) parameMap.get("conMap");
		Map sqlMap = (Map) parameMap.get("sqlMap");
		String jasperPath = (String) parameMap.get("jasperPath");
		parameters.put("jasperPath", jasperPath);
		//报表编译之后生成的.jasper文件的存放位置
		File reportFile = new File(this.getSession().getServletContext().getRealPath(jasperPath));
		File outPutFile = new File(this.getSession().getServletContext().getRealPath("/pages/reports/common/showReport.jsp"));
		if(!reportFile.exists()) {
			throw new JRRuntimeException("报表文件 AppDevInfo.jasper 不存在！");
		}
		if(!outPutFile.exists()) {
			throw new JRRuntimeException("报表文件 outPutFile 不存在！");
		}
		
		//读取数据库的配置文件
		Properties prop = new Properties();
	    InputStream in = null;
	    String url="";
	    String userName = "";
	    String password = "";
		try {
			in = new BufferedInputStream (new FileInputStream(this.getSession().getServletContext().getRealPath("/WEB-INF/classes/config/jdbc.properties")));
			prop.load(in);  
			url = prop.getProperty("jdbc.url");
			userName = prop.getProperty("jdbc.username");
			password = prop.getProperty("jdbc.password");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (FileNotFoundException e) {
			logger.error("异常exception",e);
		} catch (IOException e1){
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				logger.error("异常exception",e);
			}
		} 
		//jsper所需参数，利用迭代器，获取所有参数
		Map.Entry<String, String> me = null;
		Iterator<Map.Entry<String, String>> it = reportMap.entrySet().iterator();
		while(it.hasNext()){
			me = it.next();
			parameters.put(me.getKey(),me.getValue());
		}
		
		//遍历sql语句
		it = sqlMap.entrySet().iterator();
		String sqlStr = "";
		while(it.hasNext()){
			me = it.next();
			sqlStr = me.getValue();
			//替换sql语句中参数 并 将条件信息写入parameters
			Iterator<Map.Entry> conIt = conMap.entrySet().iterator();
			while(conIt.hasNext()){
				Map.Entry conMe = conIt.next();
				//conMap中非sql所需条件
				if( !(conMe.getKey().toString().startsWith("isSqlParam_")) && !(parameters.containsKey(conMe.getKey()))){		//避免在sql语句多条的情况下 ， 条件信息被重复写入。
					parameters.put(conMe.getKey(),conMe.getValue());
				}
				//conMap中的sql所需条件
				if(conMe.getKey() != null && conMe.getValue() != null && conMe.getKey().toString().startsWith("isSqlParam_") && conMe.getValue().toString().equals("Y")){
					String conKey4Sql = conMe.getKey().toString().substring(conMe.getKey().toString().indexOf('_')+1);
					sqlStr = sqlStr.replace(conKey4Sql, conMap.get(conKey4Sql).toString());
				}
			}
			parameters.put(me.getKey(),sqlStr);
		}
		
		parameters.put("SERVERNAME",this.getRequest().getServerName());
		parameters.put("SERVERPORT",this.getRequest().getServerPort());
		parameters.put("CONTEXTPATH",this.getRequest().getContextPath());
		parameters.put("SUBREPORT_DIR",this.getSession().getServletContext().getRealPath("/"));
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,userName,password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//获得Jasper输入流
		InputStream inputStream = this.getSession().getServletContext().getResourceAsStream(jasperPath);
		//设置格式
		this.getResponse().setContentType("text/html;charset=UTF-8");
		JasperPrint jasperPrint = new JasperPrint();
		try{
			jasperPrint = JasperFillManager.fillReport(inputStream, parameters, conn);
		}catch(Exception e){
			logger.error("异常exception",e);
		}
		//关闭数据库连接
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("异常exception",e);
		}
		//设置格式
		this.getResponse().setContentType("text/html;charset=UTF-8");
		//获得输出流 ,这里不能这样response.getOutputStream()
		PrintWriter printWriter = null;
		try {
			printWriter = this.getResponse().getWriter();
		} catch (IOException e) {
			logger.error("异常exception",e);
		}
		//创建JRHtmlExporter对象
		JRHtmlExporter htmlExporter = new JRHtmlExporter();
		//把jasperPrint到Session里面(net.sf.jasperreports.j2ee.jasper_print)
		this.getRequest().getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		//设值jasperPrint
		htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		//设置输出
		htmlExporter.setParameter(JRExporterParameter.OUTPUT_WRITER,printWriter);
		//设置图片生成的Servlet(生成图片就用这个ImageServlet,并且要在XML文件里面配置 image?image=这个是Servlet的url-pattern)
		//flush随机数用于重新获取图片（更新图片地址），否则条件改变后图片不会随之发生改变
		htmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,this.getRequest().getContextPath()+"/servlets/image?image=");
		//解决生成HTML报表缩小问题
		//htmlExporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "pt");
		htmlExporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_POINT);
		//导出
		try {
			htmlExporter.exportReport();
		} catch (JRException e) {
			logger.error("异常exception",e);
		}finally{
			try {
				if(printWriter!=null){
					printWriter.close();		//关闭输出流
				}
				if(inputStream!=null){
					inputStream.close();		//关闭输入流
				}
			} catch (IOException e) {
				logger.error("异常exception",e);
			}
		}
		
	}
	

	@SuppressWarnings("unchecked")
	public void toPdf(){
		String reportName = this.getRequest().getParameter("reportName");
		
		//报表编译之后生成的.jasper文件的存放位置
				File reportFile = new File(this.getRequest().getSession().getServletContext().getRealPath("/pages/reports/appDevInfo/AppDevInfo.jasper"));
				if(!reportFile.exists()) {
					throw new JRRuntimeException("报表文件 AppDevInfo.jasper 不存在！");
				}
		Map parameters = new HashMap();
		
		//读取数据库的配置文件
				Properties prop = new Properties();     
			    InputStream in = null;
			    String url="";
			    String userName = "";
			    String password = "";
				try {
					in = new BufferedInputStream (new FileInputStream(this.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/classes/config/jdbc.properties")));
					prop.load(in);  
					url = prop.getProperty("jdbc.url");
					userName = prop.getProperty("jdbc.username");
					password = prop.getProperty("jdbc.password");
				} catch (Exception e) {
					logger.error("异常exception",e);
				}finally{
					try {
						if(in!=null){
							in.close();
						}
					} catch (IOException e) {
						logger.error("异常exception",e);
					}
				}
				
				String selectConKeySql = "SELECT c.CONKEY FROM report r LEFT JOIN REPORT_CONDITION c ON c.REPORT_ID = r.ID WHERE r.REPORTNAMEVALUE = ? AND r.IS_ACTIVE = 'Y'";
				
				String selectSqlKeySql = "SELECT s.SQLKEY,s.SQLVALUE FROM report r LEFT JOIN report_sql s  ON s.REPORT_ID = r.ID"+
						"WHERE r.REPORTNAMEVALUE = ? AND r.IS_ACTIVE = 'Y'";
				
				Connection conn;
				//查询条件KEY
				try {
					conn = DriverManager.getConnection(url, userName, password);
//					Statement stmt = conn.createStatement();  
//					ResultSet rs = stmt.executeQuery(sb.toString());
					PreparedStatement stmt = conn.prepareStatement(selectConKeySql);
					stmt.setString(1, reportName);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						rs.getString("SQLKEY");
						parameters.put(rs.getString("SQLKEY"), this.getRequest().getParameter(rs.getString("SQLVALUE")));
					}
					stmt.close();  
					conn.close(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				}  
				//查询sql
				try {
					conn = DriverManager.getConnection(url, userName, password);
//					Statement stmt = conn.createStatement();  
//					ResultSet rs = stmt.executeQuery(selectSqlKeySql); 
					PreparedStatement stmt = conn.prepareStatement(selectSqlKeySql);
					stmt.setString(1, reportName);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						rs.getString("CONKEY");
						parameters.put(rs.getString("CONKEY"), this.getRequest().getParameter(rs.getString("CONKEY")));
					}
					stmt.close();  
					conn.close(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				}  
		
		
		
		
		
		try{
			parameters.put("SERVERNAME",this.getRequest().getServerName());
			parameters.put("SERVERPORT",this.getRequest().getServerPort());
			parameters.put("CONTEXTPATH",this.getRequest().getContextPath());
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,userName, password);
			
			byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,con);
			this.getResponse().setContentType("application/pdf");
			this.getResponse().setContentLength(bytes.length);
			
			ServletOutputStream outStream = this.getResponse().getOutputStream();
			outStream.write(bytes,0,bytes.length);
			outStream.flush();
			outStream.close();
			
		}catch(Exception e){
			logger.error("异常exception",e);
		}
	}

	/**
	 * 查询所有的设备信息，分页展示到前台
	 * 
	 * @throws Exception
	 */
	public void getReportList() throws Exception {
		this.jsonOut(reportService.getReportPagination(this.getPaginationParam()));
	}
	/**
	 * 删除选中的报表
	 * @throws Exception
	 */
	public void deleteReportList() throws Exception {
		reportService.deleteReportList(crpo.getId());
	}
	/**
	 * 查询报表信息，包括四个表的数据
	 * @throws Exception
	 */
	public void getReport() throws Exception{
		jsonOut(reportService.selectReport(id));
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 根据报表名查询报表信息，查重
	 * @throws Exception
	 */
	public void getReportForName() throws Exception{
		String reportName = this.getRequest().getParameter("reportName");
		CreateReportParamPo createReportParamPo = new CreateReportParamPo();
		createReportParamPo = reportService.selectReportByName(reportName);
		try {
			jsonOut(createReportParamPo);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
	}
}
