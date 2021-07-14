package com.zepline

class Config {
  String        name
  Image         image
  String        stage

  List<Service> services      = []
  def           variables     = [:]
  def           credentials   = []
  def           docker        = []
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
    // Parse global
    parseVarConfig(yaml.".variables")
    parseCredentialConfig(yaml.".credentials")
    parseDockerCredentialConfig(yaml.".docker")

    // Parse variables
    parseVarConfig(yaml.variables)
    parseVarConfig(config.variables)
    if (config.extend) {
      def cfgExtends = yaml."${config.extend}"
      if (cfgExtends != null) {
        parseVarConfig(cfgExtends)
      }
    }
    
    // parse global
    parseConfig(yaml)
    parseConfig(config)

    return this
  }

  def parseVarConfig(def cfg) {
    if (cfg == null) {
      return
    }

    cfg.each { k, v -> 
      this.variables[k] = v
    }
  }

  def parseCredentialConfig(def cfg) {
    if (cfg == null) {
      return
    }

    cfg.each { c -> 
      def idx = this.credentials.findIndexOf{it.credential == c.credential}
      if (idx >= 0) {
        this.credentials[idx] = c
      } else {
        this.credentials.add(c)
      }
    }
  }

  def parseDockerCredentialConfig(def cfg) {
    if (cfg == null) {
      return
    }
    cfg.each { c -> 
      def idx = this.docker.findIndexOf{it.registry == c.registry}
      if (idx >= 0) {
        this.docker[idx] = c
      } else {
        this.docker.add(c)
      }
    }
  }

  def parseConfig(def cfg) {
    cfg.each { key, val -> 
      if (!["name","image","stage","services","credentials","docker","when","only","before_script","script","after_script","extend"].contains(key)) {
        return
      }

      if (val != null) { // this.hasProperty(key) && val != null
        if (key == "extend") {
          def cfgExtends = yaml."$val"
          if (cfgExtends != null) {
            parseConfig(cfgExtends)
          }
        } else if (key == "credentials") {
          parseCredentialConfig(val)
        } else if (key == "docker") {
          parseDockerCredentialConfig(val)
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