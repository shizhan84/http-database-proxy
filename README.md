#http-database-proxy

第一版本问题：
- 不支持事务处理
- 请求sql没有安全授权

## 使用步骤

### 1 部署接入数据库一端的dbproxy-server，提供对外的http数据库查询代理服务

### 2 把dbproxy-api依赖包放到开发中的工程中，移除和数据库交互相关的所有jar包，使用jdbcOperationClient代替jdbcTemplate类，可参考test目录的下使用例子。接口调用方法基本一致
