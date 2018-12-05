package com.git.cloud.resmgt.compute.model.comparator;

import java.util.Comparator;

import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;

public class ScanVmResultVoComparator implements Comparator<ScanVmResultVo>{

	@Override
	public int compare(ScanVmResultVo o1, ScanVmResultVo o2) {
		int flag = o1.getVmName().compareToIgnoreCase(o2.getVmName());
		if(flag == 0){
			return o1.getAddDuName().compareToIgnoreCase(o2.getAddDuName());
		}
		return flag;
	}
}
