package com.zepline

import com.zepline.tasks.Taskable
import com.zepline.tasks.TaskCmd

class Zepline {
  def config
  def script

  List<Taskable> tasks

  Zepline(def script, def config) {
    this.script = script
    this.config = config
  }

  def init() {
    // Parse task
    this.tasks = config.steps.collect { yamlItem ->
      switch(yamlItem.task) {
        case "cmd": return new TaskCmd(yamlItem, script)
        case "docker": return new TaskDocker(yamlItem, script)
        default:
        break
      }
    }

    return this
  }
}