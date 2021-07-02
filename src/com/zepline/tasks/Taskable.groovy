package com.zepline.tasks

abstract class Taskable {
  protected def script
  String name

  def Taskable(String name, def script) {
    this.name = name
    this.script = script
  }

  def execute() {
    return this.run()
  }

  abstract protected run()

}
