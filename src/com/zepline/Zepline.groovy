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
    // println(config.steps)

    List<Step> steps = config.steps.each { item ->
      // println(item)
    }
    
    // this.steps = config.steps.collect { k, item ->
    //   return new Step(k, item)
    // }
  }
}