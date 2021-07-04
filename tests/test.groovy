import groovy.yaml.YamlSlurper

def yaml = """
stages:
  - prepare
  - build
  - release

tasks:
  release:
    image: alpine:3.13
    stage: release
    script: 
      - echo "hello from alpine 3.13"

  notification:
    image: alpine
    stage: prepare
    script:
      - echo "hello notification"

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

  build:
    image: alpine:3.13
    stage: build
    script: 
      - echo "hello from alpine 3.13"
"""

def config = new YamlSlurper().parseText(yaml)

def parse(def cfg) {
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
    def group = tasks.groupBy{it.value.stage}
    println group

    def closure = [:]
    stages.collect { k -> 
      if (group[k] != null) {
        closure[k] = group[k]
      }
    }
    tasks = closure

    println tasks
  }
}

  
// println(config)
parse(config)

// parse(config)