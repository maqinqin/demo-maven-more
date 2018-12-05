package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本模块管理
 * @ClassName: IModelDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface IModelDao extends ICommonDAO {

	public ModelModelVO load(String id) throws RollbackableBizException;

	public void delete(String id) throws RollbackableBizException;

	public ModelModelVO save(ModelModelVO ModelModelVO) throws RollbackableBizException;

	public Map search(Map map) throws RollbackableBizException;
	
	public void saveCloudImageSoftWareRef(CloudImageSoftWareRef imageSoftWareRef) throws RollbackableBizException;

	public List<ScriptModelVO> findByModelId(String id) throws RollbackableBizException;
	
}
