FROM maven:3.8.1-openjdk-8 AS builder

COPY settings.xml /usr/share/maven/conf/settings.xml

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn clean package -DskipTests


FROM openjdk:8-jdk

LABEL maintainer="chiiiplow q641484973@gmail.com"

# 设置 JAVA_HOME 指向 JDK，而不是 jre
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

# 下载 arthas
ADD https://arthas.aliyun.com/arthas-boot.jar /app/arthas-boot.jar

# 拷贝应用
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
