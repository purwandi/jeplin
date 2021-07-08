package com.zepline

class Config {
  String        name
  String        image
  String        stage

  List<Service> services      = []
  def           variables     = [:]
  def           credentials   = []
  def           docker
  List<String>  when
  List<String>  only
  List<String>  before_script
  List<String>  script
  List<String>  after_script

  def yaml 

  static Config parse(def config, def yaml) {
    Config data = new Config()
    data.yaml = yaml

    if (yaml.image) {
      data.image = yaml.image
    }
    
    if (yaml.variables) {
      data.variables = yaml.variables
    }

    if (yaml.docker) {
      data.docker = yaml.docker
    }

    // this.variables = yaml.variables
    config.each { k, v -> 
      if (k == "extends") {
        def yamlExtends = yaml."$v"
        if (yamlExtends) {
          yamlExtends.each {ky, vy -> 
            if (ky == "variables") {
              vy.collect { i, n ->
                data.variables[i] = n
              }
              return
            }
            if (data.hasProperty("$ky")) {
              data."$ky" = vy
            }
          }
        }
        return
      }

      if (v) {
        if (k == "variables") {
          v.collect { i, n ->
            data.variables[i] = n
          }
          return
        }
        if (data.hasProperty("$k")) {
          data."$k" = v
        }
      }
    }
    return data
  }
}