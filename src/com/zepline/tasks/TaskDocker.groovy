package com.zepline.tasks

class TaskDockerInput {
  String credential
  String registry
  String context
  String dockerfile
  String command
  String repository
  String tag
  String args

  TaskDockerInput(def input, def script) {
    this.dockerfile = input.dockerfile
    this.context    = input.context
    this.repository = input.repository
    this.credential = input.credential
    this.registry   = input.registry
    this.command    = input.command
    this.tag        = input.tag
    this.args       = input.args
  }
}


class TaskDocker extends Taskable {
  TaskDockerInput input

  TaskDocker(def yaml, def script) {
    super("docker", script)

    this.input = new TaskDockerInput(yaml.input, script)
  }

  def run() {
    switch(input.command) {
      case 'buildPush': return buildPush()
      case 'build': return build()
      case 'push': return push()
      case 'delete': return delete()
      default:
        throw new Exception("Invalid input.command ${input.command} was not recognized")
      break
    }
  }

  def buildPush() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.build(
        "${input.repository}:${input.tag}",
        "-f ${input.dockerfile} ${input.args} ${input.context}",
      ).push()
    }
  }

  def build() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.build(
        "${input.repository}:${input.tag}",
        "-f ${input.dockerfile} ${input.args} ${input.context}",
      )
    }
  }

  def push() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.push("${input.repository}:${input.tag}")
    }
  }
}