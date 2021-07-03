package com.zepline.tasks

import com.zepline.tasks.TaskDockerInput

class TaskKaniko extends Taskable {
  TaskDockerInput input

  TaskKaniko(def yaml, def script) {
    super("kaniko", yaml, script)

    this.input = new TaskDockerInput(yaml.input, script)
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