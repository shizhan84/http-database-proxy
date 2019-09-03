package cn.okcoming.dbproxy.controller;

import cn.okcoming.dbproxy.bean.*;
import cn.okcoming.dbproxy.dao.OperatorDao;
import cn.okcoming.dbproxy.util.MethodUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author bluces.wang
 */
@Slf4j
@Controller
@Api(tags = "Data",description="数据库执行的http代理接口管理，目前只加了mysql驱动")
@RequestMapping(value="/")
public class DataController {

    @Autowired
    private OperatorDao _operatorDao;

    @PostMapping(value = "/query", produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value ="查询", notes= ""  )
    @ResponseBody
    public QueryResponse query(@RequestBody QueryRequest _request){
        QueryResponse _response = new QueryResponse();
        MethodUtils.transform(_response,_operatorDao.query(_request.getDatabase(),_request.getQuery(),_request.getParameters()));

        return _response;
    }

    @PostMapping(value = "/update", produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value ="更新", notes= ""  )
    @ResponseBody
    public UpdateResponse query(@ApiParam(name="keywords",value = "参数") @RequestBody UpdateRequest _request){
        UpdateResponse _response = new UpdateResponse();
        if(_request.isNeedReturnId()){
            _response.setReturnId(_operatorDao.updateAndReturnId(_request.getDatabase(),_request.getQuery(),_request.getParameters()));
        }else{
            _response.setRowCount(_operatorDao.update(_request.getDatabase(),_request.getQuery(),_request.getParameters()));
        }

        return _response;
    }

    @PostMapping(value = "/batchUpdate", produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value ="批量更新", notes= ""  )
    @ResponseBody
    public BatchUpdateResponse batchUpdate(@RequestBody BatchUpdateRequest _request){
        BatchUpdateResponse _response = new BatchUpdateResponse();
        _response.setRowCount(_operatorDao.batchUpdate(_request.getDatabase(),_request.getQuerys()));

        return _response;
    }


    @PostMapping(value = "/execute", produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value ="DDL操作 ", notes= "正常的业务是用不到的 "  )
    @ResponseBody
    public BaseResponse execute(@RequestBody ExecuteRequest _request){
        BaseResponse _response = new BaseResponse();
        _operatorDao.execute(_request.getDatabase(),_request.getExecute());

        return _response;
    }
}
