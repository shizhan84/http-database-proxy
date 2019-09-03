package cn.okcoming.dbproxy.util;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Data
@Component
@ConfigurationProperties(prefix = "database.druid")
public class DBUtils implements InitializingBean {
    private List<DBConnConfig> configs;

    @Autowired
    private DataSource dataSource;//默认的，也即内嵌的h2database

    private static Map<String,DatabaseManagerContainer> dataSourceMap = new HashMap<>();

    @Data
    class DatabaseManagerContainer{
        private DataSourceTransactionManager transactionManager;
        private JdbcOperations jdbcOperations;

        public DatabaseManagerContainer(DataSourceTransactionManager transactionManager) {
            this.transactionManager = transactionManager;
            this.jdbcOperations = new JdbcTemplate(transactionManager.getDataSource());
        }
    }
    @Override
    public void afterPropertiesSet(){
        dataSourceMap.put("default",new DatabaseManagerContainer(new DataSourceTransactionManager(dataSource)));

        for(DBConnConfig config: configs){
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource(config));
            dataSourceMap.put(config.getDatabase(),new DatabaseManagerContainer(transactionManager));
        }
    }

    private DataSource dataSource(DBConnConfig config)  {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setUrl(config.getUrl());
        dataSource.setDriverClassName(config.getDriverClass());
        try {
            dataSource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setUseGlobalDataSourceStat(true);

        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql","true");
        properties.setProperty("druid.stat.slowSqlMillis","5000");
        dataSource.setConnectProperties(properties);
        dataSource.setValidationQuery("select 1 from dual");
        dataSource.setValidationQueryTimeout(30);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        return dataSource;
    }


    public static JdbcOperations jdbcTemplate(String database){
        if(StringUtils.isBlank(database)){
            database = "default";
        }
        return dataSourceMap.get(database).getJdbcOperations();
    }

    public static PlatformTransactionManager txManager(String database){
        return dataSourceMap.get(database).getTransactionManager();
    }
}
