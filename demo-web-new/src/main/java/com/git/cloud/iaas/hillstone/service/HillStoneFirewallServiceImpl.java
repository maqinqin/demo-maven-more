package com.git.cloud.iaas.hillstone.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.iaas.firewall.common.FirewallConstants;
import com.git.cloud.iaas.hillstone.RestfulService;
import com.git.cloud.iaas.hillstone.model.AddrBookModel;
import com.git.cloud.iaas.hillstone.model.Cookie;
import com.git.cloud.iaas.hillstone.model.DnatModel;
import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.hillstone.model.HillStoneOperation;
import com.git.cloud.iaas.hillstone.model.PolicyModel;
import com.git.cloud.iaas.hillstone.model.ServiceBookModel;
import com.git.cloud.iaas.hillstone.model.SnatModel;
import com.git.support.common.MesgFlds;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public class HillStoneFirewallServiceImpl extends RestfulService implements HillStoneFirewallService {

	private static Logger logger = LoggerFactory.getLogger(HillStoneFirewallServiceImpl.class);
	
	@Override
	public ServiceBookModel getServiceBookListByName(HillStoneModel hillStoneModel, String servicebookName) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object> ();
		String serverbookQueryData = "query={\"conditions\":[{\"field\":\"name\",\"operator\":0,\"value\":\""+servicebookName+"\"}],\"start\":0,\"limit\":1,\"page\":1}";
		paramMap.put("SERVERBOOK_NAME", servicebookName);
		paramMap.put("SERVERBOOK_QUERY_DATA", serverbookQueryData);
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.GET_SERVICEBOOK_BY_NAME, paramMap);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] getServiceBookListByName : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				if (Integer.valueOf(json.getString("total")) > 0) {
					ServiceBookModel serviceBookModel = new ServiceBookModel();
					serviceBookModel.setServicebookName(servicebookName);
					return serviceBookModel;
				}
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return null;
	}

	@Override
	public String createServiceBook(HillStoneModel hillStoneModel, ServiceBookModel serviceBookModel) throws Exception {
		IDataObject reqData = null;
		BodyDO body = this.createBodyDO();
		body.setString("SERVERBOOK_NAME", serviceBookModel.getServicebookName());
		body.setString("SERVERBOOK_DESC", serviceBookModel.getDescription());
		if ("tcp".equals(serviceBookModel.getProtocol().toLowerCase())) {
			reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_SERVICEBOOK_TCP);
			if (serviceBookModel.getDstPort() != null && !"any".equals(serviceBookModel.getDstPort().toLowerCase())) {
				if (serviceBookModel.getDstPort().indexOf("-") > 0) {
					body.setString("DST_PORT_LOW", serviceBookModel.getDstPort().split("-")[0]);
					body.setString("DST_PORT_HIGH", serviceBookModel.getDstPort().split("-")[1]);
				} else {
					body.setString("DST_PORT_LOW", serviceBookModel.getDstPort());
					body.setString("DST_PORT_HIGH", serviceBookModel.getDstPort());
				}
			} else {
				body.setString("DST_PORT_LOW", "0");
				body.setString("DST_PORT_HIGH", "65535");
			}
			if (serviceBookModel.getSrcPort() != null && !"any".equals(serviceBookModel.getSrcPort().toLowerCase())) {
				if (serviceBookModel.getSrcPort().indexOf("-") > 0) {
					body.setString("SRC_PORT_LOW", serviceBookModel.getSrcPort().split("-")[0]);
					body.setString("SRC_PORT_HIGH", serviceBookModel.getSrcPort().split("-")[1]);
				} else {
					body.setString("SRC_PORT_LOW", serviceBookModel.getSrcPort());
					body.setString("SRC_PORT_HIGH", serviceBookModel.getSrcPort());
				}
			} else {
				body.setString("SRC_PORT_LOW", "0");
				body.setString("SRC_PORT_HIGH", "65535");
			}
		} else if ("udp".equals(serviceBookModel.getProtocol().toLowerCase())) {
			reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_SERVICEBOOK_UDP);
			if (serviceBookModel.getDstPort() != null && !"any".equals(serviceBookModel.getDstPort().toLowerCase())) {
				if (serviceBookModel.getDstPort().indexOf("-") > 0) {
					body.setString("DST_PORT_LOW", serviceBookModel.getDstPort().split("-")[0]);
					body.setString("DST_PORT_HIGH", serviceBookModel.getDstPort().split("-")[1]);
				} else {
					body.setString("DST_PORT_LOW", serviceBookModel.getDstPort());
					body.setString("DST_PORT_HIGH", serviceBookModel.getDstPort());
				}
			} else {
				body.setString("DST_PORT_LOW", "0");
				body.setString("DST_PORT_HIGH", "65535");
			}
			if (serviceBookModel.getSrcPort() != null && !"any".equals(serviceBookModel.getSrcPort().toLowerCase())) {
				if (serviceBookModel.getSrcPort().indexOf("-") > 0) {
					body.setString("SRC_PORT_LOW", serviceBookModel.getSrcPort().split("-")[0]);
					body.setString("SRC_PORT_HIGH", serviceBookModel.getSrcPort().split("-")[1]);
				} else {
					body.setString("SRC_PORT_LOW", serviceBookModel.getSrcPort());
					body.setString("SRC_PORT_HIGH", serviceBookModel.getSrcPort());
				}
			} else {
				body.setString("SRC_PORT_LOW", "0");
				body.setString("SRC_PORT_HIGH", "65535");
			}
		} else if ("icmp".equals(serviceBookModel.getProtocol().toLowerCase())) {
			reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_SERVICEBOOK_ICMP);
		} else {
			reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_SERVICEBOOK);
		}
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] create servicebook : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				return "";
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	@Override
	public String createAddrBook(HillStoneModel hillStoneModel, AddrBookModel addrBookModel) throws Exception {
		IDataObject reqData = null;
		BodyDO body = this.createBodyDO();
		body.setString("ADDRBOOK_NAME", addrBookModel.getAddrbookName());
		body.setString("IP_MIN", addrBookModel.getIpMin());
		body.setString("IP_MAX", addrBookModel.getIpMax());
		reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_ADDRBOOK_RANGE);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] create addrbook : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				return "";
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String createDnat(HillStoneModel hillStoneModel, DnatModel dnatModel) throws Exception {
		String natId = "";
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_DNAT);
		BodyDO body = this.createBodyDO();
		body.setString("VR_NAME", FirewallConstants.HS_DEF_VR_NAME);
		body.setString("FROM_IP", dnatModel.getFromIp());
		body.setString("TO_IP", dnatModel.getToIp());
		body.setString("TRANS_TO_IP", dnatModel.getTransToIp());
		body.setString("FROM_IS_IP", dnatModel.getFromIsIp());
		body.setString("TO_IS_IP", dnatModel.getToIsIp());
		body.setString("TRANS_TO_IS_IP", dnatModel.getTransToIsIp());
