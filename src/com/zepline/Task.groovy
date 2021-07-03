package com.zepline

class Service {
  String image
  String alias
  List<String> entrypoint
  List<String> command
}

class Task {
  String        name
  String        image
  List<String>  environments
  List<String>  command
  List<String>  only

  List<Service> services
  def           jk
  def           config

  Task (String name, def config, def jk) {
    this.name     = name
    this.config   = config
    this.jk   = jk
  }

  def execute() {
    jk.stage(name) {
      jk.sh 'echo "Hello"'
    }
  }

  // def run() {
    
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
  // }
}