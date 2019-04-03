/**
 * 
 */
package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author isdom
 *
 */
//@Path("/s2s_dbproxy/update")
@Data
public class UpdateRequest extends DefaultBaseRequest {

    @ApiModelProperty(value = "parameters")
    private Object[] parameters;

    @ApiModelProperty(value = "是否返回最新插入记录的ID")
    private boolean needReturnId;
    
}
