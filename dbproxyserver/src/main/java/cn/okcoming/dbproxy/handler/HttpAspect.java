package cn.okcoming.dbproxy.handler;

import cn.okcoming.baseutils.ExceptionUtils;
import cn.okcoming.dbproxy.bean.BaseResponse;
import cn.okcoming.dbproxy.util.MethodUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class HttpAspect {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse defaultErrorHandle(HttpServletRequest request, Exception e){
        log.error("【异常拦截】 {}", ExceptionUtils.exception2detail(e));
        BaseResponse response = new BaseResponse();
        response.setCode("2000");
        response.setMessage(ExceptionUtils.exception2detail(e));

        return response;
    }
}
