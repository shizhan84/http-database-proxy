package cn.okcoming.dbproxy.dao.impl;

import cn.okcoming.dbproxy.dao.OperatorDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import cn.okcoming.dbproxy.util.DBUtils;

@Slf4j
@Component
public class OperatorDaoImpl implements OperatorDao {

    @Autowired
    private JdbcOperations jdbcOperations;//默认的，也即内嵌的h2database

    private JdbcOperations jdbcTemplate(final String database){
        if(database == null || Objects.equals(database,"default")) {
            return jdbcOperations;
        }else{
            return DBUtils.jdbcOperations(database);
        }
    }
    @Override
    public SqlRowSet query(final String database,String query, Object[] parameters){
        return jdbcTemplate(database).queryForRowSet(query,parameters);
    }

    @Override
    public int update(final String database,final String query, final Object[] parameters) {
        return jdbcTemplate(database).update(query, parameters);
    }

    @Override
    public Object updateAndReturnId(final String database,final String query, final Object[] parameters) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate(database).update(new PreparedStatementCreator() {
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
    public int[] batchUpdate(final String database,String query, List<Object[]> parameters) {
        return jdbcTemplate(database).batchUpdate(query,parameters);
    }

    @Override
    public int[] batchUpdate(final String database,String[] querys) {
        return jdbcTemplate(database).batchUpdate(querys);
    }

    @Override
    public void execute(final String database,final String query) {
        jdbcTemplate(database).execute(query);
    }

    public Object lastInsertID(final String database) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate(database).update(new PreparedStatementCreator(){
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
