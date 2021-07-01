package com.zepline.tasks

abstract class Taskable {
  protected def script
  protected String name

  def Taskable(def script, String name) {
    this.script = script
    this.name = name
  }

  def execute() {
    return this.run()
  }

  abstract protected run()

}
