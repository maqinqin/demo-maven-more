package com.git.cloud.cloudservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.dao.IModelDao;
import com.git.cloud.cloudservice.dao.IPackageDefDao;
import com.git.cloud.cloudservice.dao.IScriptDao;
import com.git.cloud.cloudservice.dao.IScriptParamDao;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
import com.git.cloud.cloudservice.service.IPackageDefService;
import com.git.cloud.common.exception.RollbackableBizException;

/**
 * 脚本管理
 * @ClassName: PackageDefServiceImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class PackageDefServiceImpl implements IPackageDefService {
	private IPackageDefDao packageDefDao;
	private IModelDao modelDao;

	private IScriptDao scriptDao;
	private IScriptParamDao scriptParamDao;
	private Object String;

	/*
	@Override
	public Object load(String id, String type) throws RollbackableBizException {
		if ("1".equals(type)) {
			return this.getPackageDefDao().load(id);
		} else if ("2".equals(type)) {
			return modelDao.load(id);
		} else if ("3".equals(type)) {
			ScriptModelVO s = scriptDao.load(id);
			List<ScriptParamModelVO> list = this.getScriptParamDao().loadParamsByScriptId(s.getId());
			s.setScriptParamModelVOs(list);
			return s;
		} else if ("4".equals(type)) {
			return scriptParamDao.load(id);
		}
		return null;
	}
	*/
	public Object load(String id, String type) throws RollbackableBizException {
		if ("1".equals(type)) {
			return this.loadPackageForLog(id);
		} else if ("2".equals(type)) {
			return this.loadModelForLog(id);
		} else if ("3".equals(type)) {
			ScriptModelVO s = (ScriptModelVO)this.loadScriptForLog(id);
			List<ScriptParamModelVO> list = this.getScriptParamDao().loadParamsByScriptId(s.getId());
			s.setScriptParamModelVOs(list);
			return s;
		} else if ("4".equals(type)) {
			return this.loadScriptParamForLog(id);
		}
		return null;
	}
	
	public Map loadScriptParamsByScript(String scriptId) {
		HashMap map = new HashMap();
		map.put("pid", scriptId);
		Map p = scriptParamDao.search(map);
		return p;
	}

	@Override
	public List loadTree() {
		return this.getPackageDefDao().loadTree();
	}

	@Override
	public PackageModelVO load(String id) {
		PackageModelVO packageModelVO = this.getPackageDefDao().load(id);
		return packageModelVO;
	}
//	@Override
//	public void delete(String id, String type) throws RollbackableBizException {
//		if ("1".equals(type)) {
//			this.deletePackageForLog(id);
//		}
//		if ("2".equals(type)) {
//			this.deleteModelForLog(id);
//		}
//		if ("3".equals(type)) {
//			this.deleteScriptForLog(id);
//		}
//		if ("4".equals(type)) {
//			this.deleteScriptParamForLog(id);
//		}
//	}

