import groovy.yaml.YamlSlurper

import com.zepline.Config

def yaml = """
image: alpine:latest
image: curl:latest

include:
  - remote: 'https://github.com/purwandi/zepline-test.git'
    credential: github-credentials
    ref: main
    files: 
      - /helm.yaml

tasks:
  release:
    image: alpine:3.13
    stage: release
    script: 
      - echo "hello from alpine 3.13"
      - sleep 10

  helm:
    extends: .helm
    stage: release
    variables:
      ENV: prod

.helm:
  image: dtzar/helm-kubectl:latest
  variables:
    PORT: 8080
  script:
    - echo "Runing as helm"
    - sleep 30
"""

def y = new YamlSlurper().parseText(yaml)
y.tasks.each { c, v ->
  def cfg = Config.parse(v, y)

  println cfg.getProperties().toString()
  
}
// def c = new Config(y.helm, y)