//		body.setString("SRC_PORT", dnatModel.getSrcPort());
//		body.setString("DST_PORT", dnatModel.getDstPort());
		body.setString("DNAT_DESC", dnatModel.getDescription());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] create dnat : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				natId = json.getJSONArray("result").getJSONObject(0).getString("rule_id");
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return natId;
	}

	@Override
	public void deleteDnat(HillStoneModel hillStoneModel, String[] idArr) throws Exception {
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.DELETE_DNAT);
		BodyDO body = this.createBodyDO();
		StringBuffer dnatIdDatas = new StringBuffer();
		for (int i = 0 ; i < idArr.length ; i++) {
			if (i > 0) {
				dnatIdDatas.append(",");
			}
			dnatIdDatas.append("{\"vr_name\":\"");
			dnatIdDatas.append(FirewallConstants.HS_DEF_VR_NAME);
			dnatIdDatas.append("\",\"rule_id\":\"");
			dnatIdDatas.append(idArr[i]);
			dnatIdDatas.append("\"}");
		}
		body.setString("DNATID_DATAS", dnatIdDatas.toString());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] delete dnat : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if (!"true".equals(success)) {
				throw new Exception ("delete dnat has error, ids is " + idArr.toString() + ", exception is " + json.getJSONObject("exception").toString());
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String createSnat(HillStoneModel hillStoneModel, SnatModel snatModel) throws Exception {
		String natId = "";
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_SNAT);
		BodyDO body = this.createBodyDO();
		body.setString("VR_NAME", FirewallConstants.HS_DEF_VR_NAME);
		body.setString("FROM_IP", snatModel.getFromIp());
		body.setString("TO_IP", snatModel.getToIp());
		body.setString("TRANS_TO_IP", snatModel.getTransToIp());
		body.setString("FROM_IS_IP", snatModel.getFromIsIp());
		body.setString("TO_IS_IP", snatModel.getToIsIp());
		body.setString("TRANS_TO_IS_IP", snatModel.getTransToIsIp());
//		body.setString("SRC_PORT", FirewallConstants.HS_DEF_SNAT_PORT);
		body.setString("SNAT_DESC", snatModel.getDescription());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] create snat : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				natId = json.getJSONArray("result").getJSONObject(0).getString("rule_id");
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return natId;
	}

	@Override
	public void deleteSnat(HillStoneModel hillStoneModel, String[] idArr) throws Exception {
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.DELETE_SNAT);
		BodyDO body = this.createBodyDO();
		StringBuffer dnatIdDatas = new StringBuffer();
		for (int i = 0 ; i < idArr.length ; i++) {
			if (i > 0) {
				dnatIdDatas.append(",");
			}
			dnatIdDatas.append("{\"vr_name\":\"");
			dnatIdDatas.append(FirewallConstants.HS_DEF_VR_NAME);
			dnatIdDatas.append("\",\"rule_id\":\"");
			dnatIdDatas.append(idArr[i]);
			dnatIdDatas.append("\"}");
		}
		body.setString("SNATID_DATAS", dnatIdDatas.toString());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] delete snat : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if (!"true".equals(success)) {
				throw new Exception ("delete snat has error, ids is " + idArr.toString() + ", exception is " + json.getJSONObject("exception").toString());
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String createPolicy(HillStoneModel hillStoneModel, PolicyModel policyModel) throws Exception {
		String policyId = "";
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.CREATE_POLICY);
		BodyDO body = this.createBodyDO();
		body.setString("POLICY_NAME", policyModel.getPolicyName());
		if (policyModel.getSrcIp() != null && !"".equals(policyModel.getSrcIp())) {
			body.setString("SRC_DATAS", "\"src_subnet\":{\"ip\":\""+policyModel.getSrcIp()+"\",\"netmask\":\""+policyModel.getSrcNetmask()+"\"}");
		} else {
			body.setString("SRC_DATAS", "\"src_range\":{\"min\":\""+policyModel.getSrcIpMin()+"\",\"max\":\""+policyModel.getSrcIpMax()+"\"}");
		}
		if (policyModel.getDstIp() != null && !"".equals(policyModel.getDstIp())) {
			body.setString("DST_DATAS", "\"dst_subnet\":{\"ip\":\""+policyModel.getDstIp()+"\",\"netmask\":\""+policyModel.getDstNetmask()+"\"}");
		} else {
			body.setString("DST_DATAS", "\"dst_range\":{\"min\":\""+policyModel.getDstIpMin()+"\",\"max\":\""+policyModel.getDstIpMax()+"\"}");
		}
		body.setString("SERVERBOOK_NAME", policyModel.getServerbookName());
		body.setString("POLICY_DESC", policyModel.getPolicyDesc());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] create policy : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if ("true".equals(success)) {
				policyId = json.getJSONArray("result").getJSONObject(0).getString("id");
			} else {
				throw new Exception (json.getString("exception"));
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return policyId;
	}

	@Override
	public void deletePolicy(HillStoneModel hillStoneModel, String[] idArr) throws Exception {
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.DELETE_POLICY);
		BodyDO body = this.createBodyDO();
		StringBuffer dnatIdDatas = new StringBuffer();
		for (int i = 0 ; i < idArr.length ; i++) {
			if (i > 0) {
				dnatIdDatas.append(",");
			}
			dnatIdDatas.append("{\"id\":\"");
			dnatIdDatas.append(idArr[i]);
			dnatIdDatas.append("\"}");
		}
		body.setString("POLICYID_DATAS", dnatIdDatas.toString());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		logger.info("[HillStoneFirewallServiceImpl] delete policy : " + this.getExecuteResult(rspData));
		if(this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			String success = json.getString("success");
			if (!"true".equals(success)) {
				throw new Exception ("delete policy has error, ids is " + idArr.toString() + ", exception is " + json.getJSONObject("exception").toString());
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	private String login(HillStoneModel hillStoneModel) throws Exception {
		String cookie = "";
		IDataObject reqData = this.getIDataObject(hillStoneModel, HillStoneOperation.GET_TOKEN, "", null);
		BodyDO body = this.createBodyDO();
		body.setString("USERNAME", hillStoneModel.getUsername());
		body.setString("PASSWORD", hillStoneModel.getPassword());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		
//		String jsonData = this.getExecuteResultData(rspData);
//		cookie = loginCookieParser(jsonData);
		if(this.getExecuteResult(rspData) < 300) {
			String jsonData = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(jsonData);
			boolean succflag = json.getBoolean("success");
			if (succflag) {
				cookie = loginCookieParser(json);
			} else {
				throw new Exception("login has error, username is " + hillStoneModel.getUsername() + ", exception is " + json.getJSONObject("exception").toString());
			}
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return cookie;
	}
	
	private IDataObject getIDataObject(HillStoneModel hillStoneModel, String operation) throws Exception {
		return this.getIDataObject(hillStoneModel, operation, null);
	}
	
	private IDataObject getIDataObject(HillStoneModel hillStoneModel, String operation, HashMap<String, Object> urlParamMap) throws Exception {
		String cookie = "";
		if (FirewallConstants.HS_TEST_FLAG) {
			cookie = "HS_TEST_FLAG";
		} else {
			cookie = this.login(hillStoneModel);
		}
		return this.getIDataObject(hillStoneModel, operation, cookie, urlParamMap);
	}
	
	private IDataObject getIDataObject(HillStoneModel hillStoneModel, String operation, String cookie, HashMap<String, Object> urlParamMap) {
		HeaderDO header = super.createHeaderDO(hillStoneModel.getRestIp(), hillStoneModel.getRestPort(), "", operation);
		if (cookie != null && !"".equals(cookie)) {
			header.setString("cookie", cookie);
		}
		IDataObject reqData = super.getIDataObject(header);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		if (urlParamMap != null) {
			paramMap.putAll(urlParamMap);
		}
		paramMap.put("REST_IP", hillStoneModel.getRestIp());
		paramMap.put("REST_PORT", hillStoneModel.getRestPort());
		paramMap.put("REST_DOMAIN", "");
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		return reqData;
	}
	
	private String loginCookieParser(JSONObject jo) throws JSONException, UnsupportedEncodingException {
		JSONObject resultJsn = jo.getJSONObject("result");
		String token = resultJsn.getString("token");
		String platform = resultJsn.getString("platform");
		String hw_platform = resultJsn.getString("hw_platform");
		String host_name = resultJsn.getString("host_name");
		String company = resultJsn.getString("company");
		String oemid = resultJsn.getString("oemId");
		String vsysid = resultJsn.getString("vsysId");
		String vsysname = resultJsn.getString("vsysName");
		String role = resultJsn.getString("role");
		String license = resultJsn.getString("license");
		String httpProtocol = resultJsn.getString("httpProtocol");
		JSONObject sysInfoObj = resultJsn.getJSONObject("sysInfo");
		String soft_version = sysInfoObj.getString("soft_version");
		String username = resultJsn.getString("username");
		String overseaLicense = resultJsn.getString("overseaLicense");
		String HS_frame_lang = "zh_CN";
		Cookie cookie = new Cookie(token, platform, hw_platform, host_name, company, oemid, vsysid, vsysname, role,
				license, httpProtocol, soft_version, username, overseaLicense, HS_frame_lang);
		System.out.println(cookie.toString());
		String rstCookie = "fromrootvsys=true;" + cookie.toString();
		return rstCookie;
	}
}
