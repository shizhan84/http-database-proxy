package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class DefaultBaseRequest extends BaseRequest{

    @ApiModelProperty(value = "query")
    protected String query;

    @ApiModelProperty(value = "parameters")
    private Object[] parameters;
}
