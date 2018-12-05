package com.git.cloud.bill.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.bill.model.vo.BillInfoVo;
import com.git.cloud.bill.model.vo.BillPageParamVo;
import com.git.cloud.bill.model.vo.Order;
import com.git.cloud.bill.model.vo.OrderRoot;
import com.git.cloud.bill.model.vo.PageInfo;
import com.git.cloud.bill.model.vo.Parameters;
import com.git.cloud.bill.model.vo.Shopping_lists;
import com.git.cloud.bill.model.vo.VariableVOs;
import com.git.cloud.bill.service.BillService;
import com.git.cloud.common.enums.BillingParamEnum;
import com.git.cloud.common.enums.CostType;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.DateUtil;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.rest.client.RestClient;
import com.git.cloud.rest.client.RestModel;
import com.git.cloud.rest.client.RestResult;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.service.ITenantAuthoService;
import com.git.cloud.utils.TimeUtil;
import com.ibm.icu.text.SimpleDateFormat;
@Service
public class BillServiceImpl implements BillService{
	private static Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);
	private ParameterService parameterServiceImpl;
	@Autowired
	private IBmSrDao bmSrDao;
	
	public ParameterService getParameterServiceImpl() {
		return parameterServiceImpl;
	}

	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}

	@Autowired
	private ITenantAuthoService tenantService;
	

	@Override
	public OrderRoot buildOrderData(Map<String, String> map) throws Exception {
		OrderRoot orderRoot  = new OrderRoot();
		Order order = new Order();
		List<Shopping_lists> lists = new ArrayList<>();
		Shopping_lists shopList = new Shopping_lists();
		Parameters param = new Parameters();
		try {
			String orderType = "";
			String costType = map.get(BillingParamEnum.COST_TYPE.getValue());
			String srTypeMark = map.get(BillingParamEnum.SR_TYPE_MARK.getValue());
			if(CommonUtil.isEmpty(map)) { 
				throw new RollbackableBizException("buildOrderData requestParameterMap is null");
			}
			if(CommonUtil.isEmpty(costType)) {
				throw new RollbackableBizException("costType is null");
			}
			if(CommonUtil.isEmpty(srTypeMark)) {
				throw new RollbackableBizException("srTypeMark is null");
			} 
			if(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue().equals(srTypeMark)) {
				orderType = "create";
			}else if(SrTypeMarkEnum.VIRTUAL_EXTEND.getValue().equals(srTypeMark)) {
				if(CostType.DISK.getValue().equals(costType)) {
					orderType = "create";
				}else if(CostType.FLAVOR.getValue().equals(costType)) {
					orderType = "reconfig";
				}else if(CostType.FLOATIP.getValue().equals(costType)) {
					orderType = "create";
				}
				
			}
			Integer cpuNum = null;
			Integer memNum = null;
			Integer diskSize = null;
			String serviceId = null;
			String userId = map.get(BillingParamEnum.USER_ID.getValue());
			String tenantId = map.get(BillingParamEnum.TENANT_ID.getValue());
			String instanceId = map.get(BillingParamEnum.INSTANCE_ID.getValue());
			if(CommonUtil.isEmpty(userId)) {
				throw new RollbackableBizException("userId is null");
			}
			if(CommonUtil.isEmpty(tenantId)) {
				throw new RollbackableBizException("tenantId is null");
			}
			if(CommonUtil.isEmpty(instanceId)) {
				throw new RollbackableBizException("instanceId is null");
			}
			//根据不同的收费类型，进行参数验证
			if(CostType.FLAVOR.getValue().equals(costType)) {
				cpuNum = Integer.parseInt(map.get(BillingParamEnum.CPU_NUM.getValue()));
				memNum = Integer.parseInt(map.get(BillingParamEnum.MEM_NUM.getValue()));
				serviceId = map.get(BillingParamEnum.SERVICE_ID.getValue());
				if(CommonUtil.isEmpty(cpuNum)) {
					throw new RollbackableBizException("cpuNum is null");
				}
				if(CommonUtil.isEmpty(memNum)) {
					throw new RollbackableBizException("memNum is null");
				}
				if(CommonUtil.isEmpty(serviceId)) {
					throw new RollbackableBizException("serviceId is null");
				}
				param.setCpu(cpuNum);
				param.setMem(memNum);
				param.setSerType(serviceId);
				shopList.setService_id("gitcloud_service");
			}else if(CostType.DISK.getValue().equals(costType)) {
				diskSize = Integer.parseInt(map.get(BillingParamEnum.DISK_SIZE.getValue()));
				if(CommonUtil.isEmpty(diskSize)) {
					throw new RollbackableBizException("diskSize is null");
				}
				param.setDisk(1);
				param.setDiskNumber(diskSize);
				param.setDiskType("git_disk");
				shopList.setService_id("gitcloud_disk");
			}else if(CostType.FLOATIP.getValue().equals(costType)) {
				param.setFloatIp(1);
				param.setFloatIpType("git_floatIp");
				shopList.setService_id("gitcloud_floatIp");
			}
			//构建数据
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			order.setCreate_time(df.format(new Date()));
			order.setId(Calendar.getInstance().getTimeInMillis()+"");
			order.setOrder_type(orderType);
			order.setUser_id(userId);
			order.setTenant_id(tenantId);
			shopList.setParameters(param);
			shopList.setInstance_id(instanceId);
			lists.add(shopList);
			order.setShopping_lists(lists);
			orderRoot.setOrder(order);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return orderRoot;
	}

	@Override
	public String createOrderInterface(OrderRoot orderRoot) throws RollbackableBizException {
		String returnStr = null;
		RestModel restModel = new RestModel();
		String orderInterfaceUrl = parameterServiceImpl.getParamValueByName("orderInterfaceUrl");
		if(CommonUtil.isEmpty(orderInterfaceUrl)) {
			throw new RollbackableBizException("orderInterfaceUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(orderRoot);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(orderInterfaceUrl);
		restModel.setRequestMethod(requestMethod);
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			JSONObject jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			String resultCode = jsonObject.get("resultCode").toString();
			//000000：成功订单编码，000007：重复订单编码
			if(resultCode.equals("000000") || resultCode.equals("000007")){
				returnStr = "success";
			}else {
				returnStr = jsonObject.get("resultMessage").toString();
				logger.error("error code:"+resultCode+",error msg :"+returnStr);
			}
		}catch (Exception e) {
			throw new RollbackableBizException(e);
		}
		return returnStr;
	}

	@Override
	public Pagination<BillInfoVo> buildBillingPage(PaginationParam paginationParam) throws Exception {
		BillPageParamVo billPageParam = new BillPageParamVo();
		Map<String,Object> map = paginationParam.getParams();
		String tenantId = map.get("tenantId").toString();
		if(!CommonUtil.isEmpty(tenantId)) {
			List<String> lis = new ArrayList<>();
			lis.add(tenantId);
			billPageParam.setCustIdList(lis);
		}
		billPageParam.setBillMonth(map.get("billMonth").toString());
		String billFeeMinStr = map.get("billFeeMin").toString();
		String billFeeMaxStr = map.get("billFeeMax").toString();
		if(!CommonUtil.isEmpty(billFeeMinStr)) {
			billPageParam.setBillFeeMin(Integer.parseInt(billFeeMinStr) * 1000);
		}
		if(!CommonUtil.isEmpty(billFeeMaxStr)) {
			billPageParam.setBillFeeMax(Integer.parseInt(billFeeMaxStr) * 1000);
		}
		billPageParam.setPageNo(paginationParam.getCurrentPage());
		billPageParam.setPageSize(paginationParam.getPageSize());
		return this.buildBillingList(billPageParam);
	}

	@Override
	public Pagination<BillInfoVo> buildBillingList(BillPageParamVo billPageParam) throws RollbackableBizException {
		String billMoth = billPageParam.getBillMonth();
		Pagination<BillInfoVo> page= new Pagination<>();
		List<BillInfoVo> billingList = new ArrayList<>();
		String returnStr = null;
		RestModel restModel = new RestModel();
		String billingListUrl = parameterServiceImpl.getParamValueByName("queryBillListUrl");
		if(CommonUtil.isEmpty(billingListUrl)) {
			throw new RollbackableBizException("billingListUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(billPageParam);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(billingListUrl);
		restModel.setRequestMethod(requestMethod);
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			JSONObject jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			Boolean result = (Boolean) jsonObject.get("success");
			JSONObject billPageVoObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("billPageVo");
			if(result && billPageVoObject != null){
				returnStr = "success";
				JSONArray billPageList = billPageVoObject.getJSONArray("result");
				Integer record = billPageVoObject.getInteger("count");
				for (Object obj : billPageList) {
					JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
					BillInfoVo billInfoVo = new BillInfoVo();
					billInfoVo.setBillMonth(billMoth);
					TenantPo tenantPo =  tenantService.selectTenant(jsonObj.getString("extCustId"));
					if(!CommonUtil.isEmpty(tenantPo)) {
						billInfoVo.setTenantName(tenantPo.getName());
					}
					billInfoVo.setTenantId(jsonObj.getString("extCustId"));			//租户ID
					billInfoVo.setPaymentState(jsonObj.getString("balanceFee"));	//支付状态
					billInfoVo.setRealIncomeMoney(jsonObj.getString("proceeds"));	//实收金额
					billInfoVo.setBillMoney(jsonObj.getString("adjustFee"));		//账单金额
					billInfoVo.setRemark(jsonObj.getString("subjectName"));			//备注
					billingList.add(billInfoVo);
				}
				page.setDataList(billingList);
				page.setRecord(record);
				page.setPage(billPageParam.getPageNo());
				page.setRows(billPageParam.getPageSize());
				page.setTotal(billPageVoObject.getInteger("pageCount"));
			}else {
				returnStr = jsonObject.get("resultMessage").toString()+"返回对象："+billPageVoObject;
				logger.error(",error msg :"+returnStr);
			}
		}catch (Exception e) {
			throw new RollbackableBizException(e);
		}
		return page;
	}
	
	@Override
	public Pagination<BillInfoVo> buildBillingDetailPage(PaginationParam paginationParam) throws Exception {
		BillPageParamVo billPageParam = new BillPageParamVo();
		Map<String,Object> map = paginationParam.getParams();
		String tenantId = map.get("tenantId").toString();
		String serviceType = map.get("serviceType").toString();
		if(!CommonUtil.isEmpty(tenantId)) {
			List<String> lis = new ArrayList<>();
			lis.add(tenantId);
			billPageParam.setExtCustIds(lis);
		}
		String startDate = map.get("startDate").toString();
		String endDate = map.get("endDate").toString();
		if(!CommonUtil.isEmpty(startDate)) {
			startDate = startDate.replaceAll("-", "");
			billPageParam.setStartDate(startDate+"000000");
		}
		if(!CommonUtil.isEmpty(endDate)) {
			endDate = endDate.replaceAll("-", "");
			billPageParam.setEndDate(endDate+"000000");
		}
		if(!CommonUtil.isEmpty(serviceType)) {
			billPageParam.setServiceType(serviceType);;
		}
		billPageParam.setPageNo(paginationParam.getCurrentPage());
		billPageParam.setPageSize(paginationParam.getPageSize());
		return this.buildBillingDetailList(billPageParam);
	}
	
	public Pagination<BillInfoVo> buildBillingDetailList(BillPageParamVo billPageParam) throws RollbackableBizException {
		Pagination<BillInfoVo> page= new Pagination<>();
		List<BillInfoVo> billingList = new ArrayList<>();
		String returnStr = null;
		RestModel restModel = new RestModel();
		String billingListUrl = parameterServiceImpl.getParamValueByName("queryBillDetailListUrl");
		if(CommonUtil.isEmpty(billingListUrl)) {
			throw new RollbackableBizException("billingListUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(billPageParam);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(billingListUrl);
		restModel.setRequestMethod(requestMethod);
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			JSONObject jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			Boolean result = (Boolean) jsonObject.get("success");
			JSONObject billPageVoObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("pageInfo");
			if(result && billPageVoObject != null){
				returnStr = "success";
				if(billPageVoObject.containsKey("result")&&billPageVoObject.containsKey("count")){
					JSONArray billPageList = billPageVoObject.getJSONArray("result");
					Integer record = billPageVoObject.getInteger("count");
					for (Object obj : billPageList) {
						JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
						BillInfoVo billInfoVo = new BillInfoVo();
						TenantPo tenantPo =  tenantService.selectTenant(jsonObj.getString("extCustId"));
						if(!CommonUtil.isEmpty(tenantPo)) {
							billInfoVo.setTenantName(tenantPo.getName());
						}
						billInfoVo.setTenantId(jsonObj.getString("extCustId"));			//租户ID
						double d = jsonObj.getDoubleValue("fee");
						billInfoVo.setBillMoney(d/1000+"");
						String time = jsonObj.getString("startTime");
						time = TimeUtil.formatLocalDateTimeToString(TimeUtil.stringToLocalDateTime(time, TimeUtil.TIME_PATTERN_SESSION),TimeUtil.TIME_PATTERN);
						billInfoVo.setStartTime(time);
						billInfoVo.setInstanceId(jsonObj.getString("instanceId"));
						billInfoVo.setBillMonth(jsonObj.getString("accountPeriod"));
						billInfoVo.setServiceType(jsonObj.getString("serviceType"));
						String createTiem = TimeUtil.longToStr(jsonObj.getLongValue("createDate"), TimeUtil.TIME_PATTERN);
						billInfoVo.setCreateDate(createTiem);
						billingList.add(billInfoVo);
					}
					page.setDataList(billingList);
					page.setRecord(record);
					page.setPage(billPageParam.getPageNo());
					page.setRows(billPageParam.getPageSize());
					page.setTotal(billPageVoObject.getInteger("pageCount"));
				}
			}else {
				//returnStr = jsonObject.get("resultMessage").toString()+"返回对象："+billPageVoObject;
				logger.error("error msg :");
			}
		}catch (Exception e) {
			throw new RollbackableBizException(e);
		}
		return page;
	}
	
	@Override
	public Pagination<BillInfoVo> buildBillingVoucherPage(PaginationParam paginationParam) throws Exception {
		BillPageParamVo billPageParam = new BillPageParamVo();
		Map<String,Object> map = paginationParam.getParams();
		String tenantId = map.get("tenantId").toString();
		String status = map.get("status").toString();
		billPageParam.setTenantId("ECITIC");
		billPageParam.setExtCustId(tenantId);
		if(!CommonUtil.isEmpty(status)) {
			billPageParam.setStatus(status);
		}
		billPageParam.setPageNo(paginationParam.getCurrentPage());
		billPageParam.setPageSize(paginationParam.getPageSize());
		return this.buildBillingVoucherList(billPageParam);
	}
	
	public Pagination<BillInfoVo> buildBillingVoucherList(BillPageParamVo billPageParam) throws RollbackableBizException {
		Pagination<BillInfoVo> page= new Pagination<>();
		List<BillInfoVo> billingList = new ArrayList<>();
		String returnStr = null;
		RestModel restModel = new RestModel();
		String queryBillVoucherListUrl = parameterServiceImpl.getParamValueByName("queryBillVoucherListUrl");
		if(CommonUtil.isEmpty(queryBillVoucherListUrl)) {
			throw new RollbackableBizException("queryBillVoucherListUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(billPageParam);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(queryBillVoucherListUrl);
		restModel.setRequestMethod(requestMethod);
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			JSONObject jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			String resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				returnStr = "success";
				if(JSONObject.parseObject(returnResult.getJsonData()).containsKey("result")&&JSONObject.parseObject(returnResult.getJsonData()).containsKey("count")){
					JSONArray billPageList = JSONObject.parseObject(returnResult.getJsonData()).getJSONArray("result");
					Integer record = JSONObject.parseObject(returnResult.getJsonData()).getInteger("count");
					for (Object obj : billPageList) {
						JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
						BillInfoVo billInfoVo = new BillInfoVo();
						TenantPo tenantPo =  tenantService.selectTenant(jsonObj.getString("extCustId"));
						if(!CommonUtil.isEmpty(tenantPo)) {
							billInfoVo.setTenantName(tenantPo.getName());
						}
						billInfoVo.setTenantId(jsonObj.getString("extCustId"));			//租户ID
						billInfoVo.setBalance(jsonObj.getString("balance"));
						billInfoVo.setStatus(jsonObj.getString("statusName"));
						String createTime = TimeUtil.longToStr(jsonObj.getLongValue("createTime"), TimeUtil.TIME_PATTERN);
						billInfoVo.setCreateDate(createTime);
						String effectDate = TimeUtil.longToStr(jsonObj.getLongValue("effectDate"), TimeUtil.TIME_PATTERN);
						billInfoVo.setEffectDate(effectDate);
						String expireDate = TimeUtil.longToStr(jsonObj.getLongValue("expireDate"), TimeUtil.TIME_PATTERN);
						billInfoVo.setExpireDate(expireDate);
						billingList.add(billInfoVo);
					}
					page.setDataList(billingList);
					page.setRecord(record);
					page.setPage(billPageParam.getPageNo());
					page.setRows(billPageParam.getPageSize());
					page.setTotal(JSONObject.parseObject(returnResult.getJsonData()).getInteger("pageCount"));
				}
			}else {
				returnStr = jsonObject.get("resultMessage").toString()+"返回对象："+JSONObject.parseObject(returnResult.getJsonData());
				logger.error("error code:"+resultCode+",error msg :"+returnStr);
			}
		}catch (Exception e) {
			throw new RollbackableBizException(e);
		}
		return page;
	}
	
	@Override
	public Pagination<BillInfoVo> buildFundSerialPage(PaginationParam paginationParam) throws Exception {
		BillPageParamVo billPageParam = new BillPageParamVo();
		Map<String,Object> map = paginationParam.getParams();
		String tenantId = map.get("tenantId").toString();
		String beginTime = map.get("beginTime").toString();
		String endTime = map.get("endTime").toString();
		if(CommonUtil.isEmpty(beginTime)){
			return null;
		}
		billPageParam.setTenantId("ECITIC");
		billPageParam.setExtCustId(tenantId);
		billPageParam.setBeginTime(beginTime);
		billPageParam.setEndTime(endTime);
		billPageParam.setOptType("1");
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(paginationParam.getCurrentPage());
		pageInfo.setPageSize(paginationParam.getPageSize());
		billPageParam.setPageInfo(pageInfo);
		return this.buildFundSerialList(billPageParam);
	}
	
	public Pagination<BillInfoVo> buildFundSerialList(BillPageParamVo billPageParam) throws RollbackableBizException {
		Pagination<BillInfoVo> page= new Pagination<>();
		List<BillInfoVo> billingList = new ArrayList<>();
		String returnStr = null;
		RestModel restModel = new RestModel();
		String queryFundSerialListUrl = parameterServiceImpl.getParamValueByName("queryFundSerialListUrl");
		if(CommonUtil.isEmpty(queryFundSerialListUrl)) {
			throw new RollbackableBizException("queryFundSerialListUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(billPageParam);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(queryFundSerialListUrl);
		restModel.setRequestMethod(requestMethod);
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			JSONObject jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			String resultCode = jsonObject.get("resultCode").toString();
			JSONObject billPageVoObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("pageInfo");
			if(resultCode.equals("000000") && billPageVoObject != null){
				returnStr = "success";
				if(billPageVoObject.containsKey("result")&&billPageVoObject.containsKey("count")){
					JSONArray billPageList = billPageVoObject.getJSONArray("result");
					Integer record = billPageVoObject.getInteger("count");
					for (Object obj : billPageList) {
						JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
						BillInfoVo billInfoVo = new BillInfoVo();
						TenantPo tenantPo =  tenantService.selectTenant(jsonObj.getString("custId2"));
						if(!CommonUtil.isEmpty(tenantPo)) {
							billInfoVo.setTenantName(tenantPo.getName());
						}
						billInfoVo.setTenantId(jsonObj.getString("custId2"));			//租户ID
						double d = jsonObj.getDoubleValue("totalAmount");
						billInfoVo.setTotalAmount(d/1000+"");
						billInfoVo.setOptType(jsonObj.getString("optType"));
						String payStatus = jsonObj.getString("payStatus");
						if("1".equals(payStatus)){
							billInfoVo.setPayStatus("成功");
						}else{
							billInfoVo.setPayStatus("失败");
						}
						billInfoVo.setRemark(jsonObj.getString("remark"));
						String createTiem = TimeUtil.longToStr(jsonObj.getLongValue("createTime"), TimeUtil.TIME_PATTERN);
						billInfoVo.setCreateDate(createTiem);
						billingList.add(billInfoVo);
					}
					page.setDataList(billingList);
					page.setRecord(record);
					page.setPage(billPageParam.getPageInfo().getPageNo());
					page.setRows(billPageParam.getPageInfo().getPageSize());
					page.setTotal(billPageVoObject.getInteger("pageCount"));
				}
			}else {
				returnStr = jsonObject.get("resultMessage").toString()+"返回对象："+billPageVoObject;
				logger.error("error code:"+resultCode+",error msg :"+returnStr);
			}
		}catch (Exception e) {
			throw new RollbackableBizException(e);
		}
		return page;
	}
	//	查询账户余额,查询出的单位是厘
	@Override
	public int queryAccountBalance(String tenantId) throws Exception {
		JSONObject queryParamObj = new JSONObject();
		queryParamObj.put("extCustId", tenantId);
		queryParamObj.put("tenantId", "ECITIC");
		String returnStr = "0";
		Integer balance = 0;
		RestModel restModel = new RestModel();
		String queryAccountBalanceUrl = parameterServiceImpl.getParamValueByName("queryAccountBalanceUrl");
		if(CommonUtil.isEmpty(queryAccountBalanceUrl)) {
			throw new RollbackableBizException("queryAccountBalanceUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(queryParamObj);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(queryAccountBalanceUrl);
		restModel.setRequestMethod(requestMethod);
		JSONObject jsonObject = null;
		String resultCode = "";
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				returnStr = JSONObject.parseObject(returnResult.getJsonData()).getString("balance").toString();
			}
		} catch (Exception e) {
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		balance = Integer.parseInt(returnStr)/1000;
		return balance;
	}
	//账户充值
	@Override
	public String accountRecharge(String tenantId, String amount, String busiDesc,String fundTypeCode) throws Exception {
		JSONObject paramObj = new JSONObject();
		JSONObject transObj = new JSONObject();
		ArrayList<JSONObject> obj = new ArrayList<>();
		if(fundTypeCode.equals("6")) {//代金券充值
			transObj.put("subjectId", 500000);
			transObj.put("amount", Integer.parseInt(amount));
		}else if(fundTypeCode.equals("1")) {//现金充值
			transObj.put("subjectId", 100001);
			transObj.put("amount", Integer.parseInt(amount) * 1000);
		}
		obj.add(transObj);
		paramObj.put("busiDesc", busiDesc);
		paramObj.put("busiSerialNo", DateUtil.getCurrentDataString("yyyyMMddHHmmss"));
		paramObj.put("depositTime", DateUtil.getCurrentDataString("yyyyMMddHHmmss"));
		paramObj.put("extCustId", tenantId);
		paramObj.put("tenantId", "ECITIC");
		paramObj.put("transSummary", obj);
		String returnStr = null;
		RestModel restModel = new RestModel();
		String accountRechargeUrl = parameterServiceImpl.getParamValueByName("accountRechargeUrl");
		if(CommonUtil.isEmpty(accountRechargeUrl)) {
			throw new RollbackableBizException("accountRechargeUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(paramObj);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(accountRechargeUrl);
		restModel.setRequestMethod(requestMethod);
		JSONObject jsonObject = null;
		String resultCode = "";
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				returnStr = "000000";
			}
		} catch (Exception e) {
			returnStr = jsonObject.get("resultMessage").toString();
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		return returnStr;
	}

	@Override
	public Pagination<TenantPo> queryTenantBalancePage(PaginationParam paginationParam) throws Exception {
		Pagination<TenantPo> pagination = tenantService.selectTenants(paginationParam);
		List<TenantPo> tenantList = pagination.getDataList();
		List<TenantPo> tenantListNew = new ArrayList<>();
		int balance ;
		for(TenantPo tenant: tenantList) {
			balance = this.queryAccountBalance(tenant.getId());
			tenant.setBalance(String.valueOf(balance));
			tenantListNew.add(tenant);
		}
		pagination.setDataList(tenantListNew);
		return pagination;
	}

	@Override
	public String priceElementAddOrUpdate(String jsonParameter) throws Exception {
		String returnStr = null;
		String url = "";
		RestModel restModel = new RestModel();
		JSONObject parameter = JSON.parseObject(jsonParameter);
		Object type = parameter.get("flag");  //1.修改 2.新增
		if(CommonUtil.isEmpty(type.toString())) {
			throw new RollbackableBizException("type is null");
		} 
		if(type.toString().equals("2")) {
			url = parameterServiceImpl.getParamValueByName("priceElementAddUrl");
		}else if(type.toString().equals("1")){
			url = parameterServiceImpl.getParamValueByName("priceElementUpdatUrl");
		}
		if(CommonUtil.isEmpty(url)) {
			throw new RollbackableBizException("priceElementAddOrUpdateUrl is null");
		}
		String requestMethod = "POST"; 
		String increaseVO = parameter.getString("increaseVO");
	
		String requestJosnData = JSONObject.toJSONString(increaseVO);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(increaseVO);
		restModel.setTargetURL(url);
		restModel.setRequestMethod(requestMethod);
		JSONObject jsonObject = null;
		String resultCode = "";
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				returnStr = "0000";
			}
		} catch (Exception e) {
			returnStr = jsonObject.get("resultMessage").toString();
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		return returnStr;
	}

	@Override
	public Pagination<VariableVOs> queryPriceStrategyById(PaginationParam pagination) throws Exception {
		Pagination<Map> paginat = bmSrDao.pageQuery("queryBmSrRrinfoPriceApprovalTotal", "queryBmSrRrinfoPriceApprovalPage", pagination);
		List<Map> dataList = paginat.getDataList();
		Pagination<VariableVOs> result = new Pagination<>();
		Object parameterStr =  dataList.get(0).get("jsonObj");
		if(CommonUtil.isEmpty(parameterStr)) {
			throw new RollbackableBizException("jsonObj is null");
		}
		JSONObject parameter = JSON.parseObject(parameterStr.toString());
		String policyId = "";
		JSONArray jsonArray = parameter.getJSONObject("increaseVO").getJSONArray("elements");
		for (Object object : jsonArray) {
			JSONObject parseObject = JSON.parseObject(JSON.toJSONString(object));
			policyId = parseObject.getString("pricePolicy");
		}
		if(CommonUtil.isEmpty(policyId)){
			throw new RollbackableBizException("policyId is null");
		}
		List<VariableVOs> variableVOs = new ArrayList<>();
		String returnStr = null;
		RestModel restModel = new RestModel();
		String url = parameterServiceImpl.getParamValueByName("queryPriceStrategyUrl");
		if(CommonUtil.isEmpty(url)) {
			throw new RollbackableBizException("queryPriceStrategyUrl is null");
		}
		String requestMethod = "POST"; 
		JSONObject jso = new JSONObject();
		jso.put("tenantId", "ECITIC");
		jso.put("policyId", policyId.toString());
		String requestJosnData = JSONObject.toJSONString(jso);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(url);
		restModel.setRequestMethod(requestMethod);
		String resultCode = "";
		JSONObject jsonObject = new JSONObject();
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			
			jsonObject = JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				JSONObject	strategy = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("strategyShowVO");
				JSONObject	policyVo = (JSONObject) strategy.getJSONObject("policyVo");
				String policyType = policyVo.getString("policyType");
				JSONArray variableVoList = policyVo.getJSONArray("variableVOs");
				for (Object obj : variableVoList) {
					String factorVoInfo = "";
					JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
					VariableVOs variableVo = new VariableVOs();
					variableVo.setPrice(jsonObj.getString("price"));//价格
					variableVo.setPriceType(jsonObj.getString("priceType"));//价格类型
					JSONArray factorVoArr = jsonObj.getJSONArray("factorVos");
					for (Object factor : factorVoArr) {
						JSONObject faObj = JSONObject.parseObject(factor.toString());
						factorVoInfo += faObj.getString("factorValue") +" "+faObj.getString("varUnitName") +" "+faObj.getString("varName")+"\n";
					}
					variableVo.setPolicyType(policyType);
					variableVo.setFactorVoInfo(factorVoInfo);
					variableVOs.add(variableVo);
				}
			}
		} catch (Exception e) {
			returnStr = jsonObject.get("resultMessage").toString();
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		result.setDataList(variableVOs);
		return result;
	}
	
	@Override
	public Pagination<Map> queryBmSrRrinfoPriceApproval(PaginationParam pagination) throws Exception {
		Pagination<Map> paginat = bmSrDao.pageQuery("queryBmSrRrinfoPriceApprovalTotal", "queryBmSrRrinfoPriceApprovalPage", pagination);
		List<Map> dataList = paginat.getDataList();
		List<Map> data = new ArrayList<>();
		Object parameterStr =  dataList.get(0).get("jsonObj");
		if(CommonUtil.isEmpty(parameterStr)) {
			throw new RollbackableBizException("jsonObj is null");
		}
		JSONObject parameter = JSON.parseObject(parameterStr.toString()).getJSONObject("increaseVO");
		JSONArray elements = parameter.getJSONArray("elements");
		String categoryName = "";
		//策略信息
		if(!CommonUtil.isEmpty(parameter)) {
			String categoryType = parameter.getString("categoryType");
		    Object categoryId = parameter.getString("categoryId");
		    if(!CommonUtil.isEmpty(categoryId)) {
		    	categoryName = this.getSubProductInfo(categoryId.toString());
		    }
			String mainProductName = parameter.getString("mainProductName");//主产品名称
			String billingCycle = parameter.getString("billingCycle");//计费周期
			String billingMode = parameter.getString("billingMode");//计费模式编码
			String modeCode = parameter.getString("modeCode");
			for(Object obj : elements) {
				JSONObject jsonObj  = JSONObject.parseObject(obj.toString());
				Map<String,String> map = new HashMap<String, String>();
				map.put("pricePolicyName", jsonObj.getString("pricePolicyName"));//策略名称
				map.put("specTypeName", jsonObj.getString("specTypeName"));//元素名称
				map.put("pricePolicy", jsonObj.getString("pricePolicy"));//策略id
				map.put("categoryType", categoryType);
				map.put("categoryName", categoryName);//子产品名称
				map.put("mainProductName", mainProductName);//主产品名称
				map.put("billingCycle", billingCycle);
				map.put("billingMode", billingMode);
				map.put("modeCode", modeCode);
				data.add(map);
			}
			paginat.setDataList(data);
		}
		return paginat;
	}

	/**
	 * 根据子产品id查子产品名称
	 */
	@Override
	public String getSubProductInfo(String categoryId) throws Exception {
		JSONObject queryParamObj = new JSONObject();
		queryParamObj.put("categoryId", categoryId);
		queryParamObj.put("tenantId", "ECITIC");
		String returnStr = "";
		RestModel restModel = new RestModel();
		String querySubProductInfoUrl = parameterServiceImpl.getParamValueByName("querySubProductInfoUrl");
		if(CommonUtil.isEmpty(querySubProductInfoUrl)) {
			throw new RollbackableBizException("querySubProductInfoUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(queryParamObj);
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(querySubProductInfoUrl);
		restModel.setRequestMethod(requestMethod);
		JSONObject jsonObject = null;
		String resultCode = "";
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("restInterface return result:{}",returnResult);
			jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				JSONObject obj = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("subProduct");
				returnStr = obj.getString("categoryName").toString();
			}
		} catch (Exception e) {
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		return returnStr;
	}

	@Override
	public Pagination<TenantPo> queryTotalInvoicePage(PaginationParam paginationParam) throws Exception {
		Map<String,Object> map = paginationParam.getParams();
		String billMonth = map.get("billMonth").toString();
		Pagination<TenantPo> pagination = tenantService.selectTenants(paginationParam);
		List<TenantPo> tenantList = pagination.getDataList();
		List<TenantPo> tenantListNew = new ArrayList<>();
		String  totalInvoice ;
		for(TenantPo tenant: tenantList) {
			totalInvoice = this.queryTotalInvoice(tenant.getId(),billMonth);
			tenant.setTotalInvoice(String.valueOf(totalInvoice));
			tenantListNew.add(tenant);
		}
		pagination.setDataList(tenantListNew);
		return pagination;
	}

	@Override
	public String queryTotalInvoice(String tenantId, String billMonth) throws Exception {

		JSONObject queryParamObj = new JSONObject();
		queryParamObj.put("extCustId", tenantId);
		queryParamObj.put("tenantId", "ECITIC");
		queryParamObj.put("billMonth", billMonth);
		String returnStr = "0";
		RestModel restModel = new RestModel();
		String queryAccountBalanceUrl = parameterServiceImpl.getParamValueByName("queryTotalInvoiceUrl");
		if(CommonUtil.isEmpty(queryAccountBalanceUrl)) {
			throw new RollbackableBizException("queryTotalInvoiceUrl is null");
		}
		String requestMethod = "POST"; 
		String requestJosnData = JSONObject.toJSONString(queryParamObj);
		logger.info("queryTotalInvoice requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(queryAccountBalanceUrl);
		restModel.setRequestMethod(requestMethod);
		JSONObject jsonObject = null;
		String resultCode = "";
		try {
			RestResult returnResult = RestClient.sendRestRequest(restModel);
			logger.info("queryTotalInvoice return result:{}",returnResult);
			jsonObject = (JSONObject) JSONObject.parseObject(returnResult.getJsonData()).getJSONObject("responseHeader");
			resultCode = jsonObject.get("resultCode").toString();
			if(resultCode.equals("000000")){
				returnStr = JSONObject.parseObject(returnResult.getJsonData()).getString("totalAmount").toString();
			}
		} catch (Exception e) {
			logger.error("error code:"+resultCode+",error msg :"+returnStr);
		}
		return returnStr;
	}
}

