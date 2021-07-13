package com.zepline

class Config {
  String        name
  Image         image
  String        stage

  List<Service> services      = []
  def           variables     = []
  def           credentials   = []
  def           docker
  List<String>  when
  List<String>  only
  def  before_script
  def  script
  def  after_script

  def config
  def yaml 
  def extend

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
      if (!["name","image","stage","services","variables","credentials","docker","when","only","before_script","script","after_script","extend"].contains(key)) {
        return
      }

      if (val != null) { // this.hasProperty(key) && val != null
        if (key == "variables") {
          val.each { var -> 
            this.variables.push(var)
          }
        } else if (key == "extend") {
          def cfgExtends = yaml."$val"
          if (cfgExtends != null) {
            parseConfig(cfgExtends)
          }
        } else if (key == "image") {
          this."$key" = new Image(val)
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
}