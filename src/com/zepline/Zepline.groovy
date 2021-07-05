package com.zepline

import java.util.LinkedHashMap

class Zepline {
  def stages
  def tasks = [:]
  def yaml
  def script

  Zepline(def script, def yaml) {
    this.script = script
    this.yaml = yaml
  }

  def init() {
    this.stages = yaml.stages
    this.yaml.tasks.each { k, v ->
      def config = new Config(v, yaml)
      def task = new Task(k, config, script)
      this.tasks[k] = task
    }

    if (stages) {
      // check if all task have stage property
      tasks.each { k, v -> 
        if (v.config.stage == null) {
          throw new Exception("The stage property in '${k}' is not defined ")
        }

        if (stages.contains(v.config.stage) == false) {
          throw new Exception("The stage property in '${k}' is not defined in stages ")
        }
      }

      // sort tasks by stage
      def closure = [:]
      def groups = tasks.groupBy{it.value.config.stage}
      stages.collect{ k ->
        if (groups[k] != null) {
          closure[k] = groups[k]
        }
      }
      this.tasks = closure
    }
    return this
  }

  def execute() {
    for (t in taskable(tasks).values()) {
      t.call()
    }
  }

  def taskable (def t) { 
    def that = this
    def closure = [:]
    t.each { k, task -> 
      closure[k] = {
        script.stage(k) {
          if (task.config != null && task.config.script != null) {
            script.sh "echo 'Hello'"
          } else {
            script.parallel taskable(task)
          }
        }
      }
    }

    return closure
  }
}