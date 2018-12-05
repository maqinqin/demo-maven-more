package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwCclassFLPo.java 
 * @Package 	com.git.cloud.resmgt.network.model.po
 * @author 		syp
 * @date 		2014-9-15下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwCclassFLPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String cclassId;
	private Long seqStart;
	private Long seqEnd;
	private Long availCnt;
	private int totalNum;
//	private String updateUser;
//	private Timestamp updateTime;



	// Constructors

	/** default constructor */
	public RmNwCclassFLPo() {
	}

	/** full constructor */

	public RmNwCclassFLPo(String cclassId, Long seqStart, Long seqEnd,
			Long availCnt
//			, String updateUser,Timestamp updateTime
			) {
		super();
		this.cclassId = cclassId;
		this.seqStart = seqStart;
		this.seqEnd = seqEnd;
		this.availCnt = availCnt;
//		this.updateUser = updateUser;
//		this.updateTime = updateTime;
	}

	// Property accessors

	public String getCclassId() {
		return cclassId;
	}

	public void setCclassId(String cclassId) {
		this.cclassId = cclassId;
	}

	public Long getSeqStart() {
		return seqStart;
	}

	public void setSeqStart(Long seqStart) {
		this.seqStart = seqStart;
	}

	public Long getSeqEnd() {
		return seqEnd;
	}

	public void setSeqEnd(Long seqEnd) {
		this.seqEnd = seqEnd;
	}

	public Long getAvailCnt() {
		return availCnt;
	}

	public void setAvailCnt(Long availCnt) {
		this.availCnt = availCnt;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
