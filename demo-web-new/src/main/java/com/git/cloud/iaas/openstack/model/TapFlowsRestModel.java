package com.git.cloud.iaas.openstack.model;

public class TapFlowsRestModel extends TapFlowsPo{

    private String sourceDeviceName;

    private String tapServiceId;

    private String tenantId;

    private String name;

    public String getSourceDeviceName() {
        return sourceDeviceName;
    }

    public void setSourceDeviceName(String sourceDeviceName) {
        this.sourceDeviceName = sourceDeviceName;
    }

    public String getTapServiceId() {
        return tapServiceId;
    }

    public void setTapServiceId(String tapServiceId) {
        this.tapServiceId = tapServiceId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
