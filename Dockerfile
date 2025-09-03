FROM eclipse-temurin:21-jdk-alpine

# 接收构建参数（来自脚本的JAR_FILE_NAME）
ARG JAR_NAME
ENV JAR_NAME=$JAR_NAME

ARG RUNNING_PORT
ENV RUNNING_PORT=$RUNNING_PORT

# 设置工作目录
WORKDIR /app

# 从构建阶段复制JAR文件（使用环境变量）
COPY /target/$JAR_NAME .

# 设置时区（解决容器内时间问题）
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone


# 暴露端口
EXPOSE $RUNNING_PORT

# 启动应用（使用环境变量），或者写成 ENTRYPOINT java -jar $APP_JAR
ENTRYPOINT ["sh", "-c", "java -jar $JAR_NAME"]