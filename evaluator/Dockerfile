FROM maven:3-jdk-11

MAINTAINER Yiannis Mouchakis <gmouchakis@iit.demokritos.gr>

COPY ./docker/run.sh .
COPY . /kobe
RUN chmod -v +x run.sh

RUN cd kobe && \
    mvn clean package && \
    mvn dependency:copy-dependencies

CMD /run.sh
