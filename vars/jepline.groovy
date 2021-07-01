import groovy.yaml.YamlSlurper

def call(String filename) {
   def yaml = readYaml file: filename;
  // def config = new YamlSlurper().parse(new File(filename))
  println(config)
}

// call(".jepline.yaml")