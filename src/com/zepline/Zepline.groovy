package com.zepline

class Zepline {
  def config
  def script

  List<Taskable> tasks

  Zepline(def script, def config) {
    this.script = script
    this.config = config
  }

  def init() {
    println(config)
    // config.steps.collect { k, item ->
    //   println(k)
    // }
  }
}