package com.zepline

class Task {
  String        name
  Config        config

  Task (String name, Config config) {
    this.name     = name
    this.config   = config
  }

  def execute(def script) {
    // script.sh 'echo "Hello"'
    config.script.each { command -> 
      script.sh command
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