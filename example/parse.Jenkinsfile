def parse(def cfg, def script) {
  def stages  = cfg.stages
  def tasks   = cfg.tasks

  if (stages) {
    // check if all task have stage property
    tasks.each { k, check -> 
      if (check.stage == null) {
        throw new Exception("The stage property in '${k}' is not defined ")
      }

      if (stages.contains(check.stage) == false) {
        throw new Exception("The stage property in '${k}' is not defined in stages ")
      }
    }

    // update task value for parallel build
    def closure = [:]
    def groups = tasks.groupBy{it.value.stage}
    stages.collect{ k ->
      if (groups[k] != null) {
        closure[k] = groups[k]
      }
    }
    
    tasks = closure
  }

  for (t in buildTask(tasks, script).values()) {
    t.call()
  }
}

def buildTask(def tasks, def script) {
  def closure = [:]
  tasks.each { k, item ->
    closure[k] = {
      script.stage(k) {
        if (item.script) {
          item.script.each { command ->
            script.sh command
          }
        } else {
          parallel buildTask(item, script)
        }
      }
    }
  }

  return closure
}

node {
  def config = readYaml text: """
stages:
  - prepare
  - build
  - release
  - post

.helm: &helm
  script:
    - echo "Runing as helm"
    - sleep 30

tasks:
  release:
    image: alpine:3.13
    stage: release
    script: 
      - echo "hello from alpine 3.13"
      - sleep 10

  notification:
    image: alpine
    stage: prepare
    script:
      - echo "hello notification"
      - sleep 15

  test:
    image: alpine
    stage: build
    services:
      - image: nginx:alpine
        alias: web
        # entrypoint: 
        # command: 
    script:
      - echo "hello"
      - sleep 7

  docker:
    image: alpine:3.13
    stage: build
    script: 
      - echo "hello from alpine 3.13"
      - sleep 20

  helm:
    <<: *helm
    stage: release
    variables:
      ENV: prod
  
  notif_post:
    image: alpine:3.13
    stage: post
    script: 
      - echo "post from alpine 3.13"
      - sleep 20
"""
  parse(config, this)
}