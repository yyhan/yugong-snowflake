# yugong-snowflake
基于 snowflake 算法的全局 id 生成服务

# 依赖
+ spring
+ springboot
+ apache dubbo
+ apache curator
+ slf4j
+ log4j2

# 开发
## 模块划分

| 模块 | 说明 |
| ------ | ------ |
| yugong-snowflake-core | 核心模块。雪花算法实现、zookeeper自动分配机器Id实现 |
| yugong-snowflake-rpc-api | rpc 服务接口定义模块 |
| yugong-snowflake-rpc-server | 基于 dubbo 的 rpc 服务实现模块。提供开箱即用的 rpc 服务 |
| yugong-snowflake-spring-boot-starter | springboot starter 模块。 |

## 编译打包
```shell script
mvn clean package install
```

# License
Copyright (c) 小天. All rights reserved.

Licensed under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html) license.
