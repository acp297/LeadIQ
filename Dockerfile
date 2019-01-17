FROM tomcat:8.5.16-jre8-alpine

EXPOSE 8080

RUN rm -rf /usr/local/tomcat/webapps/*

ADD ./target/img-upload-app.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]