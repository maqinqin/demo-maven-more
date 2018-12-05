<%@page import="net.sf.jasperreports.engine.export.ooxml.JRDocxExporter"%>
<%@ page language="java" contentType="application/excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%
String reportId = request.getParameter("REPORTID");
/* String REPORTDEC = request.getParameter("REPORTDEC");
String REPORTNAME = request.getParameter("REPORTNAME"); */
String jasperPath = request.getParameter("jasperPath");
//报表编译之后生成的.jasper文件的存放位置
		/* File reportFile = new File(this.getServletContext().getRealPath("/pages/reports/appDevInfo/AppDevInfoOfCommon.jasper")); */
		File reportFile = new File(this.getServletContext().getRealPath(jasperPath));
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
			in = new BufferedInputStream (new FileInputStream(this.getServletContext().getRealPath("/WEB-INF/classes/config/jdbc.properties")));
			prop.load(in);  
			url = prop.getProperty("jdbc.url");
			userName = prop.getProperty("jdbc.username");
			password = prop.getProperty("jdbc.password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			in.close();
		}
		String selectReportName = "SELECT r.REPORTNAMEVALUE , r.REPORTDECVALUE "+
				  "FROM REPORT r "+
                "WHERE r.ID = '"+ reportId +"' ";

		String selectConKeySql = "SELECT "+
		"c.CONKEY "+
		"FROM "+
			"REPORT r "+
		"LEFT JOIN REPORT_CONDITION c ON c.REPORT_ID = r.ID "+
		"WHERE "+
			"r.ID = '"+reportId+"' "+
		"AND r.IS_ACTIVE = 'Y' " +
		"AND c.IS_SQLPARAM = 'Y' ";
		
		String selectSqlKeySql = "SELECT "+
		"s.SQLKEY ,s.SQLVALUE  "+
		"FROM "+
			"REPORT r "+
		"LEFT JOIN REPORT_SQL s  ON s.REPORT_ID = r.ID "+
		"WHERE "+
			"r.ID = '"+reportId+"' "+
		"AND r.IS_ACTIVE = 'Y' ";
				
		Connection conn;
		//查询报表名字和描述
				try {
					conn = DriverManager.getConnection(url, userName, password);
					Statement stmt = conn.createStatement();  
					ResultSet rs = stmt.executeQuery(selectReportName); 
					ResultSetMetaData rsm   =rs.getMetaData();
				    int ColumnCount =rsm.getColumnCount();
					while (rs.next()) {
						 parameters.put("REPORTDEC",rs.getString(2));
						 parameters.put("REPORTNAME",rs.getString(1)); 
					}
					stmt.close();  
					conn.close(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
		//查询条件KEY
		Map conMap = new HashMap();
		try {
			conn = DriverManager.getConnection(url, userName, password);
			Statement stmt = conn.createStatement();  
			ResultSet rs = stmt.executeQuery(selectConKeySql); 
			ResultSetMetaData rsm   =rs.getMetaData();
		    int ColumnCount =rsm.getColumnCount();
			while (rs.next()) {
				conMap.put(rs.getString(1), request.getParameter(rs.getString(1)));
				parameters.put(rs.getString(1), request.getParameter(rs.getString(1)));
			}
			stmt.close();  
			conn.close(); 
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  
		//查询sql
		Map sqlMap = new HashMap();
		try {
			conn = DriverManager.getConnection(url, userName, password);
			Statement stmt = conn.createStatement();  
			ResultSet rs = stmt.executeQuery(selectSqlKeySql); 
			ResultSetMetaData rsm   =rs.getMetaData();
		    int ColumnCount =rsm.getColumnCount();
			while (rs.next()) {
				sqlMap.put(rs.getString(1), rs.getString(2));
			}
			stmt.close();  
			conn.close(); 
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  
		//遍历sql语句
				Map.Entry<String, String> me = null;
				Iterator<Map.Entry<String, String>> it = sqlMap.entrySet().iterator();
				String sqlStr = "";
				while(it.hasNext()){
					me = it.next();
					sqlStr = me.getValue();
					//替换sql语句中参数 并 将条件信息写入parameters
					Iterator<Map.Entry> conIt = conMap.entrySet().iterator();
					while(conIt.hasNext()){
						Map.Entry conMe = conIt.next();
						String conKey4Sql = conMe.getKey().toString();
						sqlStr = sqlStr.replace(conKey4Sql, conMap.get(conKey4Sql).toString());
					}
					parameters.put(me.getKey(),sqlStr);
				}
				
		try{
			parameters.put("SERVERNAME",request.getServerName());
			parameters.put("SERVERPORT",request.getServerPort());
			parameters.put("CONTEXTPATH",request.getContextPath());
			parameters.put("SUBREPORT_DIR",request.getSession().getServletContext().getRealPath("/"));
			Connection con = DriverManager.getConnection(url,userName, password);
			
			response.setCharacterEncoding("UTF-8");  
			ServletContext servletContext=this.getServletConfig().getServletContext();
			//得到jasper文件
			JasperReport jasperReport= (JasperReport)JRLoader.loadObject(reportFile);
			//创建JasperPrint
			JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters,con);
			//创建doc输出器
			JRDocxExporter exporter = new JRDocxExporter();
			
			OutputStream outStream = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String(parameters.get("REPORTNAME").toString().getBytes("gbk"),"iso8859-1")  + ".doc");
			exporter.exportReport(); 
			out = pageContext.getOut();
			out.clear();
 			out = pageContext.pushBody(); 
		}catch(Exception e){
			e.printStackTrace();
		}
%>