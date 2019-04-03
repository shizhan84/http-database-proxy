package cn.okcoming.dbproxy.bean;

import lombok.Data;

@Data
public class BatchUpdateResponse extends BaseResponse {


    //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
    private int[] rowCount ;

}