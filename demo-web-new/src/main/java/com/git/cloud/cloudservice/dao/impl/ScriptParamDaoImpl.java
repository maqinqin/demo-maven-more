package com.git.cloud.cloudservice.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.cloudservice.dao.IScriptParamDao;
import com.git.cloud.cloudservice.model.po.ScriptModel;
import com.git.cloud.cloudservice.model.po.ScriptParamModel;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
/**
 * 脚本参数管理
 * @ClassName: ScriptParamDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ScriptParamDaoImpl extends CommonDAOImpl implements IScriptParamDao {

	@Override
	public List<ScriptParamModelVO> loadParamsByScriptId(String id) {
		List<ScriptParamModel> list = (List<ScriptParamModel>) getSqlMapClientTemplate().queryForList("scriptParam.load.params", id);
        // 获取CLOUD_SCRIPT_PARAM 表中 SCRIPT_MODEL_FK = id 的对象 。
		List<ScriptParamModelVO> data = new ArrayList();
		for (ScriptParamModel source : list) {
			ScriptParamModelVO target = new ScriptParamModelVO();      //解析对象过程。
			this.toScriptParamModelVO(source, target);
			data.add(target);
		}
		return data;
	}

	@Override
	public ScriptParamModelVO load(String id) {
		List<ScriptParamModel> list = (List<ScriptParamModel>) getSqlMapClientTemplate().queryForList("scriptParam.load", id);
		ScriptParamModelVO target = new ScriptParamModelVO();
		if (list != null && list.size() > 0) {
			for (ScriptParamModel source : list) {
				this.toScriptParamModelVO(source, target);
			}
		}
		return target;
	}

	@Override
	public void delete(String id) {
		getSqlMapClientTemplate().update("scriptParam.delete0", id);
//		getSqlMapClientTemplate().delete("scriptParam.delete", id);
	}

	@Override
	public ScriptParamModelVO save(ScriptParamModelVO scriptParamModelVO) {

		ScriptParamModelVO ret = null;
		ScriptParamModel m = new ScriptParamModel();
		this.scriptParamModelVOToEntity(scriptParamModelVO, m, true);
		if (scriptParamModelVO.getId() == null || "".equals(scriptParamModelVO.getId())) {
			m.setId(UUIDGenerator.getUUID());
			m.setCreateDateTime(new Date());
			m.setIsActive("Y");
			this.getSqlMapClientTemplate().insert("scriptParam.insert", m);
		} else {
			m.setId(scriptParamModelVO.getId());
			m.setUpdateDateTime(new Date());
			this.getSqlMapClientTemplate().update("scriptParam.update", m);
		}
		ret = this.load(m.getId());
		return ret;
	}

	@Override
	public Map search(Map map) {
		List<ScriptParamModel> list = (List<ScriptParamModel>) getSqlMapClientTemplate().queryForList("scriptParam.load.params", map.get("pid"));
		Map ret = new HashMap();
		List<ScriptParamModelVO> data = new ArrayList();
		for (ScriptParamModel source : list) {
			ScriptParamModelVO target = new ScriptParamModelVO();
			this.toScriptParamModelVO(source, target);
			data.add(target);
		}
		ret.put("data", data);
		return ret;
	}

	public void scriptParamModelVOToEntity(com.git.cloud.cloudservice.model.vo.ScriptParamModelVO source, com.git.cloud.cloudservice.model.po.ScriptParamModel target, boolean copyIfNull) {
		if (copyIfNull || source.getCode() != null) {
			target.setCode(source.getCode());
		}
		if (copyIfNull || source.getName() != null) {
			target.setName(source.getName());
		}
		if (copyIfNull || source.getSplitChar() != null) {
			target.setSplitChar(source.getSplitChar());
		}
		if (copyIfNull || source.getParamType() != null) {
			target.setParamType(source.getParamType());
		}
		if (copyIfNull || source.getParamValType() != null) {
			target.setParamValType(source.getParamValType());
		}
		if (copyIfNull || source.getOrders() != null) {
			target.setOrders(source.getOrders());
		}
		if (source.getScriptModelVO() != null) {
			ScriptModel s = new ScriptModel();
			s.setId(source.getScriptModelVO().getId());
			target.setScriptModel(s);
		}
	}

	public void toScriptParamModelVO(com.git.cloud.cloudservice.model.po.ScriptParamModel source, com.git.cloud.cloudservice.model.vo.ScriptParamModelVO target) {
		target.setId(source.getId());
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setSplitChar(source.getSplitChar());
		target.setParamType(source.getParamType());
		target.setParamValType(source.getParamValType());
		target.setOrders(source.getOrders());
		if (source.getScriptModel() != null) {
			ScriptModelVO s = new ScriptModelVO();
			s.setId(source.getScriptModel().getId());
			target.setScriptModelVO(s);
		}
	}
}
