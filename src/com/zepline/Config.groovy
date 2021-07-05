package com.zepline

class Config {
  String        uid
  String        name
  String        image
  String        stage

  List<Service> services  = []
  def           variables = [:]
  List<String>  only
  List<String>  script

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
            data."$ky" = vy
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
        data."$k" = v
      }
    }
    return data
  }
}