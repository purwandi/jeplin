package com.zepline.tasks

class TaskCmdInput {
  List<String> commands
}

class TaskCmd extends Taskable {
  TaskCmdInput input

  TaskCmd(def yaml, def script) {
    super("cmd", script)

    // Parsing command
    List<String> commands = yaml.input.script.each { cmd ->
      return cmd
    }

    this.input = new TaskCmdInput(commands: command)
  }

  def run() {
    input.commands.each { cmd ->
      script.sh cmd
    }
  }
}