package cn.okcoming.dbproxy.client;

import cn.okcoming.dbproxy.core.DBRow;
import cn.okcoming.dbproxy.core.DBRowMapper;
import rx.Observable;

import java.util.List;
import java.util.Map;

public interface JdbcOperationClient {

//    public <T> Observable<List<T>> query(final String sql, final DBRowMapper<T> rowMapper) ;
//    public <T> Observable<List<T>> query(final String sql, final DBRowMapper<T> rowMapper, Object... args) ;
//
//    public <T> Observable<T> queryForObject(final String sql, final DBRowMapper<T> rowMapper);
//    public <T> Observable<T> queryForObject(final String sql, final DBRowMapper<T> rowMapper, Object... args);

//    public <T> Observable<T> queryForObject(final String sql, final Class<T> requiredType, Object... args);

//    public <T> Observable<List<T>> queryForList(final String sql, final Class<T> requiredType, Object... args);



//    public Observable<DBRow> queryForRowSet(final String sql, Object... args);
//
//    public Observable<Integer> update(final String sql);
//
//    public <T> Observable<T> updateAndNeedReturnId(final String sql, Object... args) ;
//
//    public Observable<Integer> update(final String sql, Object... args);
//
//    public Observable<int[]> batchUpdate(final String sql, List<Object[]> batchArgs);
//
//    public Observable<int[]> batchUpdate(final String[] sqls);

    <T> T queryForObject(final String sql, final DBRowMapper<T> rowMapper);
    <T> T queryForObject(final String sql, final DBRowMapper<T> rowMapper, Object... args);
    <T> T queryForObject(final String sql, final Class<T> requiredType, Object... args);
    <T> List<T> query(final String sql, final DBRowMapper<T> rowMapper) ;
    <T> List<T> query(final String sql, final DBRowMapper<T> rowMapper, Object... args) ;
    <T> List<T> queryForList(final String sql, final Class<T> requiredType, Object... args);
}
