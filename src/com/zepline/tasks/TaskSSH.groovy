package com.zepline.tasks

class TaskSSH extends Taskable {
  List<String> commands

  TaskCmd(String name, def script) {
    super(name, script)
  }

  def run() {

  }
}