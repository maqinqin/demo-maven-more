package com.git.cloud.resmgt.compute.model.comparator;

import java.util.Comparator;

public class PmHandlerResult implements Comparator<String[]>{

	

	@Override
	public int compare(String[] o1, String[] o2) {
		
		int flag = o1[0].compareToIgnoreCase(o2[0]);
		if(flag == 0){
			return o1[0].compareToIgnoreCase(o2[0]);
		}
		return flag;
		
	}

}
