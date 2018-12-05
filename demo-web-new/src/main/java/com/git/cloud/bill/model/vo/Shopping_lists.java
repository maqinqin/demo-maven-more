
package com.git.cloud.bill.model.vo;

/**
 * 购买信息
 */
public class Shopping_lists {

    private Parameters parameters;
    private String service_id;
    private String instance_id;
    public void setParameters(Parameters parameters) {
         this.parameters = parameters;
     }
     public Parameters getParameters() {
         return parameters;
     }

    public void setService_id(String service_id) {
         this.service_id = service_id;
     }
     public String getService_id() {
         return service_id;
     }

    public void setInstance_id(String instance_id) {
         this.instance_id = instance_id;
     }
     public String getInstance_id() {
         return instance_id;
     }

}