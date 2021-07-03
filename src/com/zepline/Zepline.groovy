package com.zepline

class Zepline {
  def config
  def script

  List<Step> steps

  Zepline(def script, def config) {
    this.script = script
    this.config = config
  }

  def init() {
    this.steps = config.steps.collect { k, item ->
      return new Step(k, item, script)
    }

    return this
  }
}