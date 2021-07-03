package com.zepline.tasks


class Service {
  String image

}

abstract class Taskable {
  protected def script

  String name
  String image
  List<Service> services

  def Taskable(String name, def yaml, def script) {
    this.name = name
    this.image = yaml.image
    this.script = script
  }

  def execute() {
    script.docker.image(image).inside() { c ->
      
    }
    // return this.run()
  }

  abstract protected run()

}
