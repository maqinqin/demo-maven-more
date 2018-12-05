package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.cloudservice.model.po.ScriptModel;
import com.git.cloud.cloudservice.model.vo.ScriptFullPathVo;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本管理
 * @ClassName: IScriptDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface IScriptDao extends ICommonDAO {

	public ScriptModelVO load(String id);

	public void delete(String id);

	public ScriptModelVO save(ScriptModelVO ScriptModelVO);

	public Map search(Map map);

	/**
	 * 根据脚本Id获取脚本全路径
	 * @Title: findScriptFullPath
	 * @Description: TODO
	 * @field: @param scriptId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return ScriptFullPathVo
	 * @throws
	 */
	public ScriptFullPathVo findScriptFullPath(String scriptId) throws RollbackableBizException;
	
	/**
	 * 根据脚本模块Id获取脚本
	 * @Title: findScriptByModelId
	 * @Description: TODO
	 * @field: @param modelId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<ScriptModel>
	 * @throws
	 */
	public List<ScriptModel> findScriptByModelId(String modelId) throws RollbackableBizException;

	public List<RmGeneralServerVo> findAllScriptServer() throws RollbackableBizException;
}
