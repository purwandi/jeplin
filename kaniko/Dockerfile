FROM golang:1.16-alpine as builder

WORKDIR /workspace
ADD . /workspace

RUN go mod download
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -ldflags '-extldflags "-static"' -o /workspace/helper .

FROM alpine:3.13
WORKDIR /workspace

RUN echo http://dl-4.alpinelinux.org/alpine/v3.13/main > /etc/apk/repositories && \
    echo http://dl-4.alpinelinux.org/alpine/v3.13/main >> /etc/apk/repositories

RUN apk add --no-cache ca-certificates curl gettext openssl

COPY --from=builder                        /workspace/helper   /kaniko/helper
# ADD helper /kaniko/helper
COPY --from=gcr.io/kaniko-project/executor:debug /kaniko/executor    /kaniko/executor
COPY --from=gcr.io/kaniko-project/executor:debug /kaniko/.docker     /kaniko/.docker
COPY --from=gcr.io/kaniko-project/executor:debug /kaniko/ssl         /kaniko/ssl
COPY --from=gcr.io/kaniko-project/executor:debug /etc/nsswitch.conf  /etc/nsswitch.conf

ENV HOME=/root
ENV PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/kaniko
ENV SSL_CERT_DIR /kaniko/ssl/certs
ENV DOCKER_CONFIG /kaniko/.docker/

RUN update-ca-certificates \
  && cp /etc/ssl/certs/ca-certificates.crt /kaniko/ssl/certs/