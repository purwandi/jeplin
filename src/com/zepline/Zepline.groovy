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
    this.tasks = config.steps.collect { item ->
      if (item.task == "cmd") {
        return new TaskCmd(item.task)
      }
    }

    // print(config.steps)

    return this
  }

  def run() {
    return { variables ->
      tasks.each { k, task ->
        script.stage(k) {
          script.sh "echo 'Running task'"
        }
      }
    }
    // script.stage("stage 1") {
    //   script.script {
    //     script.sh "echo 'Hello world'"
    //   }
    // }

    // script.stage("stage 2") {
    //   script.script {
    //     script.sh "echo 'Hello world'"
    //   }
    // }
  }

}