package cn.okcoming.dbproxy.client.impl;

import cn.okcoming.dbproxy.bean.*;
import cn.okcoming.dbproxy.client.JdbcOperationClient;
import cn.okcoming.dbproxy.core.DBRow;
import cn.okcoming.dbproxy.core.DBRowMapper;
import cn.okcoming.dbproxy.core.DefaultDBRow;
import cn.okcoming.dbproxy.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Created by bluces on 2016/12/28.
 */
@Slf4j
public class JdbcOperationClientImpl implements JdbcOperationClient {
    private RestTemplate _restTemplate;
    private String _project;
    private String _database;
    private String _baseUrl;

    public JdbcOperationClientImpl(RestTemplate _restTemplate, String _project, String _database, String _baseUrl) {
        this._restTemplate = _restTemplate;
        this._project = _project;
        this._database = _database;
        this._baseUrl = _baseUrl;
    }

    private  <T> T execute(String apiName, String member, Supplier<T> supplier){
        log.info("http {} api: {}",apiName,member);
        try{
            T ret = supplier.get();
            log.debug("{}", ret);
            return ret;
        }catch (Exception e){
            log.error("",e);
        }
        return null;
    }

//    /**分页接口数据全都取下来*/
//    private  <RESP extends AbstractPageResponse<DATA>,DATA> List<DATA> fetchAllPage(String url,Class<RESP> classes,Object... uriVariables){
//        final List<DATA> all = new ArrayList<>();
//        RESP response;
//        int pageIndex = 1;
//        do{
//            List variables = Lists.newArrayList(properties.getThirdAppkey());
//            variables.addAll(Arrays.asList(uriVariables));
//            variables.add(pageIndex++);
//            response = restTemplate.getForObject(url,classes, variables.toArray());
//            //response = restTemplate.getForObject(url,classes,properties.getThirdAppkey(),uriVariables,pageIndex++);
//            log.debug("{}", response);
//            if(response.success()) {
//                all.addAll(response.getResult());
//            }else if(response.notfound()){
//                break;
//            }else{
//                log.error("{}", response);
//                return null;
//            }
//        }while(response.getPaging().getPageSize()* response.getPaging().getPageIndex()< response.getPaging().getTotalRecords());
//        return all;
//    }
//
//    /**取一个对象记录*/
//    private  <RESP extends AbstractBaseResponse<DATA>,DATA> DATA fetchOne(String url, Class<RESP> classes, Object... uriVariables){
//        List variables = Lists.newArrayList(properties.getThirdAppkey());
//        variables.addAll(Arrays.asList(uriVariables));
//        RESP response = restTemplate.getForObject(url,classes, variables.toArray());
//        if(response.success()){
//            return response.getResult();
//        }else {
//            log.error("{}",response);
//            return null;
//        }
//    }
//
//    /**取一列对象记录*/
//    private  <RESP extends AbstractListResponse<DATA>,DATA> List<DATA> fetchList(String url, Class<RESP> classes, Object... uriVariables){
//        List variables = Lists.newArrayList(properties.getThirdAppkey());
//        variables.addAll(Arrays.asList(uriVariables));
//        RESP response = restTemplate.getForObject(url,classes, variables.toArray());
//        if(response.success()){
//            return response.getResult();
//        }else if(response.notfound()){
//            return Collections.EMPTY_LIST;
//        }else {
//            log.error("{}",response);
//            return null;
//        }
//    }

    private DateTimeFormatter DTF =   DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private Object[] convert(Object[] args){
        if(args == null){
            return null;
        }
        for(int i=0;i<args.length;i++){
            if(args[i] instanceof Date){
                args[i] =  new SimpleDateFormat("yyyyMMddHHmmss").format(args[i]);
            }
        }
        return args;
    }

    public void execute(String sql)  {
    }

    @FunctionalInterface
    interface Function3<R,X,Y,Z>{
        R action(X x,Y y,Z z);
    }

