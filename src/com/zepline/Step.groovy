package com.zepline

class Service {
  String image
  String alias
  List<String> entrypoint
  List<String> command
}

class Step {
  String        name
  String        image
  List<String>  environments
  List<String>  scripts
  List<String>  only

  List<Service> services
  def           script

  Step (String name, def config) {
    this.name = name
  }

  def execute() {
    // script.sh 'docker network create bridge1 || true'
    // script.sh(script: 'docker run --net bridge1 --name nginx -d nginx:alpine', returnStdout: true)
    // script.sh(script: 'docker run --net bridge1 --name httpd -d httpd:2.4-alpine', returnStdout: true)

    // script.docker.image('alpine:latest').inside('--net bridge1') { c ->
    //   // script


    //   // script.stage('test nginx') {
    //   //   script.sh 'curl http://nginx'
    //   // }
      
    //   // script.stage('test httpd') {
    //   //   script.sh 'curl http://httpd'
    //   // }
    // }

    // script.sh 'docker rm nginx httpd --force'
    // script.sh 'docker network rm bridge1 --force'
  }
}