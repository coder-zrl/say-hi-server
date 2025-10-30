# Say Hi Server 项目概述文档

## 1. 项目简介

Say Hi Server 是一个基于Spring Boot的即时通讯系统后端服务，采用微服务架构设计，包含多个功能模块，主要提供用户管理、聊天、消息处理等功能。

## 2. 技术栈

- Java 21
- Spring Boot 3.3.2
- Maven 项目管理
- gRPC 微服务通信
- Protocol Buffers 数据序列化
- WebSocket 实时通信
- Redis 缓存
- MySQL 数据库
- MyBatis-Plus ORM框架
- Sa-Token 权限认证
- Knife4j API文档工具

## 3. 项目结构

整体项目结构：

```
say-hi-server/
├── say-hi-application      # 应用启动模块
├── say-hi-common           # 公共模块，包含通用配置和工具类
├── say-hi-gateway          # 网关模块，对外提供HTTP API接口、Websocket服务
├── say-hi-im-chat          # 会话服务模块
├── say-hi-im-group         # 群组服务模块
├── say-hi-im-message       # 消息服务模块
├── say-hi-interaction      # 互动服务模块
├── say-hi-sdk              # SDK模块，包含Protocol Buffers定义
├── say-hi-user             # 用户服务模块
```

单个项目结构：

```
.
└── src
    └── main
        ├── java
        │   └── com.bird....im.message
        │        ├── controller  # Controller层，但基本不需要实现，仅测试使用
        │        ├── entity      # 与DB交互的实体层
        │        ├── mapper      # mapper层，直接操作DB
        │        ├── rpc         # 实现SDK中的service定义，对外暴露rpc接口，通过service层实现具体逻辑
        │        └── service     # 通过组装mapper调用，来实现具体的业务逻辑
        └── resources
            └── mapper            # xml格式的mapper具体实现
```



## 4. 核心模块详解

### 4.1 say-hi-sdk (协议定义模块)

该模块是整个系统的核心协议模块，定义了所有微服务之间的通信协议。通过Protocol Buffers定义了各种业务实体和服务接口，包括消息、聊天、用户等相关功能。这些协议定义会被编译成Java类，供各个微服务使用。

主要包含以下几类协议：

- 消息相关协议：定义消息实体及操作接口
- 聊天相关协议：定义聊天信息实体及操作接口
- 用户相关协议：定义用户信息实体及操作接口
- 长连接协议：定义WebSocket长连接通信的数据结构

### 4.2 say-hi-gateway (网关模块)

作为系统的唯一入口，负责对外提供HTTP RESTful API接口，并处理WebSocket长连接。主要功能包括：

1. 接收客户端的HTTP请求，将其转换为内部gRPC调用
2. 维护WebSocket长连接，实现实时消息推送
3. 用户认证和权限验证(Sa-Token)
4. 参数校验和结果封装
5. API文档生成(Knife4j)

该模块目前实现了基本的消息、聊天、用户相关的RESTful接口，但数据仍为模拟数据，需要进一步对接真实的微服务。

### 4.3 say-hi-common (公共模块)

包含各微服务共享的基础组件和工具类，例如：

- gRPC客户端模板，简化微服务间调用
- Redis配置和工具类
- MyBatis-Plus数据库访问配置
- 通用常量和工具函数

### 4.4 微服务模块

以下模块代表系统中的独立业务服务，每个服务专注于特定领域的功能：

- say-hi-im-message: 消息处理服务，负责消息的存储、查询等
- say-hi-im-chat: 会话服务，管理聊天会话相关信息
- say-hi-im-group: 群组服务，处理群聊相关功能
- say-hi-user: 用户服务，管理用户信息和认证
- say-hi-interaction: 互动服务，处理用户间互动相关功能
- say-hi-application: 应用启动整合模块，协调各服务启动

## 5. 通信方式

### 5.1 内部通信

- 微服务间通过gRPC进行通信
- 使用Protocol Buffers进行数据序列化

### 5.2 外部通信

- HTTP RESTful API接口
- WebSocket长连接实现实时通信

## 6. 部署架构设想

```
客户端 <-> Gateway网关 <-> 各个微服务(Nacos注册中心) <-> 数据存储(Redis/MySQL)
```

## 7. 开发注意事项

每次开发时都要参考@/docs/开发规范.md文件内的具体要求