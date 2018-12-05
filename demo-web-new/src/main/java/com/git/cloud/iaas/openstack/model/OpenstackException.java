package com.git.cloud.iaas.openstack.model;

import com.git.cloud.iaas.enums.ResultEnum;

/**
 * Openstack异常
 *
 * @author wjx
 * @date 2018/06/13
 */
public class OpenstackException extends TenantException{

    private OpenstackErrorModel error;

    public OpenstackException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public OpenstackException(ResultEnum resultEnum, Throwable cause) {
        super(resultEnum, cause);
    }

    public OpenstackException(ResultEnum resultEnum, Throwable cause, OpenstackErrorModel data) {
        super(resultEnum, cause);
        this.setError(data);
    }

    public OpenstackException(Throwable cause, OpenstackErrorModel data) {
        super(ResultEnum.OPENSTACK_INTERFACE_ERROR, cause);
        this.setError(data);
    }

    public OpenstackException(OpenstackErrorModel data) {
        super(ResultEnum.OPENSTACK_INTERFACE_ERROR, ResultEnum.OPENSTACK_INTERFACE_ERROR.getMessage() + " : " + data.getMessage());
        this.setError(data);
    }

    public OpenstackErrorModel getError() {
        return error;
    }

    public void setError(OpenstackErrorModel error) {
        this.error = error;
    }
}
