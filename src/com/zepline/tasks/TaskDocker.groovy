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

  TaskDockerInput(def yamlInputs, def script) {
    this.dockerfile = yamlInputs.dockerfile
    this.context    = yamlInputs.context
    this.repository = yamlInputs.repository
    this.credential = yamlInputs.credential
    this.registry   = yamlInputs.registry
    this.command    = yamlInputs.command
    this.tag        = yamlInputs.tag
  }
}


class TaskDocker extends Taskable {
  TaskDockerInput input

  TaskDocker(def yaml, def script) {
    super("docker", script)

    this.input = new TaskDockerInput(yaml, script)
  }

  def run() {
    switch(input.command) {
      case 'build': return this.build()
      case 'push': return this.push()
      case 'delete': return this.delete()
      default:
        throw new Exception("Invalid input.command ${input.command} was not recognized")
      break
    }
  }

  def build() {
    script.docker.WithRegistry(input.registry, input.credential) {
      script.docker.build(
        "${input.registry}/${input.repository}:${input.tag}",
        "-f ${input.dockerfile}",
        "${input.context}"
      )
    }
  }

  def push() {
    script.docker.WithRegistry(input.registry, input.credential) {
      script.docker.push("${input.registry}/${input.repository}:${input.tag}")
    }
  }

  def delete() {

  }
}