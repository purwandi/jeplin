package com.zepline

import com.zepline.tasks.*

class Zepline {
  // default image
  String image = "alpine:3.13"

  def config
  def script

  List<Taskable> tasks

  Zepline(def script, def config) {
    this.script = script
    this.config = config
    
    // set default image
    if (config.image != "") {
      this.image = config.image
    }
  }

  def init() {
    // Parse task
    this.tasks = config.steps.collect { yamlItem ->
      switch(yamlItem.task) {
        case "cmd": return new TaskCmd(yamlItem, script)
        case "docker": return new TaskDocker(yamlItem, script)
        case "helm": return new TaskHelm(yamlItem, script)
        default:
        break
      }
    }

    return this
  }

  def getImage(Taskable task) {
    return task.image ? task.image : image
  }

  def execute () {
    return { variables ->
      tasks.each { task ->
        script.image(getImage(task)).inside() {
          script.stage(task.name) {
            task.run()
          }
        }
      }
    }
  }

  
}