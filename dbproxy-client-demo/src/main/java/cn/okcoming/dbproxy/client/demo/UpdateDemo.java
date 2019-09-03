package cn.okcoming.dbproxy.client.demo;

import cn.okcoming.dbproxy.client.JdbcOperationClient;
import cn.okcoming.dbproxy.client.impl.JdbcOperationClientImpl;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class UpdateDemo {


    private RestTemplate restTemplate = new RestTemplate();

    //替换 jdbcTemplate
    private JdbcOperationClient jdbcTemplate = new JdbcOperationClientImpl(restTemplate,"myproject","db2","http://127.0.0.1:11007/dbproxy");


    @Test
    public void update1() {
        Integer ret = jdbcTemplate.update("update black_overdue_idcard set user_name = 11111 where id = 8769236 ");
        System.out.println(ret);
    }


    @Test
    public void update2() {
        Integer ret = jdbcTemplate.update("update black_overdue_idcard set user_name = 22222 where id = ? ",8769237);
        System.out.println(ret);
    }

    @Test
    public void update3() {
        Integer ret = jdbcTemplate.updateAndNeedReturnId("insert into temp_config(source_schema) values(?) ", 8769238);
        System.out.println(ret);
    }

    @Test
    public void update4() {
        int[] rets = jdbcTemplate.batchUpdate(new String[]{"insert into temp_config(source_schema) values(8769239991)","update black_overdue_idcard set user_name = 1111122233 where id = 8769236"});
        System.out.println(rets);
    }
}

