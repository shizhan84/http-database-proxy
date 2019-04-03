package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class BaseResponse {

    @ApiModelProperty(value = "code")
    protected String code = "0";
    @ApiModelProperty(value = "message")
    protected String message = "执行成功";

}

