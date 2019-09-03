package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
abstract class BaseRequest {

    @ApiModelProperty(value = "项目名称 只做记录")
    protected String project;
    @ApiModelProperty(value = "数据库名 默认使用测试内存数据库")
    protected String database = "default";

}
