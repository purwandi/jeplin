package com.zepline

class Zepline {
  def stages
  def tasks = [:]
  def yaml
  def script

  Zepline(def script, def yaml) {
    this.script = script
    this.yaml = yaml

    this.stages = yaml.stages
  }

  def init() {
    this.yaml.tasks.each { k, v ->
      def config = new Config(v, yaml)
    //   return new Task(k, new Config(v, yaml), script)
    }

    // this.tasks = config.tasks.collect { k, item ->
    //   return new Task(k, item, script)
    // }

    return this
  }
}