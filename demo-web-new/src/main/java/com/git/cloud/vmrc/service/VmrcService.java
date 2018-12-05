package com.git.cloud.vmrc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.vmrc.dao.VmrcDao;
import com.git.cloud.vmrc.model.po.VmrcPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;


/**
* @ClassName: VmrcService  
* @Description: vmware remote console service
* @author WangJingxin
* @date 2016年6月27日 下午2:32:04  
*
 */
public class VmrcService {
	private static Logger logger = LoggerFactory.getLogger(VmrcService.class);
  /**
  * @Title: queryVmrcInfoByVmId  
  * @Description: 查询 vmware remote console 连接所需的信息
  * @param vmId 虚拟机ID
  * @return VmrcPo    
   */
  public VmrcPo queryVmrcInfoByVmId(String vmId) throws Exception {
    VmrcPo po  = vmrcDaoHandler.queryVmrcInfoByVmId(vmId);
    po.setConnectPassword(PwdUtil.decryption(po.getConnectPassword()));
    
    // 通过vmware接口获取信息
    HeaderDO header = HeaderDO.CreateHeaderDO();
    header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
    header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
    header.set("DATACENTER_QUEUE_IDEN", "BJ");
    header.setOperationBean("vmrcServiceImpl");
    BodyDO body = BodyDO.CreateBodyDO();
    body.set(GeneralKeyField.VMware.ESXI_HOST_NAME,CloudClusterConstants.VCENTER_URL_HTTPS
        + po.getConnectHost() + CloudClusterConstants.VCENTER_URL_SDK);
    body.set(GeneralKeyField.VMware.ESXI_USERNAME, po.getConnectUsername());
    body.set(GeneralKeyField.VMware.ESXI_PASSWORD, po.getConnectPassword());
    body.setString(GeneralKeyField.VM.VAPP_NAME, po.getDevName());
    IDataObject reqData = DataObject.CreateDataObject();
    reqData.setDataObject(MesgFlds.HEADER, header);
    reqData.setDataObject(MesgFlds.BODY, body);

    IDataObject rspData = null;
    
    try {
    	IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
        rspData = invoker.invoke(reqData, 1200000);
        if (rspData == null) {
          logger.info("请求响应失败!");
        } else {
          HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
              HeaderDO.class);
    		
          if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
            BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
            po.setConnectThumbprint(rspBody.getString(GeneralKeyField.VMware.ESXI_SSL_THUMBPRINT));
            po.setConnectVmpath(rspBody.getString(GeneralKeyField.VMware.VM_PATH_NAME));
            
            
          } else {
            logger.info("信息获取失败!");
            throw new Exception(rspHeader.getString("RET_MESG"));
          }
        }
	} catch (Exception e) {
		logger.error("通过vmware接口获取vmrc信息异常：",e);
		throw(e);
	}
    
    return po;
  }

  @Autowired
  private VmrcDao vmrcDaoHandler;
  
  private ResAdptInvokerFactory resInvokerFactory;

  public ResAdptInvokerFactory getResInvokerFactory() {
	return resInvokerFactory;
  }

  public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
	this.resInvokerFactory = resInvokerFactory;
  }

  public VmrcDao getVmrcDaoHandler() {
    return vmrcDaoHandler;
  }

  public void setVmrcDaoHandler(VmrcDao vmrcDaoHandler) {
    this.vmrcDaoHandler = vmrcDaoHandler;
  }

}
