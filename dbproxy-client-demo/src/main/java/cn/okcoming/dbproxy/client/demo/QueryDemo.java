package cn.okcoming.dbproxy.client.demo;

import cn.okcoming.dbproxy.client.JdbcOperationClient;
import cn.okcoming.dbproxy.client.impl.JdbcOperationClientImpl;
import cn.okcoming.dbproxy.core.DBRow;
import cn.okcoming.dbproxy.core.DBRowMapper;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class QueryDemo {

    private static final DBRowMapper<MessageBean> MESSAGES_ROW_MAPPER =
            new DBRowMapper<MessageBean>() {
                @Override
                public MessageBean mapRow(final DBRow rs){
                    final MessageBean record = new MessageBean();
                    record.setId(rs.getLong("id"));
                    record.setUsername(rs.getString("user_name"));
                    record.setLostContact(rs.getDate("lost_contact_date"));
                    record.setGmtCreated(rs.getTimestamp("gmt_created"));
                    return record;
                }};


    private RestTemplate restTemplate = new RestTemplate();

    //替换 jdbcTemplate
    private JdbcOperationClient jdbcTemplate = new JdbcOperationClientImpl(restTemplate,"myproject","db2","http://127.0.0.1:11007/dbproxy");


    @Test
    public void findMessages1() {
        List<MessageBean> findMessages = jdbcTemplate.query("select id,user_name,lost_contact_date,gmt_created from black_overdue_idcard where lost_contact_date is not null limit  ? ", MESSAGES_ROW_MAPPER, 10);
        System.out.println(findMessages);
    }


    @Test
    public void findMessages2() {
        List<String> findMessages = jdbcTemplate.queryForList("select user_name from black_court_zxgk limit ? ", String.class,10);
        System.out.println(findMessages);
    }

    @Test
    public void findMessages3() {
        String findMessages = jdbcTemplate.queryForObject("select user_name from black_court_zxgk limit 1 ", String.class);
        System.out.println(findMessages);
    }

    @Test
    public void findMessages4() {
        MessageBean findMessages = jdbcTemplate.queryForObject("select id,user_name,lost_contact_date,gmt_created from black_overdue_idcard where lost_contact_date is not null limit 1 ", MESSAGES_ROW_MAPPER);
        System.out.println(findMessages);
    }
}

