#!/bin/bash

# 自动构建脚本
PROJECT_NAME="say-hi-server"
VERSION="0.0.1-SNAPSHOT"
RUNNING_PORT=10086

# 1. Maven 打包
mvn clean package -DskipTests

# 2. 获取生成的JAR文件名
JAR_FILE=$(ls target/*.jar | head -n 1)
JAR_NAME=$(basename $JAR_FILE)
echo "📦 打包完成! 使用的JAR文件: $JAR_FILE，JAR_NAME:$JAR_NAME"

# 3. Docker构建（传递JAR文件名作为参数）
echo "🏗️ 开始构建Docker镜像: $PROJECT_NAME:$VERSION"
docker build \
  --build-arg JAR_NAME=$JAR_NAME \
  --build-arg RUNNING_PORT=$RUNNING_PORT \
  --build-arg JVM_OPTS="-Xmx128m" \
  -t "$PROJECT_NAME:$VERSION" \
  .

# 4. 条件部署
if [ "$1" == "run" ] || [ "$DEPLOY" == "true" ]; then
    echo "🚀 启动容器..."

    # 停止并删除旧容器（如果存在）
    docker stop ${PROJECT_NAME}-container 2>/dev/null
    docker rm ${PROJECT_NAME}-container 2>/dev/null

    # 启动新容器
    docker run -d \
        -p $RUNNING_PORT:$RUNNING_PORT \
        --name ${PROJECT_NAME}-container \
        $PROJECT_NAME:$VERSION

    # 检查容器状态
    sleep 2  # 等待容器启动
    if docker ps | grep -q ${PROJECT_NAME}-container; then
        echo "✅ 部署完成!"
        echo "  使用的JAR文件: $JAR_NAME"
        echo "  部署端口: $RUNNING_PORT"
        echo "  容器名称: ${PROJECT_NAME}-container"
    else
        echo "⚠️ 容器启动失败，请检查日志: docker logs ${PROJECT_NAME}-container"
        exit 1
    fi
else
    echo "ℹ️ 构建完成但未部署容器。要部署请运行:sh $0 run"
    echo "或设置环境变量: DEPLOY=true，然后重新运行:sh $0"
fi