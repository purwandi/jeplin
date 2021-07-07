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
          WithImageRegistry.parse(service, config.docker, script, {
            def ctr = script
                  .docker
                  .image(service.image)
                  .run("-v ${script.env.WORKSPACE}:${script.env.WORKSPACE}")

            links = links +  " --link $ctr.id:${service.alias}"
            ctrIds = " $ctr.id "
          })
        }
      }

      def cmd = {
        config.script.each { command -> 
          Command.parse(script, command)
        }
      }

      // if image is defined we run it using docker
      if (config.image) {
        WithImageRegistry.parse(config, config.docker, script, {
          script.docker.image(config.image).inside("$links") { 
            cmd()
          }
        })
      } else {
        cmd()
      }
    }

    try {
      WithEnvironment.parse(config, script)

      if (config.credentials) {
        // script.sh "echo 'using credentials'"
        task = WithCredentials.parse(config.credentials, script, task)
      }

      task()
    } finally {
      if (config.services) {
        Command.parse(script, "docker rm $containerIds --force")
      }
    }
  }
}
