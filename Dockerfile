FROM openjdk:8-jdk-alpine

# 作者信息（可选）
LABEL maintainer="chiiiplow q641484973@gmail.com"

# 在容器中创建目录
WORKDIR /app

# 把 jar 包复制进容器
COPY target/demo-app-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口（比如 Spring Boot 默认 8080）
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
