package com.zepline

class Zepline {
  def config
  def jk

  List<Step> steps

  Zepline(def jk, def config) {
    this.jk = jk
    this.config = config
  }

  def init() {
    this.steps = config.steps.collect { k, item ->
      return new Step(k, item, jk)
    }

    return this
  }
}