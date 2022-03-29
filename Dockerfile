FROM adoptopenjdk/openjdk8
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD ./target/codebase-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java","-jar","-Xmx1024m","-Xms256m","/app.jar"]
