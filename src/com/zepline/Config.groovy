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
  List<String>  before_script
  List<String>  script
  List<String>  after_script

  def yaml 

  Config(def config, def yaml) {
    this.yaml = yaml 

    // parse global
    this.parse(yaml)

    // parse extends
    this.parse(config)
  }

  def parse(def cfg) {
    cfg.each { key, val -> 
      if (this.hasProperty(key) && val != null) {
        if (key == "extends") {
          def cfgExtends = yaml."$v"
          if (cfgExtends != null) {
            this.parse(cfgExtends)
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