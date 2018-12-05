package com.git.cloud.resmgt.compute.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.git.cloud.handler.common.Utils;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.compute.handler.ClusterService;
import com.git.cloud.resmgt.compute.handler.ClusterServiceImpl;
import com.git.cloud.resmgt.compute.handler.ScanDeviceStatusCaller;
import com.git.cloud.resmgt.compute.handler.ScanDeviceStatusService;
import com.git.cloud.resmgt.compute.handler.ScanDeviceStatusServiceImpl;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
@Controller
public class ScanDeviceStatusAction implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(ScanDeviceStatusAction.class);
	private ApplicationContext applicationContext;
	private ClusterService clusterServiceImpl;
	private ScanDeviceStatusService scanDeviceStatusService;
	private ParameterService parameterServiceImpl;

	public void scan() throws Exception {
		// 获得所有的集群
		List<RmClusterPo> clusterList = clusterServiceImpl.findAllCluster();
		// 创建线程池，在这里的线程池的尺寸为1，可以修改为其它值
		ExecutorService workers = Executors.newFixedThreadPool(1);
		List<Future<List<ScanResult>>> listFuture = new ArrayList<Future<List<ScanResult>>>();
		// 循环扫描每一组设备，包括物理机和虚拟机
		if(clusterList.size()>0){
			for (RmClusterPo cluster : clusterList) {
				String virtualTypeCode = cluster.getVirtualTypeCode();
				List<CmDevicePo> devices = null;
				if ("KV".equalsIgnoreCase(virtualTypeCode)){
					devices = clusterServiceImpl.findAllHostDevice(cluster.getId());
				} else if("PV".equalsIgnoreCase(virtualTypeCode)){
					devices = clusterServiceImpl.findAllVmDevice(cluster.getId());
				}
				if(devices !=null &&devices.size()>0){
					ScanDeviceStatusCaller callerVm = new ScanDeviceStatusCaller(scanDeviceStatusService, "VM", devices,virtualTypeCode);
					listFuture.add(workers.submit(callerVm));
				}else{
					logger.debug("集群下暂时无虚拟机");
				}
			}	
		}else{
			logger.debug("系统中暂时无集群");
		}
		
		// 直到所有任务执行完成后，关闭线程池
		workers.shutdown();
		// 处理所有的扫描结果
		if (listFuture.size()>0){
			for (Future<List<ScanResult>> ft : listFuture) {
				// 解析返回信息
				try {
					List<ScanResult> scanResults = ft.get();
					// 输出每个设备扫描结果的日志
					if(scanResults!=null && scanResults.size()>0){
						for (ScanResult sr : scanResults) {
							logger.info("【设备ID】：" + sr.getDeviceId() + ",【设备名称】："
									+ sr.getDeviceName() + "【扫描状态】："
									+ sr.getDeviceStatus());
						}
					}
					
				} catch (InterruptedException e) {
					logger.error("获取扫描结果列表异常" + e);
					logger.error("异常exception",e);
					Utils.printExceptionStack(e);
					// 输出错误日志
				} catch (ExecutionException e) {
					logger.error(e.getMessage());
					logger.error("异常exception",e);
					Utils.printExceptionStack(e);
				}

			}
		}else{
			logger.debug("系统中暂时无虚拟机");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
			clusterServiceImpl = applicationContext.getBean(ClusterServiceImpl.class);
			scanDeviceStatusService =  applicationContext.getBean(ScanDeviceStatusServiceImpl.class);
			parameterServiceImpl = (ParameterService)applicationContext.getBean("parameterServiceImpl");
			
			//scan();
			Thread t = new Thread(
				new Runnable() {
					@Override
					public void run() {
						while (true) {
							try {
								scan();
								String mechineScanTime = parameterServiceImpl.getParamValueByName("MechineScanTime");
								final long scanTime = Long.parseLong(mechineScanTime);
								Thread.sleep(scanTime);
							} catch (Exception e) {
								logger.error("异常exception",e);
							}
						}
					}
				}
			);
			t.start();
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
	}

}
