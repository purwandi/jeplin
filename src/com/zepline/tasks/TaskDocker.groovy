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
      case 'build': return build()
      case 'push': return push()
      case 'delete': return delete()
      default:
        throw new Exception("Invalid input.command ${input.command} was not recognized")
      break
    }
  }

  def build() {
    println(input)
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.build(
        "${input.registry}/${input.repository}:${input.tag}",
        "-f ${input.dockerfile}",
        "${input.context}"
      )
    }
  }

  def push() {
    script.docker.withRegistry(input.registry, input.credential) {
      script.docker.push("${input.registry}/${input.repository}:${input.tag}")
    }
  }

  def delete() {

  }
}