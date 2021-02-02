FROM alpine:3.7

RUN mkdir /semagrow
RUN mkdir /semagrow/output

COPY init.sh /semagrow/init.sh

ENTRYPOINT ["/bin/sh", "/semagrow/init.sh"]
