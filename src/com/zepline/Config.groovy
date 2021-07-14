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
  def extend

  Config(def config, def yaml) {
    this.yaml   = yaml 
    this.config = config
  }

  def parse() {
    // Parse variables
    parseVarConfig(yaml)
    parseVarConfig(config)
    if (config.extend) {
      def cfgExtends = yaml."${config.extend}"
      if (cfgExtends != null) {
        parseVarConfig(cfgExtends)
      }
    }
    
    // parse global
    parseConfig(yaml, true)
    parseConfig(config, true)

    return this
  }

  def parseVarConfig(def cfg) {
    if (cfg.variables == null) {
      return
    }

    cfg.variables.each { k, v -> 
      this.variables[k] = v
    }
  }

  def parseConfig(def cfg, def highOrder) {
    cfg.each { key, val -> 
      if (!["name","image","stage","services","credentials","docker","when","only","before_script","script","after_script","extend"].contains(key)) {
        return
      }

      if (val != null) { // this.hasProperty(key) && val != null
        if (key == "extend") {
          def cfgExtends = yaml."$val"
          if (cfgExtends != null) {
            parseConfig(cfgExtends, false)
          }
        } else if (key == "variables") {
          return
        } else if (key == "credentials") {
          val.each { c -> 
            def idx = this.credentials.findIndexOf{it.credential == c.credential}
            if (idx >= 0) {
              this.credentials[idx] = c
            } else {
              this.credentials.add(c)
            }
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