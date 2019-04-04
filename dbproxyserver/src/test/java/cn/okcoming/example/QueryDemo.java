package cn.okcoming.example;

import cn.okcoming.dbproxy.client.JdbcOperationClient;
import cn.okcoming.dbproxy.client.impl.JdbcOperationClientImpl;
import cn.okcoming.dbproxy.core.DBRow;
import cn.okcoming.dbproxy.core.DBRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
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
    private JdbcOperationClient _jdbcOperationClient;

    @Before
    public void init(){
        _jdbcOperationClient = new JdbcOperationClientImpl();
        ((JdbcOperationClientImpl) _jdbcOperationClient).setRestTemplate(restTemplate);
    }

    @Test
    public void findMessages1() {
        List<MessageBean> findMessages = _jdbcOperationClient.query("select id,user_name,lost_contact_date,gmt_created from black_overdue_idcard where lost_contact_date is not null limit  ? ", MESSAGES_ROW_MAPPER, 10);
        System.out.println(findMessages);
    }


    @Test
    public void findMessages2() {
        List<String> findMessages = _jdbcOperationClient.queryForList("select user_name from black_court_zxgk limit ? ", String.class,10);
        System.out.println(findMessages);
    }

}

