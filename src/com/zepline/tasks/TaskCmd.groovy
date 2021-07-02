package com.zepline.tasks

class TaskCmdInput {
  List<String> commands

  TaskCmdInput(def input) {
    this.commands = input.script.each { cmd ->
      return cmd
    }
  }
}

class TaskCmd extends Taskable {
  TaskCmdInput input

  TaskCmd(def yaml, def script) {
    super("cmd", yaml, script)

    this.input = new TaskCmdInput(yaml.input)
  }

  def run() {
    input.commands.each { cmd ->
      script.sh cmd
    }
  }
}