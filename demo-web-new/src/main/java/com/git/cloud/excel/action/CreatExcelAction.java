package com.git.cloud.excel.action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.excel.model.po.ExcelInfoPo;
import com.git.cloud.excel.service.IExcelInfoService;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

@SuppressWarnings({ "serial", "rawtypes" })
public class CreatExcelAction extends BaseAction {

	
	private ResAdptInvokerFactory resInvokerFactory;
	
	private IExcelInfoService iExcelInfoService;
	
	
	
	public IExcelInfoService getiExcelInfoService() {
		return iExcelInfoService;
	}



	public void setiExcelInfoService(IExcelInfoService iExcelInfoService) {
		this.iExcelInfoService = iExcelInfoService;
	}



	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}



	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}



	/**
	 * 调用AMQ接口，传递参数是下拉框类型,username,password，
	 * 返回XML报文，解析报文HEAD文件，为true时解析报文BODY，为FALSE时返回页面保存失败信息
	 * 为TRUE时需要将返回报文生成EXCEL文件，并上传到服务器上
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void createExcelAction()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String url_ip = request.getParameter("url_ip");
		String amqType = request.getParameter("amqType");
		String fileName = "";
		String filePath = "";
		//调用AMQ接口
		Map fileMap = getAmqXml(url_ip,username,password,amqType,fileName,filePath);
		String result=(String)fileMap.get("result");
		HashMap map = new HashMap();
		map.put("fileType", amqType);
		//跟据文件类型查询数据库中是否已经存在该信息
		ExcelInfoPo excelInfoPo = iExcelInfoService.showExcelInfoByType(map);
		
		if(excelInfoPo == null){//新增
			ExcelInfoPo ei = new ExcelInfoPo();
			ei.setFileName((String)fileMap.get("fileName"));
			ei.setFileType(amqType);
			ei.setFilePath((String)fileMap.get("filePath"));
			ei.setIsInput("N");
			Date d = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			sdf.
			ei.setUpdateDateTime(d);
			//新增方法
			iExcelInfoService.insertExcelInfo(ei);
		}else{//修改
			excelInfoPo.setFileName((String)fileMap.get("fileName"));
			excelInfoPo.setIsInput("N");
			//修改方法 
			iExcelInfoService.updateExcelInfo(excelInfoPo);
			
		}
		stringOut(result);
		
	}
	
	
	
	/**
	 * 调用AMQ接口，解析返回报文。
	 */
	private Map getAmqXml(String url_ip,String username,String password,String aqmType,String fileName,String filePath) throws Exception{
		String result = "";
		Map fileMap = new HashMap();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		BodyDO body = BodyDO.CreateBodyDO();
		if("vcenter".equals(aqmType)){
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			body.set(VMFlds.URL, "https://"+url_ip+"/sdk");
			fileName = "VC";
		}else if("kvm".equals(aqmType)){
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			body.set(VMFlds.URL, url_ip);
			fileName = "KVM";
		}else if("xen".equals(aqmType)){
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			body.set(VMFlds.URL, url_ip);
			fileName = "XEN";
		}else if("power".equals(aqmType)){
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			body.set(VMFlds.URL, url_ip);
			fileName = "POWER";
		}else{
			throw new Exception();
		}
		header.setOperationBean("queryInfoServiceImpl");
		header.set("DATACENTER_QUEUE_IDEN", "BJ");
		body.set(VMFlds.USERNAME, username);
		body.set(VMFlds.PASSWORD, password);

		IDataObject reqData = DataObject.CreateDataObject();
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		
		
		IDataObject rspData = null;
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
		rspData = invoker.invoke(reqData, 1200000);
		
		//初始化集合用于接收报文中的HOSTINFO,VMINFO,DATASTOREINFO集合
		List<DataObject> hostlist = new ArrayList();
		List<DataObject> vmlist = new ArrayList();
		List<DataObject> datastoreList = new ArrayList();
		if (rspData == null) {
			result = "请求响应失败!";
		} else {
			HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
					HeaderDO.class);
			BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,
					BodyDO.class);
			result="获取信息成功！";
			if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
				hostlist = rspBody.getList("HOSTINFO");
				vmlist = rspBody.getList("VMINFO");
				datastoreList = rspBody.getList("DATASTOREINFO");
				
				//调用创建EXCEL方法
				fileMap = createExcel(hostlist, vmlist, datastoreList, fileName);
				
				
			}else{
				result = rspHeader.getRetMesg();
				
			}
			fileMap.put("result", result);
		}
		return fileMap;
	}
	/**
	 * 生成EXCEL方法
	 * @param hostList
	 * @param vmList
	 * @param dataList
	 * @param type
	 * @throws Exception
	 */
	private Map createExcel(List hostList,List vmList,List dataList,String fileName) throws Exception{
		Map fileMap = new HashMap();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datTime = sdf.format(d);
		fileName = fileName +"-"+ datTime+".xls";
		
		String pathDirectory = ServletActionContext.getServletContext().getRealPath("/uploadexcel"); 
		File f = new File(pathDirectory);
		
		if(!f.exists() && !f.isDirectory()){
			 /*System.out.println("//不存在");*/
			 f.mkdirs();
			 f = new File(pathDirectory+"/"+fileName);
			 fileMap.put("fileName", fileName);
			 fileMap.put("filePath", pathDirectory);
		}else{
			f = new File(pathDirectory+"/"+fileName);
			 fileMap.put("fileName", fileName);
			 fileMap.put("filePath", pathDirectory);
		}
		OutputStream os = new FileOutputStream(f);
		
		
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet hostsheet = workbook.createSheet("HOSTINFO", 0);
        WritableSheet vmsheet = workbook.createSheet("VMINFO", 1);
        WritableSheet datasheet = workbook.createSheet("DATASTOREINFO", 2);
        //调用HOSINFO sheet页
        getHostSheet(hostsheet, hostList);
        
        //调用VMINFO sheet页
        getVmSheet(vmsheet, vmList);
        
        //调用DATASTOREINFO sheet页
        getDataSheet(datasheet, dataList);
        
        workbook.write();
        workbook.close();
        os.close();
        return fileMap;
		
	}
	
	/**
	 * HOSTINFO sheet页
	 * @param hostsheet
	 * @param hostList
	 * @throws Exception
	 */
	private void getHostSheet(WritableSheet hostsheet,List hostList)throws Exception{
		//创建HOSTINFO标题
        Label dataCenterCode = new Label(0,0,"数据中心英文编码");
        hostsheet.addCell(dataCenterCode);
        Label clusterCode = new Label(1,0,"集群英文编码");
        hostsheet.addCell(clusterCode);
        Label hostName = new Label(2,0,"物理机名称");
        hostsheet.addCell(hostName);
        Label hostIp = new Label(3,0,"物理机IP");
        hostsheet.addCell(hostIp);
        Label hostMem = new Label(4,0,"物理机内存");
        hostsheet.addCell(hostMem);
        Label hostCpu = new Label(5,0,"物理机CPU核数");
        hostsheet.addCell(hostCpu);
        Label hostSn = new Label(6,0,"物理机sn号");
        hostsheet.addCell(hostSn);
        Label nwRuleListId = new Label(7,0,"IP分配规则");
        hostsheet.addCell(nwRuleListId);
        Label  deviceModelId = new Label(8,0,"设备类型ID");
        hostsheet.addCell(deviceModelId);
        Label  seatId = new Label(9,0,"位置编码");
        hostsheet.addCell(seatId);
        Label  hostUserName = new Label(10,0,"物理机用户名");
        hostsheet.addCell(hostUserName);
        Label  hostPassword = new Label(11,0,"物理机密码");
        hostsheet.addCell(hostPassword);
        
        //遍历HOST集合，填入到EXCEL表中
        for(int i=0;i<hostList.size();i++){
        	
        	DataObject object = (DataObject) hostList.get(i);
   
 
        	//创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
            Label dcode = new Label(0,i+1,object.getString("DATACENTERCODE"));
            hostsheet.addCell(dcode);
            Label ccode = new Label(1,i+1,object.getString("CLUSTERCODE"));
            hostsheet.addCell(ccode);
            Label hname = new Label(2,i+1,object.getString("HOST_NAME"));
            hostsheet.addCell(hname);
            Label hip = new Label(3,i+1,object.getString("HOST_IP"));
            hostsheet.addCell(hip);
            Label hmem = new Label(4,i+1,object.getString("HOST_MEM"));
            hostsheet.addCell(hmem);
            Label hcpu = new Label(5,i+1,object.getString("HOST_CPU"));
            hostsheet.addCell(hcpu);
            Label hsn = new Label(6,i+1,object.getString("HOST_SN"));
            hostsheet.addCell(hsn);
            Label nrlid = new Label(7,i+1,object.getString("NW_RULE_LIST_ID"));
            hostsheet.addCell(nrlid);
            Label deviceModel = new Label(8,i+1,object.getString("DEVICE_MODEL_ID"));
            hostsheet.addCell(deviceModel);
            Label seatI = new Label(9,i+1,object.getString("SEAT_ID"));
            hostsheet.addCell(seatI);
            Label hostUser = new Label(10,i+1,object.getString("HOST_USER_NAME"));
            hostsheet.addCell(hostUser);
            Label hostPwd = new Label(11,i+1,object.getString("HOST_PASSWORD"));
            hostsheet.addCell(hostPwd);
        }
	}
	
	/**
	 * VM sheet页
	 * @param vmsheet
	 * @param vmList
	 * @throws Exception
	 */
	private void getVmSheet(WritableSheet vmsheet,List vmList)throws Exception{
		//创建VMINFO标题
        Label dataCenterCode = new Label(0,0,"数据中心英文编码");
        vmsheet.addCell(dataCenterCode);
        Label clusterCode = new Label(1,0,"集群英文编码");
        vmsheet.addCell(clusterCode);
        Label hostName = new Label(2,0,"物理机名称");
        vmsheet.addCell(hostName);
        Label vmName = new Label(3,0,"虚拟机名称");
        vmsheet.addCell(vmName);
        Label vmMem = new Label(4,0,"虚拟机内存");
        vmsheet.addCell(vmMem);
        Label vmCpu = new Label(5,0,"CPU");
        vmsheet.addCell(vmCpu);
        Label groupId = new Label(6,0,"服务器角色ID");
        vmsheet.addCell(groupId);
        Label vmIp = new Label(7,0,"虚拟机IP");
        vmsheet.addCell(vmIp);
    
        
        //遍历VM集合，填入到EXCEL表中
        for(int i=0;i<vmList.size();i++){
        	
        	DataObject object = (DataObject) vmList.get(i);
   
 
        	//创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
            Label dcode = new Label(0,i+1,object.getString("DATACENTERCODE"));
            vmsheet.addCell(dcode);
            Label ccode = new Label(1,i+1,object.getString("CLUSTERCODE"));
            vmsheet.addCell(ccode);
            Label hname = new Label(2,i+1,object.getString("HOST_NAME"));
            vmsheet.addCell(hname);
            Label vname = new Label(3,i+1,object.getString("VM_NAME"));
            vmsheet.addCell(vname);
            Label vmem = new Label(4,i+1,object.getString("VM_MEM"));
            vmsheet.addCell(vmem);
            Label vcpu = new Label(5,i+1,object.getString("VM_CPU"));
            vmsheet.addCell(vcpu);
            Label duid = new Label(6,i+1,object.getString("DU_ID"));
            vmsheet.addCell(duid);
            Label vIP = new Label(7,i+1,object.getString("VM_IP"));
            vmsheet.addCell(vIP);
        }
	}
	
	/**
	 * DATASTOREINFO sheet页
	 * @param datasheet
	 * @param dataList
	 * @throws Exception
	 */
	private void getDataSheet(WritableSheet datasheet,List dataList)throws Exception{
		//创建DATASTOREINFO标题
        Label saveName = new Label(0,0,"存储名称");
        datasheet.addCell(saveName);
        Label savePath = new Label(1,0,"存储路径");
        datasheet.addCell(savePath);
        Label hostName = new Label(2,0,"物理机名称");
        datasheet.addCell(hostName);
        Label saveDrivceName = new Label(3,0,"存储设备名称");
        datasheet.addCell(saveDrivceName);
      
    
        
        //遍历DATASTOREINFO集合，填入到EXCEL表中
        for(int i=0;i<dataList.size();i++){
        	
        	DataObject object = (DataObject) dataList.get(i);
   
 
        	//创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
            Label sname = new Label(0,i+1,object.getString("DS_NAME"));
            datasheet.addCell(sname);
            Label path = new Label(1,i+1,object.getString("PATH"));
            datasheet.addCell(path);
            Label hname = new Label(2,i+1,object.getString("HOST_NAME"));
            datasheet.addCell(hname);
            Label sdname = new Label(3,i+1,object.getString("DRIVCE_NAME"));
            datasheet.addCell(sdname);
        }
	}
	
	
}