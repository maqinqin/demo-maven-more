package com.git.cloud.resmgt.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.constants.SAConstants;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class AutomationHostsManager {
	private static Logger logger = LoggerFactory.getLogger(AutomationHostsManager.class);

	public synchronized static String updateHosts(IResAdptInvoker invoker,
			Map<String, Object> contextParams) {
		String result = "";
		IDataObject rspData = null;

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();

		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
				contextParams.get("DC_QUEUE"));

		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(SAOpration.EXEC_SHELL);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		/**
		 * 要知道Key的命名规则： 脚本路径、脚本名、执行参数等 然后从contenxtParmas中将以上这些Key对应的值取出来拼成cmd
		 */
		body.setString(SAConstants.SERVER_IP,
				contextParams.get(SAConstants.SERVER_IP).toString());
		body.setString(SAConstants.USER_NAME,
				contextParams.get(SAConstants.USER_NAME).toString());
		body.setString(SAConstants.USER_PASSWORD,
				contextParams.get(SAConstants.USER_PASSWORD).toString());
		if (contextParams.get(SAConstants.SERVER_NAME) != null) {
			body.setString(SAConstants.SERVER_NAME,
					contextParams.get(SAConstants.SERVER_NAME).toString());
		}
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");

		List<String> cmdList = (List<String>) contextParams
				.get(SAConstants.CMD_LIST);
		body.setList(SAConstants.EXEC_SHELL, cmdList); // 至此body生成完毕

		reqData.setDataObject(MesgFlds.BODY, body);

		try {
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "";
			} else {
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(header1.getRetCode())) {
					result = MesgRetCode.SUCCESS;
				} else {
					result = header1.getRetMesg();
				}
			}

		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}
}
