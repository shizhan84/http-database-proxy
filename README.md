#http-database-proxy

该项目可以全局监控数据库访问性能，可以通过druid或者自定义jmx查看接口的响应情况，可以配置访问多个数据库，可以作为访问内部数据库的一个公网代理服务

### 主要的开发工作量是持续维护一个好用的api，让客户端能像使用spring jdbcTemplate那样使用jdbcOperationClient

### 目前的问题：
- 不支持事务处理
- 请求sql没有安全授权

## 使用步骤

- 1 配置合适的数据库连接情况(application-prod.yml),启动数据库端的dbproxy-server进程(./appctrl.sh start dbproxy-server.jar)
  ，提供http协议的数据库查询代理服务(可通过swagger-ui查看接口情况)

- 2 把dbproxy-api依赖包放到目标工程中，移除和数据库交互相关的jdbc驱动jar包，使用jdbcOperationClient代替jdbcTemplate类，
参考dbproxy-client-demo目录的下使用例子。接口调用方法和jdbcTemplate保持基本一致
