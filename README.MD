## Say-Hi-Server

say-hi 即时通讯应用的后端服务： IM、Link等后端服务

鉴于开发成本，本项目代码结构暂时为单体形式。但在编码时会提前按照分 module 的形式编码，便于后续进行服务拆分。

websocket 连接：ws://localhost:10086/websocket/{uid}

websocket 包结构：

```json
{
  "requestClientInfo": {
    "userId": "user123",
    "appVersion": "1.0.0"
  },
  "command":"Message.Send",
  "data": "{\"content\":\"test\"}"
}
```

打包 Docker 镜像命令：

```shell
# 仅打包
sh ./build.sh

# 打包并启动 container
sh ./build.sh run
```

API 接口文档

```shell
http://localhost:10086/doc.html
```

Sa-Token

官方文档：https://sa-token.cc/doc.html#/

登录接口会让浏览器自动塞cookie

```shell
Response Header

set-cookie
satoken=28956fd0-9257-473c-a1af-98a37d163e56; Max-Age=2592000; Expires=Mon, 27 Oct 2025 22:51:16 +0800; Path=/
```

后序前端请求，带上对应cookie即可