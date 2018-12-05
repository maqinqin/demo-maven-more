package com.git.cloud.excel.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.excel.model.vo.VmVo;
import com.git.cloud.excel.service.IExcelWriteDataBaseService;

public class ExcelWriteDataBaseAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ExcelWriteDataBaseAction.class);
	
	private IExcelWriteDataBaseService iExcelWriteDataBaseService;
	
	
	
	public IExcelWriteDataBaseService getiExcelWriteDataBaseService() {
		return iExcelWriteDataBaseService;
	}

	public void setiExcelWriteDataBaseService(
			IExcelWriteDataBaseService iExcelWriteDataBaseService) {
		this.iExcelWriteDataBaseService = iExcelWriteDataBaseService;
	}

	@SuppressWarnings("unchecked")
	public void excelWriteDataBase()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileName = request.getParameter("fileName");
		
		String regex = "[*%/\\\\]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fileName);
		fileName = matcher.replaceAll("").trim();
		
		String result = "";
		if(fileName!=null){
			String fileSaveRootPath= ServletActionContext.getServletContext().getRealPath("/uploadexcel"); 
	        String path = fileSaveRootPath +File.separatorChar+fileName;
	        File file = new File(path); 
	        Workbook book = Workbook.getWorkbook(file);
	        Sheet hostSheet = book.getSheet(0);
	        Sheet vmSheet = book.getSheet(1);
	        Sheet dsSheet = book.getSheet(2);
	        
	        List<HostVo> hostList = getHostVoList(hostSheet);
	       
	        List<VmVo> vmList = getVmVoList(vmSheet);
	        
	        List<DataStoreVo> dsList = getDataStoreVoList(dsSheet);
	        
	        result = iExcelWriteDataBaseService.saveDataCenterByCode(hostList, vmList, dsList,fileName);
		}else{
			result = "file name is error!";
		}
        stringOut(result);
	}
	
	/**
	 * 获取EXCEL表中的HOST SHEET页中的数据信息
	 * 将数据信息封装到集合中并返回该集合
	 * @param hostSheet
	 * @return
	 */
	private List<HostVo> getHostVoList(Sheet hostSheet){
		List<HostVo> hostVoList = new ArrayList<HostVo>();
		//获取Sheet表中所包含的总列数   
        
        int rsColumns = hostSheet.getColumns();   

        //获取Sheet表中所包含的总行数   
        int rows = hostSheet.getRows();
        
        for(int i=0;i<rows;i++){
        	
        	Map hostMap = new HashMap();
        	
        	HostVo hostVo = new HostVo();
        	
    		int hang = i+1;
    		if(hang < rows){
    			for(int j=0;j<rsColumns;j++){
        			
        			Cell c = hostSheet.getCell(j, hang);
            	
            		//使用MAP保存EXCEL表中信息
            		getHostVoMap(j, c.getContents(), hostMap);
        		
    			}
    			 
    			//封装到VO对象中
    			getHostVo(hostMap, hostVo);
    			
    			//将对象添加到集合中
    			hostVoList.add(hostVo);
    			
    		
    		}else{
    			
    			break;
    		}
        	
        }
		return hostVoList;
	}
	
	/**
	 * 获取EXCEL表中的HOST SHEET页中的数据信息
	 * 将数据信息封装到集合中并返回该集合
	 * @param vmSheet
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<VmVo> getVmVoList(Sheet vmSheet){
		List<VmVo> vmVoList = new ArrayList<VmVo>();
		//获取Sheet表中所包含的总列数   
        
        int rsColumns = vmSheet.getColumns();   

        //获取Sheet表中所包含的总行数   
        int rows = vmSheet.getRows();
        
        for(int i=0;i<rows;i++){
        	
        	Map vmMap = new HashMap();
        	
        	VmVo vmVo = new VmVo();
        	
    		int hang = i+1;
    		if(hang < rows){
    			for(int j=0;j<rsColumns;j++){
        			
        			Cell c = vmSheet.getCell(j, hang);
            	
            		//使用MAP保存EXCEL表中信息
            		getVmVoMap(j, c.getContents(), vmMap);
        		
    			}
    			 
    			//封装到VO对象中
    			getVmVo(vmMap, vmVo);
    			
    			//将对象添加到集合中
    			vmVoList.add(vmVo);
    			
    			
    		}else{
    			
    			break;
    		}
        	
        }
		return vmVoList;
	}
	
	/**
	 * 获取EXCEL表中的HOST SHEET页中的数据信息
	 * 将数据信息封装到集合中并返回该集合
	 * @param hostSheet
	 * @return
	 */
	private List<DataStoreVo> getDataStoreVoList(Sheet dsSheet){
		List<DataStoreVo> dsVoList = new ArrayList<DataStoreVo>();
		//获取Sheet表中所包含的总列数   
        
        int rsColumns = dsSheet.getColumns();   

        //获取Sheet表中所包含的总行数   
        int rows = dsSheet.getRows();
        
        for(int i=0;i<rows;i++){
        	
        	Map dsMap = new HashMap();
        	
        	DataStoreVo dsVo = new DataStoreVo();
        	
    		int hang = i+1;
    		if(hang < rows){
    			for(int j=0;j<rsColumns;j++){
        			
        			Cell c = dsSheet.getCell(j, hang);
            		//使用MAP保存EXCEL表中信息
            		getDataStoreVoMap(j, c.getContents(), dsMap);
        		
    			}
    			 
    			//封装到VO对象中
    			getDataStoreVo(dsMap, dsVo);
    			
    			//将对象添加到集合中
    			dsVoList.add(dsVo);
    			
    			
    		}else{
    			
    			break;
    		}
        	
        }
		return dsVoList;
	}
	
	@SuppressWarnings("unchecked")
	private void getHostVoMap(int colum,String hostValue,Map hostMap){
		switch (colum) {
		case 0:
			hostMap.put("DATACENTERCODE", hostValue);
			break;
		case 1:
			hostMap.put("CLUSTERCODE", hostValue);
			break;
		case 2:
			hostMap.put("HOST_NAME", hostValue);
			break;
		case 3:
			hostMap.put("HOST_IP", hostValue);
			break;
		case 4:
			hostMap.put("HOST_MEM", hostValue);
			break;
		case 5:
			hostMap.put("HOST_CPU", hostValue);
			break;
		case 6:
			hostMap.put("HOST_SN", hostValue);
			break;
		case 7:
			hostMap.put("NW_RULE_LIST_ID", hostValue);
			break;
		case 8:
			hostMap.put("DEVICE_MODEL_ID", hostValue);
			break;
		case 9:
			hostMap.put("SEAT_ID", hostValue);
			break;
		case 10:
			hostMap.put("HOST_USER_NAME", hostValue);
			break;
		case 11:
			hostMap.put("HOST_PASSWORD", hostValue);
			break;
		default:
			break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void getVmVoMap(int colum,String vmValue,Map vmMap){
		switch (colum) {
		case 0:
			vmMap.put("DATACENTERCODE", vmValue);
			break;
		case 1:
			vmMap.put("CLUSTERCODE", vmValue);
			break;
		case 2:
			vmMap.put("HOST_NAME", vmValue);
			break;
		case 3:
			vmMap.put("VM_NAME", vmValue);
			break;
		case 4:
			vmMap.put("VM_MEM", vmValue);
			break;
		case 5:
			vmMap.put("VM_CPU", vmValue);
			break;
		case 6:
			vmMap.put("DU_ID", vmValue);
			break;
		case 7:
			vmMap.put("VM_IP", vmValue);
			break;
		default:
			break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void getDataStoreVoMap(int colum,String dsValue,Map dsMap){
		switch (colum) {
		case 0:
			dsMap.put("DS_NAME", dsValue);
			break;
		case 1:
			dsMap.put("PATH", dsValue);
			break;
		case 2:
			dsMap.put("HOST_NAME", dsValue);
			break;
		case 3:
			dsMap.put("DRIVCE_NAME", dsValue);
			break;
		default:
			break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void getHostVo(Map hostMap,HostVo hostVo){
		hostVo.setClusterCode((String) hostMap.get("CLUSTERCODE"));
		hostVo.setDataCenterCode((String)hostMap.get("DATACENTERCODE"));
		hostVo.setHostName((String)hostMap.get("HOST_NAME"));
		hostVo.setHostIp((String)hostMap.get("HOST_IP"));
		int memValue = Integer.parseInt((String)hostMap.get("HOST_MEM")) * 1024;
		hostVo.setHostMem(String.valueOf(memValue));
		hostVo.setHostCpu((String)hostMap.get("HOST_CPU"));
		hostVo.setHostSn((String)hostMap.get("HOST_SN"));
		hostVo.setNwRuleListId((String)hostMap.get("NW_RULE_LIST_ID"));
		hostVo.setDeviceModelId((String)hostMap.get("DEVICE_MODEL_ID"));
		hostVo.setSeatId((String)hostMap.get("SEAT_ID"));
		hostVo.setHostUser((String)hostMap.get("HOST_USER_NAME"));
		hostVo.setHostPwd((String)hostMap.get("HOST_PASSWORD"));
	}
	
	@SuppressWarnings("unchecked")
	private void getVmVo(Map vmMap,VmVo vmVo){
		vmVo.setClusterCode((String) vmMap.get("CLUSTERCODE"));
		vmVo.setDataCenterCode((String)vmMap.get("DATACENTERCODE"));
		vmVo.setHostName((String)vmMap.get("HOST_NAME"));
		/*try {
			String id = iExcelWriteDataBaseService.selecthostIdByName((String)vmMap.get("HOST_NAME"));
			vmVo.setId(id);
		} catch (RollbackableBizException e) {
			logger.error("获取信息异常" + e);
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}*/
		vmVo.setVmName((String)vmMap.get("VM_NAME"));
		int memValue = Integer.parseInt((String) vmMap.get("VM_MEM")) * 1024;
		vmVo.setVmMem(String.valueOf(memValue));
		vmVo.setVmCpu((String)vmMap.get("VM_CPU"));
		vmVo.setDuId((String)vmMap.get("DU_ID"));
		vmVo.setVmIp((String)vmMap.get("VM_IP"));
	}
	
	@SuppressWarnings("unchecked")
	private void getDataStoreVo(Map dsMap,DataStoreVo dataStoreVo){
		dataStoreVo.setDsName((String) dsMap.get("DS_NAME"));
		dataStoreVo.setPath((String)dsMap.get("PATH"));
		dataStoreVo.setHostName((String)dsMap.get("HOST_NAME"));
		dataStoreVo.setDrivceName((String)dsMap.get("DRIVCE_NAME"));
		
	}
}
