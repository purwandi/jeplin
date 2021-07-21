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
          // service callback
          def svc = {
            def image = script.docker.image(service.image.name)
            image.pull()

            def ctr  = image.run("${config.image.args} -v ${script.env.WORKSPACE}:${script.env.WORKSPACE}")

            links = links +  " --link $ctr.id:${service.alias}"
            ctrIds = " $ctr.id "
          }

          WithImageRegistry.parse(service, config.docker, script, svc)
        }
      }

      def cmd = { cmds ->
        if (cmds == null) {
          return
        }

        cmds.each { command -> 
          Command.parse(script, command)
        }
      }

      // if image is defined we run it using docker
      if (config.image.name) {
        // service callback
        def svc = {
          def image = script.docker.image(config.image.name)
          image.pull()
          image.inside("${config.image.args} $links") { 
            cmd(config.before_script)
            cmd(config.script)
            cmd(config.after_script)
          }
        }

        WithImageRegistry.parse(config, config.docker, script, svc)
      } else {
        cmd(config.before_script)
        cmd(config.script)
        cmd(config.after_script)
      }
    }

    try {
      // 1 Parse variable
      if (config.variables) {
        task = WithEnvironment.parse(config.variables, script, task)
      }

      // 2 Parse credentials
      if (config.credentials) {
        script.sh 'echo "with credentials"'
        task = WithCredentials.parse(config.credentials, script, task)
      }

      // 3 Parse node
      if (config.node) {
        task = WithNode.parse(config.node, script, task)
      }
      
      // 
      task()
    } finally {
      if (config.services) {
        Command.parse(script, "docker rm $containerIds --force")
      }
    }
  }
}
