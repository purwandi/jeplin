package com.zepline.tasks

class TaskHelmInput {
  List<String> commands

  TaskHelmInput(def input) {
    this.commands = input.script.each { cmd ->
      return cmd
    }
  }
}

class TaskHelm extends Taskable {
  TaskHelmInput input

  TaskHelm(def yaml, def script) {
    super("helm", yaml, script)

    this.input = new TaskHelmInput(yaml.input)
  }

  def run() {
    script.sh 'kubectl version --short || true'
    script.sh 'helm version --short || true'

    
    input.commands.each { cmd ->
      script.sh cmd
    }
  }
}