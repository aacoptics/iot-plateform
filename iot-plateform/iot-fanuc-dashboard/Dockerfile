FROM java:8
#作者
MAINTAINER yan shangqi
#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp
#复制上下文目录下的target/demo-1.0.0.jar 到容器里
COPY target/iot-fanuc-dashboard-1.0-SNAPSHOT.jar iot-fanuc-dashboard-1.0-SNAPSHOT.jar

#bash方式执行，使demo-0.0.1jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
RUN bash -c "touch /iot-fanuc-dashboard-1.0-SNAPSHOT.jar"

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+08","iot-fanuc-dashboard-1.0-SNAPSHOT.jar"]