## Why
当前系统仅有模拟的用户登录功能，缺少真实的用户注册能力。Gateway层提供用户注册HTTP接口，User服务实现具体业务逻辑，实现完整的用户生命周期管理。

## What Changes
- 在SDK的user.proto中添加用户注册相关协议定义（RegisterRequest、RegisterResponse）
- 在UserService中添加register RPC方法
- 在Gateway中创建用户注册HTTP接口（/user/register）
- 在User服务中实现注册业务逻辑，包括参数校验、用户创建、数据存储
- 建立Gateway与User服务的gRPC通信

## Impact
- 新增能力：用户注册功能
- 影响模块：say-hi-sdk、say-hi-user、say-hi-gateway
- 向后兼容：不破坏现有功能

## Dependencies
- 依赖数据库设计（后续任务）
- 需要整合Sa-Token认证框架
- 需要MyBatis-Plus数据访问层
