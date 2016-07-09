FROM maven:3-jdk-8
VOLUME /tmp
ADD . /
RUN apt-get update
RUN apt-get -y install python-pip
RUN pip install awscli
RUN mvn clean package -Dstart-class=com.devfactory.assignment4.Application
CMD aws s3 cp $CONFIG_URL/application.properties application.properties && java -jar target/1-1.0-SNAPSHOT.jar
