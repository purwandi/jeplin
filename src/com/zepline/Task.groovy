package com.zepline

class Task {
  String        name
  Config        config

  Task (String name, Config config) {
    this.name     = name
    this.config   = config
  }

  def execute(def script) {
    String links = ""
    String containerIds = ""

    if (config.script == null) {
      throw new Exception("The script property in '${k}' is not defined ")
    }

    def task = {
      if (config.services) {
        config.services.each { service ->
          def container = script.docker.image(service.image).run("-v ${script.env.WORKSPACE}:${script.env.WORKSPACE}")
          links = links +  " --link $container.id:${service.alias}"
          containerIds = " $container.id "
        }
      }

      // parse environment variable
      if (config.variables) {
        config.variables.each { k, v -> 
          script.env."$k" = v
        }
      }
      
      script.docker.image(config.image).inside("$links") { c ->
        config.script.each { command -> 
          script.sh command
        }
      }
    }

    try {
      if (config.docker) {
        task = WithImageRegistry.parse(config.docker, script, task)
      }

      if (config.credentials) {
        task = WithCredentials.parse(config.credentials, script, task)
      }

      task()
    } finally {
      if (config.services) {
        script.sh "docker rm $containerIds --force"
      }

      script.cleanWs()
    }
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
