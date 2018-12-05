package com.git.cloud.appmgt.service;

import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.common.exception.RollbackableBizException;

public interface IAppMagService {
	
	/**
	 * 向数据库添加应用履历
	  * @param @param appStatVo
	  * @return void    返回类型
	 */
	public void addAppStat(AppStatVo appStatVo) throws RollbackableBizException;
	/**
	 * 向数据库添加应用履历
	  * @param @param appStatVo
	  * @return void    返回类型
	 */
	public void insertAppStat(AppStatVo appStatVo) throws RollbackableBizException;
	
}