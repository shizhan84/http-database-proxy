package cn.okcoming.dbproxy.bean;

import cn.okcoming.dbproxy.util.Constants;
import lombok.Data;


@Data
public class BaseResponse {

    private String code = Constants.SUCCESS;
    private String message = "执行成功";

}