//	public Object save(Object object) throws RollbackableBizException {
//		if (object instanceof PackageModelVO){
//			PackageModelVO packageModelVO = (PackageModelVO)object;
////			if (packageModelVO.getId() == null || "".equals(packageModelVO.getId())) {
////				return this.savePackageForLog(packageModelVO);
////			}else {
////				return this.updatePackageForLog(packageModelVO);
////			}
//			return packageModelVO;
//			
//		}
//		else if (object instanceof ModelModelVO){
//			ModelModelVO modelModelVO = (ModelModelVO)object;
//			if (modelModelVO.getId() == null || "".equals(modelModelVO.getId())) {
//				return this.saveModelForLog(modelModelVO);
//			}else {
//				return this.updateModelForLog(modelModelVO);
//			}
//		}
//		else if (object instanceof ScriptModelVO){
//			ScriptModelVO scriptModelVO = (ScriptModelVO)object;
//			if (scriptModelVO.getId() == null || "".equals(scriptModelVO.getId())) {
//				return this.saveScriptForLog(scriptModelVO);
//			}else {
//				return this.updateScriptForLog(scriptModelVO);
//			}
//		}
//		else if (object instanceof ScriptParamModelVO){
//			ScriptParamModelVO scriptParamModelVO = (ScriptParamModelVO)object;
//			if (scriptParamModelVO.getId() == null || "".equals(scriptParamModelVO.getId())) {
//				return this.savescriptParamForLog(scriptParamModelVO);
//			}else {
//				return this.updateScriptParamForLog(scriptParamModelVO);
//			}
//		}
//		return null;
//	}

	@Override
	public Map search(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	public IPackageDefDao getPackageDefDao() {
		return packageDefDao;
	}

	public void setPackageDefDao(IPackageDefDao packageDefDao) {
		this.packageDefDao = packageDefDao;
	}

	public IModelDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(IModelDao modelDao) {
		this.modelDao = modelDao;
	}

	public IScriptDao getScriptDao() {
		return scriptDao;
	}

	public void setScriptDao(IScriptDao scriptDao) {
		this.scriptDao = scriptDao;
	}

	public IScriptParamDao getScriptParamDao() {
		return scriptParamDao;
	}

	public void setScriptParamDao(IScriptParamDao scriptParamDao) {
		this.scriptParamDao = scriptParamDao;
	}

	@Override
	public List loadDict(java.util.Map<String, String> params) {
		return getPackageDefDao().loadDict(params);
	}

	/* (non-Javadoc)
	 * <p>Title:saveModelForLog</p>
	 * <p>Description:</p>
	 * @param modelVO
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#saveModelForLog(com.git.cloud.cloudservice.model.vo.ModelModelVO)
	 */
	@Override
	public ModelModelVO saveModelForLog(ModelModelVO object) throws RollbackableBizException {
		return getModelDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:savePackageForLog</p>
	 * <p>Description:</p>
	 * @param packageModelVO
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#savePackageForLog(com.git.cloud.cloudservice.model.vo.PackageModelVO)
	 */
	@Override
	public PackageModelVO savePackageForLog(PackageModelVO object) throws RollbackableBizException {
		PackageModelVO p = getPackageDefDao().save(object);
		return p;
	}

	/* (non-Javadoc)
	 * <p>Title:savePackageForLog</p>
	 * <p>Description:</p>
	 * @param scriptVO
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#savePackageForLog(com.git.cloud.cloudservice.model.vo.ScriptModelVO)
	 */
	@Override
	public ScriptModelVO saveScriptForLog(ScriptModelVO object) throws RollbackableBizException {
		return getScriptDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:savescriptParamForLog</p>
	 * <p>Description:</p>
	 * @param scriptParamVO
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#savescriptParamForLog(com.git.cloud.cloudservice.model.vo.ScriptParamModelVO)
	 */
	@Override
	public ScriptParamModelVO savescriptParamForLog(ScriptParamModelVO object) throws RollbackableBizException {
		return getScriptParamDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:updateModelForLog</p>
	 * <p>Description:</p>
	 * @param object
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#updateModelForLog(java.lang.Object)
	 */
	@Override
	public ModelModelVO updateModelForLog(ModelModelVO object) throws RollbackableBizException {
		return getModelDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:updatePackageForLog</p>
	 * <p>Description:</p>
	 * @param object
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#updatePackageForLog(java.lang.Object)
	 */
	@Override
	public PackageModelVO updatePackageForLog(PackageModelVO object) throws RollbackableBizException {
		return getPackageDefDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:updateScriptForLog</p>
	 * <p>Description:</p>
	 * @param object
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#updateScriptForLog(java.lang.Object)
	 */
	@Override
	public ScriptModelVO updateScriptForLog(ScriptModelVO object) throws RollbackableBizException {
		return getScriptDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:updateScriptParamForLog</p>
	 * <p>Description:</p>
	 * @param object
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#updateScriptParamForLog(java.lang.Object)
	 */
	@Override
	public ScriptParamModelVO updateScriptParamForLog(ScriptParamModelVO object) throws RollbackableBizException {
		return getScriptParamDao().save(object);
	}

	/* (non-Javadoc)
	 * <p>Title:deleteModelForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#deleteModelForLog(java.lang.String)
	 */
	@Override
	public void deleteModelForLog(String id) throws RollbackableBizException {
		this.getModelDao().delete(id);
	}

	/* (non-Javadoc)
	 * <p>Title:deletePackageForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#deletePackageForLog(java.lang.String)
	 */
	@Override
	public void deletePackageForLog(String id) throws RollbackableBizException {
		this.getPackageDefDao().delete(id);
	}

	/* (non-Javadoc)
	 * <p>Title:deleteScriptForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#deleteScriptForLog(java.lang.String)
	 */
	@Override
	public void deleteScriptForLog(String id) throws RollbackableBizException {
		this.getScriptDao().delete(id);
	}

	/* (non-Javadoc)
	 * <p>Title:deleteScriptParamForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#deleteScriptParamForLog(java.lang.String)
	 */
	@Override
	public void deleteScriptParamForLog(String id) throws RollbackableBizException {
		this.getScriptParamDao().delete(id);
	}

	/* (non-Javadoc)
	 * <p>Title:loadModelForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#loadModelForLog(java.lang.String)
	 */
	@Override
	public ModelModelVO loadModelForLog(String id) throws RollbackableBizException {
		return modelDao.load(id);
	}

	/* (non-Javadoc)
	 * <p>Title:loadPackageForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#loadPackageForLog(java.lang.String)
	 */
	@Override
	public PackageModelVO loadPackageForLog(String id) throws RollbackableBizException {
		return this.getPackageDefDao().load(id);
	}

	/* (non-Javadoc)
	 * <p>Title:loadScriptForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#loadScriptForLog(java.lang.String)
	 */
	@Override
	public ScriptModelVO loadScriptForLog(String id) throws RollbackableBizException {
		return scriptDao.load(id);
	}

	/* (non-Javadoc)
	 * <p>Title:loadScriptParamForLog</p>
	 * <p>Description:</p>
	 * @param id
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.IPackageDefService#loadScriptParamForLog(java.lang.String)
	 */
	@Override
	public ScriptParamModelVO loadScriptParamForLog(String id) throws RollbackableBizException {
		return scriptParamDao.load(id);
	}
  //检查包下面有没有模板
	@Override
	public List<ModelModelVO> getParamByPackageId(String id) throws RollbackableBizException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ModelModelVO> list= packageDefDao.findByPackageId(id);
		return list;
	}
//检查模板下面有没有脚本
	@Override
	public List<ScriptModelVO> getParamByModelId(String id) throws RollbackableBizException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ScriptModelVO>  list= modelDao.findByModelId(id);
		return list;
	}

	@Override
	public Object save(ModelModelVO modelModelVO)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return modelModelVO;
	}

	@Override
	public Object save(ScriptModelVO scriptModelVO)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return scriptModelVO;
	}

	@Override
	public Object save(ScriptParamModelVO scriptParamModelVO)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return scriptParamModelVO;
	}

	@Override
	public Object save(PackageModelVO packageModelVO)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return packageModelVO;
	}

	@Override
	public String checkPackageName(String packageName) {
		// TODO Auto-generated method stub
		Object packageObject = packageDefDao.checkPackageName(packageName);
		return (packageObject==null) ? (0+"") : (1+"");
	}

	@Override
	public boolean checkModelName(String modelName, String packageId) {
		// TODO Auto-generated method stub
		Integer count = packageDefDao.checkModelName(modelName, packageId);
		if(count != null && count == 0)
			return true;
		return false;
	}

	@Override
	public boolean checkScriptName(java.lang.String scriptName, java.lang.String modelId) {
		Integer count = packageDefDao.checkScriptName(scriptName, modelId);
		if(count != null && count == 0)
			return true;
		return false;
	}

}
