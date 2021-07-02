package com.zepline.tasks

abstract class Taskable {
  protected def script
  String name
  String image

  def Taskable(String name, def yaml, def script) {
    this.name = name
    this.image = yaml.image
    this.script = script
  }

  def execute() {
    return this.run()
  }

  abstract protected run()

}
