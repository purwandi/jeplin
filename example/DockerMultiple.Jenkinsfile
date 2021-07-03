node {
  // sh 'docker network rm bridge1 || true'
  sh 'docker network create bridge1 || true'
  sh(script: 'docker run --net bridge1 --name nginx -d nginx:alpine', returnStdout: true)
  sh(script: 'docker run --net bridge1 --name httpd -d httpd:2.4-alpine', returnStdout: true)

  docker.image('byrnedo/alpine-curl').inside('--net bridge1 --entrypoint=""') { c->
    stage('test nginx') {
      sh 'curl http://nginx'
    }
    
    stage('test httpd') {
      sh 'curl http://httpd'
    }
  }

  sh 'docker rm nginx httpd --force'
  sh 'docker network rm bridge1 --force'
}

node {
  try {
    sh 'docker network create bridge2 || true'
    sh(script: 'docker run --net bridge2 --privileged --name docker -v /certs:/certs -e DOCKER_TLS_CERTDIR=/certs -d docker:dind', returnStdout: true)
    
    docker.image('docker:latest').inside('--net bridge2 --entrypoint="" -v /certs:/certs  -e DOCKER_CERT_PATH=/certs/client -e DOCKER_TLS_VERIFY=1 -e DOCKER_DRIVER=overlay2 -e DOCKER_HOST=tcp://docker:2376') { c->
      sh 'env'
      sh 'docker info'
    }
  } finally {
    sh 'docker rm docker --force'
    sh 'docker network rm bridge2'
  }
}