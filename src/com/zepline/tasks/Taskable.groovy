package com.zepline.tasks

abstract class Taskable {
  protected def script
  protected String name

  def execute(def script, String name) {
    this.script = script
    this.name = name
  }

  abstract protected run()

}
