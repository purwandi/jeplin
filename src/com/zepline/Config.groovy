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

  Config(def config, def yaml) {
    this.yaml = yaml
    // this.uid  = GenUID()

    if (yaml.image) {
      this.image = yaml.image
    }

    if (yaml.variables) {
      this.variables = yaml.variables
    }

    config.each { c, v ->
      if (c == "extends") {
        parseConfig(yaml."$v")
        return
      }

      // need refactor
      if (v) {
        if (c == "variables") {
          v.collect { i, n ->
            this.variables[i] = n
          }
          return
        } 

        this."$c" = v
      }
    }
  }

  // need refactor
  // @NonCPS
  def parseConfig(def yaml) {
    yaml.each { c, v -> 
      if (v) {
        if (c == "variables") {
          v.collect { i, n ->
            this.variables[i] = n
          }
          return
        } 

        this."$c" = v
      }
    }

    return this
  }

  // @NonCPS
  // def GenUID() {
  //   String alphabet = (('A'..'N')+('P'..'Z')+('a'..'k')+('m'..'z')+('2'..'9')).join() 
  //   def length = 8

  //   return new Random().with {
  //     (1..length).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
  //   }
  // }
}