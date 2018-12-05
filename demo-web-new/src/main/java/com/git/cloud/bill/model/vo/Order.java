
package com.git.cloud.bill.model.vo;
import java.util.Date;
import java.util.List;

/**
 * 计费订单
 */
public class Order {

    private String create_time;
    private String id;
    private String order_type;
    private String user_id;
    private String tenant_id;
    private List<Shopping_lists> shopping_lists;
    

    public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setOrder_type(String order_type) {
         this.order_type = order_type;
     }
     public String getOrder_type() {
         return order_type;
     }

    public void setUser_id(String user_id) {
         this.user_id = user_id;
     }
     public String getUser_id() {
         return user_id;
     }

    public void setTenant_id(String tenant_id) {
         this.tenant_id = tenant_id;
     }
     public String getTenant_id() {
         return tenant_id;
     }

    public void setShopping_lists(List<Shopping_lists> shopping_lists) {
         this.shopping_lists = shopping_lists;
     }
     public List<Shopping_lists> getShopping_lists() {
         return shopping_lists;
     }

}