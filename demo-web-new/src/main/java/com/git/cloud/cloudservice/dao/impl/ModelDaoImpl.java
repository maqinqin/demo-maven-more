package com.git.cloud.cloudservice.dao.impl;

import java.util.Date;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.cloudservice.dao.IModelDao;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;
import com.git.cloud.cloudservice.model.po.ModelModel;
import com.git.cloud.cloudservice.model.po.PackageModel;
import com.git.cloud.cloudservice.model.vo.ModelModelVO;
import com.git.cloud.cloudservice.model.vo.PackageModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
/**
 * 脚本模块管理
 * @ClassName: ModelDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ModelDaoImpl extends CommonDAOImpl implements IModelDao {

	@Override
	public ModelModelVO load(String id)  throws RollbackableBizException{
		List<ModelModel> list =super.findByID("model.load", id);// (List<ModelModel>)findByID("model.load", id);
		ModelModelVO target = new ModelModelVO();
		if (list != null && list.size() > 0) {
			for (ModelModel source : list) {
				this.toModelModelVO(source, target);
			}
		}
		return target;
	}

	@Override
	public void delete(String id) throws RollbackableBizException {
		/*super.deleteForIsActive("model.delete01", id);
		super.deleteForIsActive("model.delete02", id);*/
		super.deleteForIsActive("model.delete0", id);
//		this.getSqlMapClientTemplate().update("model.delete0", id);
//		this.getSqlMapClientTemplate().delete("model.delete1", id);
//		this.getSqlMapClientTemplate().delete("model.delete2", id);
//		this.getSqlMapClientTemplate().delete("model.delete", id);

	}

	@Override
	public ModelModelVO save(ModelModelVO modelModelVO) throws RollbackableBizException {
		ModelModelVO ret = null;
		ModelModel m = new ModelModel();
		if (modelModelVO.getId() == null || "".equals(modelModelVO.getId())) {
			this.modelModelVOToEntity(modelModelVO, m, true);
			m.setId(UUIDGenerator.getUUID());
			m.setCreateDateTime(new Date());
			m.setIsActive(IsActiveEnum.YES.getValue());
			super.save("model.insert", m);
//			this.getSqlMapClientTemplate().insert("model.insert", m);
		} else {
			this.modelModelVOToEntity(modelModelVO, m, true);
			m.setId(modelModelVO.getId());
			m.setUpdateDateTime(new Date());
			super.save("model.update", m);
//			this.getSqlMapClientTemplate().update("model.update", m);
		}
		ret = this.load(m.getId());
		return ret;
	}

	@Override
	public Map search(Map map) {
		return null;
	}

	public void toModelModelVO(ModelModel source, ModelModelVO target) {
		target.setName(source.getName());
		target.setRemark(source.getRemark());
		target.setFilePath(source.getFilePath());
		target.setId(source.getId());
		if (source.getPackageModel() != null) {
			PackageModelVO p = new PackageModelVO();
			p.setId(source.getPackageModel().getId());
			target.setPackageModelVO(p);
		}
	}

	public void modelModelVOToEntity(ModelModelVO source, ModelModel target, boolean copyIfNull) {
		if (copyIfNull || source.getName() != null) {
			target.setName(source.getName());
		}
		if (copyIfNull || source.getRemark() != null) {
			target.setRemark(source.getRemark());
		}
		if (copyIfNull || source.getFilePath() != null) {
			target.setFilePath(source.getFilePath());
		}
		if (copyIfNull || source.getPackageModelVO() != null) {
			PackageModel p = new PackageModel();
			p.setId(source.getPackageModelVO().getId());
			target.setPackageModel(p);
		}
	}

	/* (non-Javadoc)
	 * <p>Title:saveCloudImageSoftWareRef</p>
	 * <p>Description:</p>
	 * @param imageSoftWareRef
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.IModelDao#saveCloudImageSoftWareRef(com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef)
	 */
	@Override
	public void saveCloudImageSoftWareRef(CloudImageSoftWareRef imageSoftWareRef)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		
	}
//检查模板下面有没有脚本
	@Override
	public List<ScriptModelVO> findByModelId(String id)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<ScriptModelVO> list =super.findByID("model.id", id);
		return list;
	}

}
