package com.zepline

class WithImageRegistry {

  static def parse(def config, def script, def closure) {
    return script.docker.withRegistry(config.registry, config.credential) {
      return closure
    }
  }
  
}