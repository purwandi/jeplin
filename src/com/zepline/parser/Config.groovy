package com.zepline.parser

import com.zepline.Zepline
import com.zepline.tasks.Taskable
import com.zepline.tasks.*

class Config {
  static Zepline parse(def yaml, def script) {
    Zepline zepline = new Zepline()

    zepline.tasks = parseTasks(yaml, script)
    return zepline
  }

  static def parseTasks(def yaml, def script) {
    List<Taskable> tasks = yaml.steps.collect { item ->
      if (item.task == "cmd") {
        return new TaskCmd(script: script, name: item.task, commands: item.input.scripts)
      }
    }

    return tasks
  }
}