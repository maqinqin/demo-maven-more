
package com.git.cloud.bill.model.vo;
import java.util.List;


public class VariableVOs {

    private String detailId;
    private String price;
    private String priceType;
    private String comments;
    private String groupId;
    private int index;
    private String policyType;	//定价类型
    private String factorVoInfo;//元素类型
    public void setDetailId(String detailId) {
         this.detailId = detailId;
     }
     public String getDetailId() {
         return detailId;
     }

    public void setPrice(String price) {
         this.price = price;
     }
     public String getPrice() {
         return price;
     }

    public void setPriceType(String priceType) {
         this.priceType = priceType;
     }
     public String getPriceType() {
         return priceType;
     }

    public void setComments(String comments) {
         this.comments = comments;
     }
     public String getComments() {
         return comments;
     }

    public void setGroupId(String groupId) {
         this.groupId = groupId;
     }
     public String getGroupId() {
         return groupId;
     }

    public void setIndex(int index) {
         this.index = index;
     }
     public int getIndex() {
         return index;
     }

	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getFactorVoInfo() {
		return factorVoInfo;
	}
	public void setFactorVoInfo(String factorVoInfo) {
		this.factorVoInfo = factorVoInfo;
	}

}