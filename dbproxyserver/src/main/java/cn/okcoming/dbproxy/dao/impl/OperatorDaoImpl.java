package cn.okcoming.dbproxy.dao.impl;

import cn.okcoming.baseutils.ExceptionUtils;
import cn.okcoming.dbproxy.bean.QueryResponse;
import cn.okcoming.dbproxy.dao.OperatorDao;
import cn.okcoming.dbproxy.util.MethodUtils;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OperatorDaoImpl implements OperatorDao {

    @Autowired
    private JdbcTemplate _jdbcTemplate;

    @Override
    public SqlRowSet query(String query, Object[] parameters){
        return _jdbcTemplate.queryForRowSet(query,parameters);
    }

    @Override
    public int update(final String query, final Object[] parameters) {
        return _jdbcTemplate.update(query, parameters);
    }

    @Override
    public Object updateAndReturnId(final String query, final Object[] parameters) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        this._jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                new ArgumentPreparedStatementSetter(parameters).setValues(ps);
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey();
    }

    @Override
    public int[] batchUpdate(String query, List<Object[]> parameters) {
        return _jdbcTemplate.batchUpdate(query,parameters);
    }

    @Override
    public int[] batchUpdate(String[] querys) {
        return _jdbcTemplate.batchUpdate(querys);
    }

    @Override
    public void execute(String query) {
        _jdbcTemplate.execute(query);
    }

    public Object lastInsertID() {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        _jdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement("SELECT LAST_INSERT_ID()", Statement.RETURN_GENERATED_KEYS);
                new ArgumentPreparedStatementSetter(new Object[]{}).setValues(ps);
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey();
    }

}
