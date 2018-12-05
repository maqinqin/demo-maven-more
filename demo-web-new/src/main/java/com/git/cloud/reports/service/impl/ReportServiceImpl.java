package com.git.cloud.reports.service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.reports.dao.IReportDao;
import com.git.cloud.reports.dao.impl.ReportDaoImpl;
import com.git.cloud.reports.model.po.ConProParamPo;
import com.git.cloud.reports.model.po.CreateReportParamPo;
import com.git.cloud.reports.model.po.PropertyParamPo;
import com.git.cloud.reports.model.po.SqlParamPo;
import com.git.cloud.reports.service.IReportService;
import com.git.cloud.resmgt.common.model.vo.CmDeviceVo;
import com.git.cloud.sys.dao.SysMenuDao;
import com.git.cloud.sys.model.po.MenuPo;
import com.git.cloud.sys.model.po.SysMenuPo;

import com.git.cloud.common.action.BaseAction;
import com.jhlabs.image.CropFilter;

public class ReportServiceImpl implements IReportService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private IReportDao reportDao;
	private SysMenuDao sysMenuDao;

	@Override
	public Pagination<CreateReportParamPo> getReportPagination(
			PaginationParam paginationParam) {

		return reportDao.pageQuery("findReportTotal", "findReportPage",
				paginationParam);
	}
	//保存报表
	@Override
	public String save(String[] params, String type, String reportId) {
		JSONArray array = JSONArray.fromObject(params[0]);
		JSONObject obj;
		JSONObject selectobj;
		CreateReportParamPo crpo = new CreateReportParamPo();
		if ("update".equals(type)) {
			try {
				reportDao.resetReport(reportId);
				reportDao.resetReportCondition(reportId);
				reportDao.resetReportConditionProperty(reportId);
				reportDao.resetReportSql(reportId);
				String conId;
				String proId;
				String sqlId;
				crpo.setId(reportId);
				for (int i = 0; i < array.size(); i++) {
					obj = array.getJSONObject(i);
					if (obj.containsKey("reportNameKey")) { // 报表名称map
						crpo.setReportNameKey(obj.getString("reportNameKey"));
						crpo.setReportNameValue(obj
								.getString("reportNameValue"));

					} else if (obj.containsKey("reportDecKey")) { // 报表描述map
						crpo.setReportDecKey(obj.getString("reportDecKey"));
						crpo.setReportDecValue(obj.getString("reportDecValue"));

					} else if (obj.containsKey("conKey")) { // 报表条件和属性map
						ConProParamPo cppo = new ConProParamPo();
						conId = obj.getString("conId");
						cppo.setConKey(obj.getString("conKey"));
						cppo.setConValue(obj.getString("conValue"));
						cppo.setConType(obj.getString("conType"));
						cppo.setIsSqlParam(obj.getString("isSqlParam"));
						if (obj.getString("conType") != null
								&& obj.getString("conType").equals("time")) {
							cppo.setConType_dec(obj.getString("sqlSelectValue"));
						} else if (obj.getString("conType") != null
								&& obj.getString("conType").equals("selectSql")) {
							cppo.setConType_dec(obj.getString("sqlSelectValue"));
						}
						cppo.setReportId(crpo.getId());
						if (conId.equals("")) {
							cppo.setId(UUID.randomUUID().toString());
							reportDao.saveReportCondition(cppo);
						} else {
							cppo.setId(conId);
							reportDao.updateReportCondition(cppo);
						}
						if (obj.getString("conType") != null
								&& obj.getString("conType").equals("select")) {
							String select = obj.getString("select");
							JSONArray selectArray = JSONArray
									.fromObject(select);
							for (int y = 0; y < selectArray.size(); y++) {
								selectobj = selectArray.getJSONObject(y);
								if (selectobj.containsKey("proKey")) {
									PropertyParamPo pppo = new PropertyParamPo();
									proId = selectobj.getString("proId");
									pppo.setPropertyKey(selectobj
											.getString("proKey"));
									pppo.setPropertyValue(selectobj
											.getString("proValue"));
									pppo.setConditionId(cppo.getId());
									if (proId.equals("")) {
										pppo.setId(UUID.randomUUID().toString());
										reportDao.saveReportProperty(pppo);
									} else {
										pppo.setId(proId);
										reportDao.updateReportProperty(pppo);
									}
								}
							}
						}
					} else if (obj.containsKey("sqlKey")) { // 报表sql语句map
						SqlParamPo sqlPo = new SqlParamPo();
						sqlId = obj.getString("sqlId");
						sqlPo.setSqlKey(obj.getString("sqlKey"));
						sqlPo.setSqlValue(obj.getString("sqlValue"));
						sqlPo.setReportId(crpo.getId());
						if (sqlId.equals("")) {
							sqlPo.setId(UUID.randomUUID().toString());
							reportDao.saveReportSql(sqlPo);
						} else {
							sqlPo.setId(sqlId);
							reportDao.updateReportSql(sqlPo);
						}
					} else if (obj.containsKey("jasperPath")) { // 报表Jasper路径
						crpo.setJasperPath(obj.getString("jasperPath"));
					}
				}
				reportDao.updateReport(crpo);
			} catch (RollbackableBizException e) {
				logger.error("异常exception",e);
			}
		} else if ("add".equals(type)) {
			ArrayList<ConProParamPo> conProList = new ArrayList<ConProParamPo>();
			ArrayList<SqlParamPo> sqlList = new ArrayList<SqlParamPo>();
			crpo.setId(UUID.randomUUID().toString());
			for (int i = 0; i < array.size(); i++) {
				obj = array.getJSONObject(i);

				if (obj.containsKey("reportNameKey")) { // 报表名称map
					crpo.setReportNameKey(obj.getString("reportNameKey"));
					crpo.setReportNameValue(obj.getString("reportNameValue"));

				} else if (obj.containsKey("reportDecKey")) { // 报表描述map
					crpo.setReportDecKey(obj.getString("reportDecKey"));
					crpo.setReportDecValue(obj.getString("reportDecValue"));

				} else if (obj.containsKey("conKey")) { // 报表条件和属性map
					ConProParamPo cppo = new ConProParamPo();
					cppo.setId(UUID.randomUUID().toString());
					cppo.setConKey(obj.getString("conKey"));
					cppo.setConValue(obj.getString("conValue"));
					cppo.setConType(obj.getString("conType"));
					cppo.setIsSqlParam(obj.getString("isSqlParam"));

					List<PropertyParamPo> proList = new ArrayList<PropertyParamPo>();
					String select = obj.getString("select");
					JSONArray selectArray = JSONArray.fromObject(select);
					if (obj.getString("conType") != null
							&& obj.getString("conType").equals("select")) {
						for (int y = 0; y < selectArray.size(); y++) {
							selectobj = selectArray.getJSONObject(y);
							if (selectobj.containsKey("proKey")) {
								PropertyParamPo pppo = new PropertyParamPo();
								pppo.setId(UUID.randomUUID().toString());
								pppo.setPropertyKey(selectobj
										.getString("proKey"));
								pppo.setPropertyValue(selectobj
										.getString("proValue"));
								pppo.setConditionId(cppo.getId());
								proList.add(pppo);
							}
						}
					} else if (obj.getString("conType") != null
							&& obj.getString("conType").equals("time")) {
						cppo.setConType_dec(obj.getString("sqlSelectValue"));
					} else if (obj.getString("conType") != null
							&& obj.getString("conType").equals("selectSql")) {
						cppo.setConType_dec(obj.getString("sqlSelectValue"));
					}
					cppo.setProList(proList);
					cppo.setReportId(crpo.getId());
					conProList.add(cppo);
				} else if (obj.containsKey("sqlKey")) { // 报表sql语句map
					SqlParamPo sqlPo = new SqlParamPo();
					sqlPo.setId(UUID.randomUUID().toString());
					sqlPo.setSqlKey(obj.getString("sqlKey"));
					sqlPo.setSqlValue(obj.getString("sqlValue"));
					sqlPo.setReportId(crpo.getId());
					sqlList.add(sqlPo);
				} else if (obj.containsKey("jasperPath")) { // 报表Jasper路径
					crpo.setJasperPath(obj.getString("jasperPath"));
				}
			}
			crpo.setConProList(conProList);
			crpo.setSqlList(sqlList);
			try {
				reportDao.save(crpo);
			} catch (RollbackableBizException e) {
				logger.error("异常exception",e);
			}
			//这段代码是用来添加菜单的，原来是自动添加的，现在改为了手动添加，没有删除。
			/*try {
				SysMenuPo menu = new SysMenuPo();
				menu.setMenuName(crpo.getReportNameValue());
				menu.setMenuUrl("/reports/common/showReport.action?id="
						+ crpo.getId());
				menu.setParentId("399812C641A242D6A121CD55B58FB021");
				SysMenuPo parent = new SysMenuPo();
				parent.setId("399812C641A242D6A121CD55B58FB021");
				menu.setParent(parent);
				// parentId暂时写死
				List<SysMenuPo> menuList = sysMenuDao
						.findMenusByParentId("399812C641A242D6A121CD55B58FB021");
				String orderId = "0";
				for (SysMenuPo m : menuList) {
					if (Integer.parseInt(m.getOrderId()) > Integer
							.parseInt(orderId)) {
						orderId = m.getOrderId();
					}
				}
				orderId = String.valueOf(Integer.parseInt(orderId) + 1);
				menu.setOrderId(orderId);
				menu.setResourceType("menu");

				sysMenuDao.save(menu);
			} catch (RollbackableBizException e) {
				// TODO Auto-generated catch block
				logger.error("异常exception",e);
			}*/
		}
		return null;
	}

	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	//显示报表
	@SuppressWarnings("unchecked")
	@Override
	public CreateReportParamPo showReport(String id) {
		try {
			CreateReportParamPo crpo = reportDao.selectReportParam(id);
			List<ConProParamPo> cppoList = reportDao.selectConParam(id);
			List<PropertyParamPo> pppoList = reportDao.selectProParam(id);
			List<SqlParamPo> sqlList = reportDao.selectSqlParam(id);
			if (crpo == null) {
				throw new RuntimeException("未查询到该报表相关信息！");
			}
			crpo.setSqlList(sqlList);
			if (cppoList.size() > 0) {
				for (ConProParamPo cppo : cppoList) {
					List<PropertyParamPo> proList = new ArrayList<PropertyParamPo>();
					for (PropertyParamPo pppo : pppoList) {
						if (pppo.getConditionId().equals(cppo.getId())) {
							proList.add(pppo);
						}
					}
					cppo.setProList(proList);
				}
			}
			crpo.setConProList(cppoList);
			crpo.setId(id);
			return crpo;
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return null;
	}
	//删除报表
	@Override
	public void deleteReportList(String id) {
		CreateReportParamPo crpo = new CreateReportParamPo();
		SqlParamPo sppo = new SqlParamPo();
		ConProParamPo cppo = new ConProParamPo();
		crpo.setId(id);
		sppo.setReportId(id);
		cppo.setReportId(id);
		try {
			List<ConProParamPo> cppoList = reportDao.selectConParam(id);
			if (cppoList.size() > 0) {
				for (ConProParamPo cppo1 : cppoList) {
					reportDao.update("deleteReportOfProperty", cppo1);
				}
			}
			reportDao.update("deleteReport", crpo);
			reportDao.update("deleteReportOfCondition", cppo);
			reportDao.update("deleteReportOfSql", sppo);
			//删除报表时根据url删除菜单
			/*String menuUrl = "/reports/common/showReport.action?id=" + id;
			MenuPo mp = new MenuPo();
			mp.setMenuUrl(menuUrl);
			sysMenuDao.update("SysMenu.delete1", mp);*/
		} catch (RollbackableBizException e1) {
			e1.printStackTrace();
		}

	}

	public CreateReportParamPo selectReport(String id) {
		try {
			CreateReportParamPo crpo = reportDao.selectReportParam(id);
			List<ConProParamPo> cppoList = reportDao.selectConParam(id);
			List<PropertyParamPo> pppoList = reportDao.selectProParam(id);
			List<SqlParamPo> sqlList = reportDao.selectSqlParam(id);
			if (crpo == null) {
				throw new RuntimeException("未查询到该报表相关信息！");
			}
			crpo.setSqlList(sqlList);
			if (cppoList.size() > 0) {
				for (ConProParamPo cppo : cppoList) {
					List<PropertyParamPo> proList = new ArrayList<PropertyParamPo>();
					for (PropertyParamPo pppo : pppoList) {
						if (pppo.getConditionId().equals(cppo.getId())) {
							proList.add(pppo);
						}
					}
					cppo.setProList(proList);
				}
			}
			crpo.setConProList(cppoList);
			return crpo;
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return null;
	}

	@Override
	public CreateReportParamPo selectReportByName(String reportName) {
		try {
			return reportDao.selectReportByName(reportName);
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return null;
	}

}
