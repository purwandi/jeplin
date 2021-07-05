package com.zepline

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
      this.tasks[k] = new Task(k, config, script)
    }

    if (stages) {
      // check if all task have stage property
      tasks.each { k, v -> 
        if (v.stage == null) {
          throw new Exception("The stage property in '${k}' is not defined ")
        }

        if (stages.contains(v.stage) == false) {
          throw new Exception("The stage property in '${k}' is not defined in stages ")
        }
      }

      // sort tasks by stage
      def closure = [:]
      def groups = tasks.groupBy(it.value.stage)
      stages.collect{k ->
        if (groups[k] != null) {
          closure[k] = groups[k]
        }
      }
      this.tasks = closure
    }
    return this
  }

  def execute() {
    this.buildTask(tasks).values()
    // for (t in ) {
    //   t.call()
    // }
  }

  def buildTasks(def t) {
    def closure = [:]
    // t.each { k, task ->
    //   closure[k] = {
    //     // script.stage(k) {
    //     //   if (task.config.script) {
    //     //     script.sh 'echo "Hello"'
    //     //     // task.execute()
    //     //   } else {
    //     //     parallel buildTask(task)
    //     //   }
    //     // }        
    //   }
    // }

    return closure
  }
}