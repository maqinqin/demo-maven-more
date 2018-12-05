package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;

public class ScanDeviceStatusCaller implements Callable<List<ScanResult>> {
	
	ScanDeviceStatusService scanDeviceStatusService;
	String deviceType = null;
	List<CmDevicePo> devices = null;
	String virtualTypeCode = null; 
	/**
	 * <p>Title:</p>
	 * <p>Description:</p>
	 * @param scanDeviceStatusService
	 * @param deviceType
	 * @param deviceIds
	 */
	public ScanDeviceStatusCaller(ScanDeviceStatusService scanDeviceStatusService, String deviceType, List<CmDevicePo> devices,String virtualTypeCode) {
		super();
		this.scanDeviceStatusService = scanDeviceStatusService;
		this.deviceType = deviceType;
		this.devices = devices;
		this.virtualTypeCode = virtualTypeCode;
	}



	/* (non-Javadoc)
	 * <p>Title:call</p>
	 * <p>Description:</p>
	 * @return
	 * @throws Exception
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public List<ScanResult> call() throws Exception {
		List<ScanResult> list = new ArrayList<ScanResult>();
		if (this.deviceType.equals("HOST")) {
			list =  scanDeviceStatusService.scanAndUpdateHostIndicator(devices);
		} else if (this.deviceType.equals("VM")) {
			list =  scanDeviceStatusService.scanAndUpdateVmIndicator(devices,virtualTypeCode);
		}
		return list;
	}

}
