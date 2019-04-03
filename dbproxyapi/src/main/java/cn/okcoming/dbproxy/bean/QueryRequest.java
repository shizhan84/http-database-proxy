/**
 * 
 */
package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


//@Path("/s2s_dbproxy/query")
@Data
@ApiModel(value = "查询操作")
public class QueryRequest extends DefaultBaseRequest {

    @ApiModelProperty(value = "parameters")
    private Object[] parameters;

}
