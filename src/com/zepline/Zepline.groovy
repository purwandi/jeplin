package com.zepline

import com.zepline.tasks.*

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
    config.steps.collect { k, item ->
      println(k)
    }
    
    // // Parse task
    // this.tasks = config.steps.collect { yamlItem ->
    //   switch(yamlItem.task) {
    //     case "cmd": return new TaskCmd(yamlItem, script)
    //     case "docker": return new TaskKaniko(yamlItem, script)
    //     case "helm": return new TaskHelm(yamlItem, script)
    //     default:
    //     break
    //   }
    // }

    // return this
  }

  // def getImage(Taskable task) {
  //   return task.image ? task.image : image
  // }

  // def execute () {
  //   return { variables ->
  //     tasks.each { task ->
  //       script.docker.image(getImage(task)).inside("--entrypoint=''") {
  //         script.stage(task.name) {
  //           task.run()
  //         }
  //       }
  //     }
  //   }
  // }
}