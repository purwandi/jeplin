package com.zepline

class Config {
  String        name
  Image         image
  String        stage

  List<Service> services      = []
  def           variables     = [:]
  def           credentials   = []
  def           docker
  List<String>  when
  List<String>  only
  def  before_script
  def  script
  def  after_script

  def config
  def yaml 

  Config(def config, def yaml) {
    this.yaml   = yaml 
    this.config = config
  }

  def parse() {
    // parse global
    parseConfig(yaml)

    // parse extends
    parseConfig(config)

    return this
  }

  def parseConfig(def cfg) {
    cfg.each { key, val -> 
      if (this.hasProperty(key) && val != null) {
        if (key == "extends") {
          def cfgExtends = yaml."$v"
          if (cfgExtends != null) {
            parseConfig(cfgExtends)
          }
        } else if (key == "image") {
          this."$key" = new Image(val)
        } else if (key == "variables") {
          val.each { i, n -> 
            this.variables[i] = n
          }
        } else if (key == "services") {
          val.each { s -> 
            this.services.add(new Service(s))
          }
        } else {
          this."$key" = val
        }
      }
    }
  }



  // static Config parse(def config, def yaml) {
  //   Config data = new Config()
  //   data.yaml = yaml

  //   if (yaml.image) {
  //     data.image = new Image(yaml.image)
  //   }
    
  //   if (yaml.variables) {
  //     data.variables = yaml.variables
  //   }

  //   if (yaml.docker) {
  //     data.docker = yaml.docker
  //   }

  //   // this.variables = yaml.variables
  //   config.each { k, v -> 
  //     if (k == "extends") {
  //       def yamlExtends = yaml."$v"
  //       if (yamlExtends) {
  //         yamlExtends.each {ky, vy -> 
  //           if (ky == "variables") {
  //             vy.collect { i, n ->
  //               data.variables[i] = n
  //             }
  //             return
  //           }
  //           if (data.hasProperty(ky)) {
  //             data."$ky" = vy
  //           }
  //         }
  //       }
  //       return
  //     }

  //     if (v) {
  //       if (k == "variables") {
  //         v.collect { i, n ->
  //           data.variables[i] = n
  //         }
  //         return
  //       }
  //       if (data.hasProperty(k)) {
  //         data."$k" = v
  //       }
  //     }
  //   }
  //   return data
  // }
}