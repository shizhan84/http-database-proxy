package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UpdateResponse extends BaseResponse {

    //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
    @ApiModelProperty(value = "rowCount")
    private Integer rowCount ;
    @ApiModelProperty(value = "returnId")
    private Object returnId ;
}