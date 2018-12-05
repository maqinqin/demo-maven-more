package com.git.cloud.appmgt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.appmgt.model.po.AppStatPo;
import com.git.cloud.appmgt.model.vo.AppMagActVo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.service.IAppMagService;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;


/**
 * @author git
 */
public class AppMagServiceImpl extends CommonDAOImpl implements IAppMagService{
	
	/**
	* @Title: getMonthSataData  
	* @Description: 将数据库查询结果处理成每个月的数据
	* @param  sqlData
	* @return List<AppMagActVo>    返回类型  
	 */
	private List<AppMagActVo> getMonthSataData(List<AppMagActVo> sqlData){
		List<AppMagActVo> resList = new ArrayList<AppMagActVo>();
		Map<Date,AppMagActVo> monthMap = new HashMap<Date, AppMagActVo>();
		
		// 获取过去的12个月
		for(int i=0;i<12;i++) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.MONTH, -i + 1);  
			
		    //String month = "" + cal.get(Calendar.MONTH) + 1;
		    //String year = "" + cal.get(Calendar.YEAR);
		    //String currMon = year + "-" + (month.length()==2 ? month : "0" + month) + "-01";
			monthMap.put(cal.getTime(), new AppMagActVo());
		}
		// 为每个月赋值
		for(Date mon : monthMap.keySet()){
			Map<String,AppMagActVo> deviceMap = new HashMap<String, AppMagActVo>();
			for(AppMagActVo vo : sqlData){
				if(vo.getCreateDateTime() != null){
					if(vo.getCreateDateTime().getTime() > mon.getTime()) {
						break;
					}	
					// 当出现当前device的新值时，覆盖原有的值
					deviceMap.put(vo.getDeviceId(), vo);
				}
				
			}
			AppMagActVo monVo = new AppMagActVo();
			int cpuNum = 0;
			int memNum = 0;
			int diskNum = 0;
			
			// 将device 的值相加来获得当月的总和
			for(String deviceKey : deviceMap.keySet()) {
				cpuNum += Integer.parseInt(deviceMap.get(deviceKey).getCpuNum());
				memNum += Integer.parseInt(deviceMap.get(deviceKey).getMemNum());
				diskNum += Integer.parseInt(deviceMap.get(deviceKey).getDiskNum());
			}
			monVo.setCreateDateTime(mon);
			monVo.setCpuNum(cpuNum + "");
			monVo.setMemNum(memNum + "");
			monVo.setDiskNum(diskNum + "");
			
			resList.add(monVo);
		}
		
		
		return resList;
	}
	
	@Override
	public void addAppStat(AppStatVo appStatVo) throws RollbackableBizException {
		//初始化参数
		AppStatPo po = new AppStatPo();
		//获取ID
		po.setStatID(UUIDGenerator.getUUID());
		po.setAppID(appStatVo.getAppID());
		po.setDuID(appStatVo.getDuID());
		po.setDataCenterID(appStatVo.getDataCenterID());
		po.setSrStatusCode(appStatVo.getSrStatusCode());
		po.setSrTypeMark(appStatVo.getSrTypeMark());
		po.setDiviceID(appStatVo.getDiviceID());
		po.setCpu(appStatVo.getCpu());
		po.setMem(appStatVo.getMem());
		po.setDisk(appStatVo.getDisk());
		po.setServiceID(appStatVo.getServiceID());
		//获取系统时间
		po.setCreateTime(new Date());
		//执行SQL
		super.save("insertAppStat", po);
		
	}

	@Override
	public void insertAppStat(AppStatVo appStatVo) throws RollbackableBizException {
		//初始化参数
		AppStatPo po = new AppStatPo();
		//获取ID
		po.setStatID(UUIDGenerator.getUUID());
		po.setAppID(appStatVo.getAppID());
		po.setDuID(appStatVo.getDuID());
		po.setDataCenterID(appStatVo.getDataCenterID());
		po.setSrStatusCode(appStatVo.getSrStatusCode());
		po.setSrTypeMark(appStatVo.getSrTypeMark());
		po.setDiviceID(appStatVo.getDiviceID());
		po.setCpu(appStatVo.getCpu());
		po.setMem(appStatVo.getMem());
		po.setDisk(appStatVo.getDisk());
		po.setServiceID(appStatVo.getServiceID());
		//获取系统时间
		po.setCreateTime(new Date());
		//执行SQL
		super.save("insertAppStat", po);
	}
}