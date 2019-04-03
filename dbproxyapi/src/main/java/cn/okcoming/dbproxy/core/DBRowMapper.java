package cn.okcoming.dbproxy.core;

/**
 * Created by bluces on 2016/12/28.
 */
public interface DBRowMapper<T> {

    T mapRow(final DBRow row);

}

