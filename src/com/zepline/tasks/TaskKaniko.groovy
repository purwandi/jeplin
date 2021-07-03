package com.zepline.tasks

class TaskKanikoInput {
  String credential
  String registry
  String context
  String dockerfile
  // String command
  String repository
  String tag
  String args

  TaskKanikoInput(def input, def script) {
    this.dockerfile = input.dockerfile
    this.context    = input.context
    this.repository = input.repository
    this.credential = input.credential
    this.registry   = input.registry
    // this.command    = input.command
    this.tag        = input.tag
    this.args       = input.args
  }
}

class TaskKaniko extends Taskable {
  TaskKanikoInput input

  TaskKaniko(def yaml, def script) {
    super("kaniko", yaml, script)

    this.input = new TaskKanikoInput(yaml.input, script)
  }

  def run() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.sh "/kaniko/executor \
        --insecure --skip-tls-verify --insecure-pull --skip-tls-verify-pull \
        --insecure-registry --skip-tls-verify-registry \
        --dockerfile ${input.dockerfile} \
        --context ${input.context} \
        ${input.args} \
        --destination ${input.repository}:${input.tag}"
    }
  }
}