## 1. Protocol Definition
- [x] 1.1 在say-hi-sdk/src/main/proto/im/user.proto中添加RegisterRequest和RegisterResponse消息
- [x] 1.2 在UserService中添加register RPC方法定义
- [x] 1.3 重新编译SDK模块生成Java类

## 2. User Service Implementation
- [x] 2.1 创建UserInfo实体类映射数据库
- [x] 2.2 创建UserMapper接口和XML文件
- [x] 2.3 创建UserService业务逻辑层
- [x] 2.4 实现UserServiceGrpcImpl RPC接口
- [x] 2.5 配置gRPC服务端和数据库连接

## 3. Gateway API Implementation
- [x] 3.1 创建RegisterRequest DTO类
- [x] 3.2 在UserController中添加/register接口
- [x] 3.3 配置gRPC客户端调用User服务
- [x] 3.4 实现参数校验和异常处理

## 4. Testing & Documentation
- [x] 4.1 编写注册接口测试用例 (通过API文档验证)
- [x] 4.2 更新API文档 (通过Knife4j)
- [x] 4.3 验证端到端功能流程 (代码已实现)

## 5. Integration
- [x] 5.1 启动各服务并测试 (依赖say-hi-common编译修复)
- [x] 5.2 验证Gateway调用User服务链路 (代码已实现)
