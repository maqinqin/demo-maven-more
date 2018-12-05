package com.git.cloud.cloudservice.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.cloudservice.dao.IPackageDefDao;
import com.git.cloud.cloudservice.model.po.PackageModel;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本包管理
 * @ClassName: PackageDefDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class PackageDefDaoImpl extends CommonDAOImpl implements IPackageDefDao {

	@Override
	public List loadTree() {
		List<Map> list = (List<Map>) getSqlMapClientTemplate().queryForList(
				"package.loadTree");
		return list;
	}

	@Override
	public PackageModelVO load(String id) {
		List<PackageModel> list = (List<PackageModel>) getSqlMapClientTemplate()
				.queryForList("package.load", id);
		PackageModelVO vo = new PackageModelVO();
		if (list != null && list.size() > 0) {
			for (PackageModel p : list) {
				this.toPackageModelVO(p, vo);
			}
		}
		return vo;
	}

	@Override
	public void delete(String id) {
//		getSqlMapClientTemplate().delete("package.delete1", id);
//		getSqlMapClientTemplate().delete("package.delete2", id);
//		getSqlMapClientTemplate().delete("package.delete3", id);
//		getSqlMapClientTemplate().delete("package.delete", id);
		getSqlMapClientTemplate().update("package.delete01", id);
		getSqlMapClientTemplate().update("package.delete02", id);
		getSqlMapClientTemplate().update("package.delete03", id);
		getSqlMapClientTemplate().update("package.delete0", id);
	}

	@Override
	public PackageModelVO save(PackageModelVO packageModelVO) {
		PackageModelVO ret = null;
		PackageModel m = new PackageModel();
		if (packageModelVO.getId() == null || "".equals(packageModelVO.getId())) {
			this.packageModelVOToEntity(packageModelVO, m, true);
			m.setId(UUIDGenerator.getUUID());
			m.setCreateDateTime(new Date());
			m.setIsActive("Y");
			this.getSqlMapClientTemplate().insert("package.insert", m);
		} else {
			this.packageModelVOToEntity(packageModelVO, m, true);
			m.setId(packageModelVO.getId());
			m.setUpdateDateTime(new Date());
			this.getSqlMapClientTemplate().update("package.update", m);
		}
		ret = this.load(m.getId());
		return ret;
	}

	@Override
	public Map search(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	public com.git.cloud.cloudservice.model.vo.PackageModelVO toPackageModelVO(
			final com.git.cloud.cloudservice.model.po.PackageModel entity) {
		com.git.cloud.cloudservice.model.vo.PackageModelVO target = null;
		if (entity != null) {
			target = new com.git.cloud.cloudservice.model.vo.PackageModelVO();
			this.toPackageModelVO(entity, target);
		}
		return target;
	}

	protected com.git.cloud.cloudservice.model.vo.PackageModelVO toPackageModelVO(
			java.lang.Object[] row) {
		return this.toPackageModelVO(this.toEntity(row));
	}

	protected PackageModel toEntity(java.lang.Object[] row) {
		com.git.cloud.cloudservice.model.po.PackageModel target = null;
		if (row != null) {
			final int numberOfObjects = row.length;
			for (int ctr = 0; ctr < numberOfObjects; ctr++) {
				final java.lang.Object object = row[ctr];
				if (object instanceof com.git.cloud.cloudservice.model.po.PackageModel) {
					target = (PackageModel) object;
					break;
				}
			}
		}
		return target;
	}

	public void packageModelVOToEntity(PackageModelVO source,
			PackageModel target, boolean copyIfNull) {
		if (copyIfNull || source.getName() != null) {
			target.setName(source.getName());
		}
		if (copyIfNull || source.getRemark() != null) {
			target.setRemark(source.getRemark());
		}
		if (copyIfNull || source.getFilePath() != null) {
			target.setFilePath(source.getFilePath());
		}
		if (copyIfNull || source.getFzr() != null) {
			target.setFzr(source.getFzr());
		}
	}

	public void toPackageModelVO(PackageModel source, PackageModelVO target) {
		target.setName(source.getName());
		target.setRemark(source.getRemark());
		target.setFilePath(source.getFilePath());
		target.setFzr(source.getFzr());
		target.setId(source.getId());
	}

	@Override
	public List loadDict(java.util.Map<String, String> params) {
		List<Map> list = (List<Map>) getSqlMapClientTemplate().queryForList(
				params.get("sqlId"), params.get("pid"));
		return list;
	}

	public List<PackageModel> findPackageModelByNameList(List<String> packageNameList) throws RollbackableBizException {
		java.util.Map<String, String> paramMap = new HashMap<String, String> ();
		String packNames = "";
		int len = packageNameList == null ? 0 : packageNameList.size();
		if(len == 0) {
			throw new RollbackableBizException("传入的包名列表为空");
		}
		for(int i=0 ; i<len ; i++) {
			packNames += ",'" + packageNameList.get(i) + "'";
		}
		paramMap.put("packNames", packNames.substring(1));
		return this.findListByParam("findPackageModelByNameList", paramMap);
	}
    //检查包下面有没有有没有模板
	@Override
	public List<ModelModelVO> findByPackageId(String id)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<ModelModelVO> list = (List<ModelModelVO>) getSqlMapClientTemplate().queryForList(
				"package.id",id);
		return list;
	}

	@Override
	public Object checkPackageName(String packageName) {
		return getSqlMapClientTemplate().queryForObject("checkPackageName", packageName);
	}

	@Override
	public Integer checkModelName(String modelName, String packageId) {
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("modelName", modelName);
		params.put("packageId", packageId);
		return (Integer) getSqlMapClientTemplate().queryForObject("checkModelName", params);
	}

	@Override
	public Integer checkScriptName(String scriptName, String modelId) {
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("scriptName", scriptName);
		params.put("modelId", modelId);
		return (Integer) getSqlMapClientTemplate().queryForObject("checkScriptName", params);
	}
	
}
