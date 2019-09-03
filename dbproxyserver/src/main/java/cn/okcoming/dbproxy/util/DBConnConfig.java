package cn.okcoming.dbproxy.util;

import lombok.Data;

@Data
public class DBConnConfig {
    private String database;
    private String username;
    private String password;
    private String url;
    private String driverClass;
}
