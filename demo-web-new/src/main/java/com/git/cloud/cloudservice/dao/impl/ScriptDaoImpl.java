package com.git.cloud.cloudservice.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.git.cloud.cloudservice.dao.IScriptDao;
import com.git.cloud.cloudservice.model.po.ModelModel;
import com.git.cloud.cloudservice.model.po.ScriptModel;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptFullPathVo;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本管理
 * @ClassName: ScriptDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ScriptDaoImpl extends CommonDAOImpl implements IScriptDao {

	@Override
	public ScriptModelVO load(String id) {

		List<ScriptModel> list = (List<ScriptModel>) getSqlMapClientTemplate().queryForList("script.load", id);
		ScriptModelVO target = new ScriptModelVO();
		if (list != null && list.size() > 0) {
			for (ScriptModel source : list) {
				this.toScriptModelVO(source, target);
			}
		}
		return target;
	}

	@Override
	public void delete(String id) {
//		getSqlMapClientTemplate().delete("script.delete1", id);
//		getSqlMapClientTemplate().delete("script.delete", id);
		getSqlMapClientTemplate().update("script.delete01", id);
		getSqlMapClientTemplate().update("script.delete0", id);
	}

	@Override
	public ScriptModelVO save(ScriptModelVO scriptModelVO) {
		ScriptModelVO ret = null;
		ScriptModel m = new ScriptModel();
		this.scriptModelVOToEntity(scriptModelVO, m, true);
		if (scriptModelVO.getId() == null || "".equals(scriptModelVO.getId())) {
			m.setId(UUIDGenerator.getUUID());
			m.setCreateDateTime(new Date());
			m.setIsActive("Y");
			this.getSqlMapClientTemplate().insert("script.insert", m);
		} else {
			m.setId(scriptModelVO.getId());
			m.setUpdateDateTime(new Date());
			this.getSqlMapClientTemplate().update("script.update", m);
		}
		ret = this.load(m.getId());
		return ret;
	}

	@Override
	public Map search(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	public void scriptModelVOToEntity(ScriptModelVO source, ScriptModel target, boolean copyIfNull) {
		if (copyIfNull || source.getName() != null) {
			target.setName(source.getName());
		}
		if (copyIfNull || source.getFileName() != null) {
			target.setFileName(source.getFileName());
		}
		if (copyIfNull || source.getHadFz() != null) {
			target.setHadFz(source.getHadFz());
		}
		if (copyIfNull || source.getCheckCode() != null) {
			target.setCheckCode(source.getCheckCode());
		}
		if (copyIfNull || source.getRunUser() != null) {
			target.setRunUser(source.getRunUser());
		}
		if (copyIfNull || source.getRemark() != null) {
			target.setRemark(source.getRemark());
		}
		if (copyIfNull || source.getModelModelVO() != null) {
			ModelModel m = new ModelModel();
			m.setId(source.getModelModelVO().getId());
			target.setModelModel(m);
		}
	}

	public void toScriptModelVO(ScriptModel source, ScriptModelVO target) {
		target.setName(source.getName());
		target.setFileName(source.getFileName());
		target.setHadFz(source.getHadFz());
		target.setCheckCode(source.getCheckCode());
		target.setRunUser(source.getRunUser());
		target.setRemark(source.getRemark());
		target.setId(source.getId());
		ModelModelVO m = new ModelModelVO();
		m.setId(source.getModelModel().getId());
		target.setModelModelVO(m);
	}
	
	public ScriptFullPathVo findScriptFullPath(String scriptId) throws RollbackableBizException {
		java.util.Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("scriptId", scriptId);
		return this.findObjectByMap("findScriptFullPath", paramMap);
	}
	
	public List<ScriptModel> findScriptByModelId(String modelId) throws RollbackableBizException {
		java.util.Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("modelId", modelId);
		return this.findListByParam("findScriptByModelId", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RmGeneralServerVo> findAllScriptServer() throws RollbackableBizException {
		String serverType = "SCRIPT_SERVER";
		return this.getSqlMapClientTemplate().queryForList("findAllGeneralServerByType",serverType);
	}
}
