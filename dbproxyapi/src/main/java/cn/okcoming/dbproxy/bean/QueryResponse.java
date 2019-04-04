package cn.okcoming.dbproxy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryResponse extends BaseResponse {

    private List<List<Object>> rowList ;

    private List<String> colList ;

    private List<Integer> typeList ;


}