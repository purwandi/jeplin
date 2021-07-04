import groovy.yaml.YamlSlurper

def content
for (c in ["manifest/.helm.yaml", "manifest/.zepline.yaml"]) {
  println c
}

// def config = new YamlSlurper().parseText(yaml)
