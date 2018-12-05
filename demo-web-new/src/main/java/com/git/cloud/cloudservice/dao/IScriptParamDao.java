package com.git.cloud.cloudservice.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
/**
 * 脚本参数管理
 * @ClassName: IScriptParamDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface IScriptParamDao {

	public List<ScriptParamModelVO> loadParamsByScriptId(String id);

	public ScriptParamModelVO load(String id);

	public void delete(String id);

	public ScriptParamModelVO save(ScriptParamModelVO scriptParamModelVO);

	public Map search(Map map);

}
