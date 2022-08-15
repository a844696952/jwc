### 一.项目结构
- yep (yice edu platform)
  - common (通用模块,pojo,util,sdk,sequenceId等)
  - config-server (配置中心)
  - eureka (注册中心)
  - gateway (网关)
  - monitors (监控模块,地下包含所有监控项目)
     - boot-admin (监控项目的端点,线程,url等)
     - zipkin (待办)
     - hystrix-turbine (待办)
  - modules (业务项目模块,地下包含所有业务项目)
     - yed (yice edu admin,亿策管理后台)
     - user (用户模块)


### 二.启动
赶项目,暂时不用配置中心,
启动redis,mysql5.7,启动eureka,user,yed,gateway
e
