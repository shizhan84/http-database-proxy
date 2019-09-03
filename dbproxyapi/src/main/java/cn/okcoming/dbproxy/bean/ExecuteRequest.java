package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * DDL语句
 */
@Data
public class ExecuteRequest extends BaseRequest {

    @ApiModelProperty(value = "execute")
    private String execute;
    
}
