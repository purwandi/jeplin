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
          Command.parse(script, command)
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
        // authenticate with docker registry for pulling preparation
        config.docker.each { cfg -> 
          def creds = [
            script.usernamePassword(
              credentialsId: cfg.credentials,
              usernameVariable: "DOCKER_REGISTRY_USERNAME",
              passwordVariable:  "DOCKER_REGISTRY_PASSWORD",
            )
          ]

          script.withCredentials(creds) {
            Command.parse(script, (script: 'docker login -u $DOCKER_REGISTRY_USERNAME -p $DOCKER_REGISTRY_PASSWORD ' + cfg.registry))
          }
        }
      }

      if (config.credentials) {
        // script.sh "echo 'using credentials'"
        task = WithCredentials.parse(config.credentials, script, task)
      }

      task()
    } finally {
      // cleanup authentication with docker registry
      if (config.docker) {
        // config.docker.each { cfg -> 
        //   Command.parse(script, (script: 'docker logout ' + cfg.registry))
        // }
      }

      if (config.services) {
        Command.parse(script, "docker rm $containerIds --force")
      }
    }
  }
}
