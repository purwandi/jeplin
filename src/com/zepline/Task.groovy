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
        scrip.sh "echo 'using docker registry auth'"
        task = WithImageRegistry.parse(config.docker, script, task)
      }

      if (config.credentials) {
        scrip.sh "echo 'using credentials'"
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
