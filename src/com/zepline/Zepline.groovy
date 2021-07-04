package com.zepline

class Zepline {
  def config
  def script

  String        image
  List<Service> services
  def           environment


  List<Task> tasks

  Zepline(def script, def config) {
    this.script = script
    this.config = config
  }

  def init() {
    // this.tasks = config.tasks.collect { k, item ->
    //   return new Task(k, item, script)
    // }

    return this
  }

  def execute() {
    return { variables ->
      tasks.each { task ->
      }
    }
  }
}