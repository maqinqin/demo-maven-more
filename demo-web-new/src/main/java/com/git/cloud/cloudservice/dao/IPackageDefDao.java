package com.git.cloud.cloudservice.dao;

import java.util.List;

import com.git.cloud.cloudservice.model.po.PackageModel;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本包管理
 * @ClassName: IPackageDefDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface IPackageDefDao {

	public PackageModelVO load (String id);

	public void delete(String id);

	public PackageModelVO save(PackageModelVO packageModelVO);

	public Map search(Map map);

	public List loadTree();

	public List loadDict(java.util.Map<String, String> params);
	
	public List<PackageModel> findPackageModelByNameList(List<String> packageNameList) throws RollbackableBizException;

	public List<ModelModelVO> findByPackageId(String id) throws RollbackableBizException;

	public Object checkPackageName(String packageName);

	public Integer checkModelName(String modelName, String packageId);

	public Integer checkScriptName(String scriptName, String modelId);

}
