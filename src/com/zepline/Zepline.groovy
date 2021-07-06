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

  def parseGitEnvironment() {
    def envs = [
      "CI_GIT_COMMIT_SHA": "git --no-pager show -s --format='%H'",
      "CI_GIT_COMMIT_SHORT_SHA": "git --no-pager show -s --format='%h'",
      "CI_GIT_AUTHOR_NAME": "git --no-pager show -s --format='%an'",
      "CI_GIT_AUTHOR_EMAIL": "git --no-pager show -s --format='%ae'",
      "CI_GIT_COMMIT": "git --no-pager show -s --format='%s'",
    ]
    
    envs.each { k, v ->
      if (script.isUnix()) {
        script.env."$k" = script.sh(script: v, returnStdout: true).trim()
      } else {
        script.env."$k" = script.bat(script: v, returnStdout: true).trim()
      }
    }
  }

  def init() {
    this.stages = yaml.stages
    this.yaml.tasks.each { k, v ->
      def config = Config.parse(v, yaml)
      def task = new Task(k, config)
      this.tasks[k] = task
    }

    parseGitEnvironment()

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
    for (t in taskable(tasks, script).values()) {
      t.call()
    }
  }

  def taskable (def t, def script) { 
    def closure = [:]
    
    t.each { k, task -> 
      closure[k] = {
        script.stage(k) {
          if (task.config != null && task.config.script != null) {
            task.execute(script)
          } else {
            script.parallel taskable(task, script)
          }
        }
      }
    }

    return closure
  }
}