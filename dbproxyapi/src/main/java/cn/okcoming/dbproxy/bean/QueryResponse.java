package cn.okcoming.dbproxy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryResponse extends BaseResponse {


    //Retrieve the value of the indicated column in the current row
    @JsonProperty(value = "rows")
    @ApiModelProperty(value = "rows")
    private List<List<Object>> rowList ;
    //ColumnName
    @JsonProperty(value = "cols")
    @ApiModelProperty(value = "ColumnName")
    private List<String> colList ;
    //ColumnType
    @JsonProperty(value = "types")
    @ApiModelProperty(value = "ColumnType")
    private List<Integer> typeList ;


}