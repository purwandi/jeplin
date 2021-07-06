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
      "CI_GIT_REMOTE_BRANCH_NAME": "git name-rev --name-only HEAD",
      // "CI_GIT_RELEASE_NAME": "git describe --tags --abbrev=0 || true"
    ]
    
    envs.each { k, v ->
      if (script.isUnix()) {
        script.env."$k" = script.sh(script: v, returnStdout: true).trim()
      } else {
        script.env."$k" = script.bat(script: v, returnStdout: true).trim()
      }
    }

    // parse git branch name
    if (script.env.CI_GIT_REMOTE_BRANCH_NAME.contains("tags/")) {
      script.env.CI_GIT_BRANCH_NAME = "tags"
    } else {
      script.env.CI_GIT_BRANCH_NAME = script.env.CI_GIT_REMOTE_BRANCH_NAME.replace("remotes/origin/", "")
    }

    if (script.env.CI_GIT_BRANCH_NAME == "tags") {
      script.env.CI_GIT_RELEASE_NAME = script.env.CI_GIT_REMOTE_BRANCH_NAME.replace("tags/", "")
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

  def canBuild(def script, def task) {
    script.sh "echo 'Trigger can build'"
    if (task.config == null) {
      return true
    }

    // if task.only is not defined
    if (task.config.only == null) {
      script.sh "echo 'By pass task, because task.only is null'"
      return true
    }

    // validate if task can run
    return task.config.only.contains(script.env.CI_GIT_BRANCH_NAME)
  }

  def taskable (def t, def script) { 
    def closure = [:]
    
    t.each { k, task -> 
      closure[k] = {
        script.stage(k) {
          if (task.config != null && task.config.script != null) {
            if (canBuild(script, task)) {
              task.execute(script)
            }
          } else {
            script.parallel taskable(task, script)
          }
        }
      }
    }

    return closure
  }
}