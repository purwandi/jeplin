package com.zepline

import com.zepline.tasks.Taskable
import com.zepline.tasks.TaskCmd

class Zepline {
  def config
  List<Taskable> tasks

  Zepline(def config) {
    this.config = config
  }

  def init() {
    this.tasks = config.steps.collect { item ->
      if (item.task == "cmd") {
        return new TaskCmd(name: item.task)
      }
    }

    return this
  }

  def build() {
    
  }

}