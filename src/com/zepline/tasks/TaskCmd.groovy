package com.zepline.tasks

class TaskCmd extends Taskable {
  List<String> commands

  TaskCmd(def script, String name, List<String> commands) {
    super(script, name)
    this.commands = commands
  }
  
  def run() {
    script.each{ command -> 
      script command
    }
  }
}