/**
 * 
 */
package cn.okcoming.dbproxy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 每一个query语言都是一个完整的整体 不能传递parameters参数了
 */
//@Path("/s2s_dbproxy/batchUpdate")
@Data
@ApiModel(value = "批量更新")
public class BatchUpdateRequest extends BaseRequest {

    @ApiModelProperty(value = "相互独立的sql更新语句")
    private String[] querys;
    
}
