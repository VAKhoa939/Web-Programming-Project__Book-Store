FROM tomcat:10.1.13-jdk17-temurin-jammy
RUN rm -rf /usr/local/tomcat/webapps/BookStore
RUN rm -rf /usr/local/tomcat/webapps/examples
ADD BookStore.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]