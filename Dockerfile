FROM tomcat:latest
ADD target/userauth-0.0.1.war /usr/local/tomcat/webapps/
EXPOSE 8089
CMD ["catalina.sh", "run"]