    /**
     * 组装需要发送的http request对象
     * @param request
     * @param sql
     * @param args
     * @return
     */
    private HttpEntity<? extends BaseRequest> assembleHttpEntity(BaseRequest request,String sql, Object[] args) {
        request.setProject(_project);
        request.setDatabase(_database);
        if(request instanceof DefaultBaseRequest){
            ((DefaultBaseRequest)request).setParameters(convert(args));
            ((DefaultBaseRequest)request).setQuery(sql);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set(Constants.X_UUID, UUID.randomUUID().toString());
        return new HttpEntity(request,headers);
    }

    /**
     * http远程交互
     * @param url
     * @param httpEntity
     * @param cls
     */
    private <T extends BaseResponse,R> R exchange(Function<T, R> func, String url, HttpEntity httpEntity, Class<T> cls){
        ResponseEntity<T> responseEntity = _restTemplate.exchange(url, HttpMethod.POST,httpEntity,cls);
        if(responseEntity.getBody() != null){
            BaseResponse resp = responseEntity.getBody();
            if (Objects.equals(Constants.SUCCESS, resp.getCode())) {
                return  func.apply((T)resp);
            } else {
                log.error(resp.getMessage());
                throw new RuntimeException(resp.getMessage());
            }
        }else{
            throw new RuntimeException("远程访问出错:"+responseEntity.getStatusCode().toString());
        }
    }

    public <R> R queryTemplate(Function3<R,List<List<Object>>,List<String>,List<Integer>> function,
                             final String sql, Object... args){
        QueryRequest request = new QueryRequest();
        HttpEntity httpEntity = assembleHttpEntity(request,sql, args);

        return exchange(new Function<QueryResponse,R>() {
            @Override
            public R apply(QueryResponse resp) {
                List<List<Object>> rows = resp.getRowList();
                List<String> cols = resp.getColList();
                List<Integer> types = resp.getTypeList();

                return function.action(rows,cols,types);
            }
        },_baseUrl + "/query",httpEntity,QueryResponse.class);

//        ResponseEntity<QueryResponse> responseEntity = _restTemplate.exchange(_baseUrl + "/query", HttpMethod.POST,httpEntity,QueryResponse.class);
//        if(responseEntity.getBody() != null){
//            QueryResponse resp = responseEntity.getBody();
//            log.debug("query {}",resp);
//            if (Objects.equals(Constants.SUCCESS, resp.getCode())) {
//                List<List<Object>> rows = resp.getRowList();
//                List<String> cols = resp.getColList();
//                List<Integer> types = resp.getTypeList();
//
//                return function.action(rows,cols,types);
//            } else {
//                log.error(resp.getMessage());
//                throw new RuntimeException(resp.getMessage());
//            }
//        }else{
//            throw new RuntimeException("远程访问出错:"+responseEntity.getStatusCode().toString());
//        }
    }

    @Override
    public <T> List<T> query(final String sql, final DBRowMapper<T> rowMapper)  {
        return this.query(sql,rowMapper,null);
    }

    @Override
    public <T> List<T> query(final String sql, final DBRowMapper<T> rowMapper,Object... args)  {
        return queryTemplate((rows, cols, types) -> {
            List<T> ret = new ArrayList<>();
            for (List<Object> row : rows) {//行
                T bean = rowMapper.mapRow(new DefaultDBRow(row, cols, types));
                ret.add(bean);
            }
            return ret;
        },sql,args);
    }

    @Override
    public <T> List<T> queryForList(final String sql, final Class<T> requiredType,Object... args)  {
        return queryTemplate((rows, cols, types) -> {
            List<T> ret = new ArrayList<>();
            for (List<Object> row : rows) {//行
                T bean = new DefaultDBRow(row, cols, types).getOnlyObject(requiredType);
                ret.add(bean);
            }
            return ret;
        },sql,args);
    }

    @Override
    public <T> T queryForObject(String sql, DBRowMapper<T> rowMapper)  {
        return this.queryForObject(sql,rowMapper,null);
    }

    @Override
    public <T> T queryForObject(final String sql,final DBRowMapper<T> rowMapper, Object... args)  {
//        QueryRequest request = new QueryRequest();
//        HttpEntity httpEntity = assembleHttpEntity(request,sql, args);
//
//        return exchange(new Function<QueryResponse,T>() {
//            @Override
//            public T apply(QueryResponse resp) {
//                List<List<Object>> rows = resp.getRowList();
//                List<String> cols = resp.getColList();
//                List<Integer> types = resp.getTypeList();
//                if(rows.size()>0){
//                    return rowMapper.mapRow(new DefaultDBRow(rows.get(0), cols, types));
//                }else{
//                    return null;
//                }
//            }
//        },_baseUrl + "/query",httpEntity,QueryResponse.class);

        return queryTemplate((rows, cols, types) -> {
            if(rows.size()>0){
                return rowMapper.mapRow(new DefaultDBRow(rows.get(0), cols, types));
            }else{
                return null;
            }
        },sql,args);

    }

    @Override
    public <T> T queryForObject(final String sql,final Class<T> requiredType, Object... args)  {
        return queryTemplate((rows, cols, types) -> {
            if(rows.size()>0){
                return new DefaultDBRow(rows.get(0),cols,types).getOnlyObject(requiredType);
            }else{
                return null;
            }
        },sql,args);

    }

    @Override
    public DBRow queryForRowSet(String sql, Object... args){
        return queryTemplate((rows, cols, types) -> {
            if(rows.size()>0){
                return  new DefaultDBRow(rows.get(0),cols,types);
            }else{
                return null;
            }
        },sql,args);
    }
    @Override
    public Integer update(String sql)  {
        return this.update(false,sql,null);
    }

    @Override
    public Integer update(String sql, Object... args)  {
        return  this.update(false,sql,args);
    }

    @Override
    public Integer updateAndNeedReturnId(String sql, Object... args)  {
        return  this.update(true,sql,args);
    }

    private Integer update(Boolean needReturnId, String sql, Object... args){
        UpdateRequest request = new UpdateRequest();
        request.setNeedReturnId(needReturnId);
        HttpEntity httpEntity = assembleHttpEntity(request,sql, args);

        return exchange(resp -> {
            if (resp.getReturnId() != null) {
                return (Integer) resp.getReturnId();
            } else {
                return  resp.getRowCount();
            }
        },_baseUrl + "/update",httpEntity,UpdateResponse.class);
    }

    @Override
    public int[] batchUpdate(String[] sqls)  {
        BatchUpdateRequest request = new BatchUpdateRequest();
        request.setQuerys(sqls);
        HttpEntity httpEntity = assembleHttpEntity(request,null, null);

        return exchange(resp -> resp.getRowCount(),_baseUrl + "/batchUpdate",httpEntity,BatchUpdateResponse.class);
    }

}
