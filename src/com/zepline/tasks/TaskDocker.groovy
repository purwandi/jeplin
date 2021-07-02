package com.zepline.tasks

class TaskDockerInput {
  String credential
  String registry
  String context
  String dockerfile
  // String command
  String repository
  String tag
  String args

  TaskDockerInput(def input, def script) {
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


class TaskDocker extends Taskable {
  TaskDockerInput input

  TaskDocker(def yaml, def script) {
    super("docker", yaml, script)

    this.input = new TaskDockerInput(yaml.input, script)
  }

  def run() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.build(
        "${input.repository}:${input.tag}",
        "-f ${input.dockerfile} ${input.args} ${input.context}",
      ).push()
    }
  }
}