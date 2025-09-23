FROM maven:3.6.3-jdk-8 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine

# 作者信息（可选）
LABEL maintainer="chiiiplow q641484973@gmail.com"

ADD https://arthas.aliyun.com/arthas-boot.jar /app/arthas-boot.jar

# 把 jar 包复制进容器
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口（比如 Spring Boot 默认 8080）
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
