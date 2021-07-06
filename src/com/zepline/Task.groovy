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
        if (config.image == null) {
          throw new Exception("Sidecar service only available in docker pipeline")
        }

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

      def cmd = {
        config.script.each { command -> 
          if (isUnix()) {
            script.sh command
          } else {
            script.bat command
          }
        }
      }
      

      // if image is defined we run it using docker
      if (config.image) {
        script.docker.image(config.image).inside("$links") { 
          cmd()
        }
      } else {
        cmd()
      }
    }

    try {
      if (config.docker) {
        // script.sh "echo 'using docker registry auth'"
        task = WithImageRegistry.parse(config.docker, script, task)
      }

      if (config.credentials) {
        // script.sh "echo 'using credentials'"
        task = WithCredentials.parse(config.credentials, script, task)
      }

      task()
    } finally {
      if (config.services) {
        script.sh "docker rm $containerIds --force"
      }
    }
  }
}
