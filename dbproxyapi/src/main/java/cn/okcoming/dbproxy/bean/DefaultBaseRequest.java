package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
abstract class DefaultBaseRequest extends BaseRequest{

    @ApiModelProperty(value = "query")
    protected String query;

}
