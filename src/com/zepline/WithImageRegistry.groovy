package com.zepline

class WithImageRegistry {

  static def parse(def config, def script, def closure) {
    def registry = { cfg, clsr ->
      return {
        script.withDockerRegistry([url: cfg.registry, credentialsId: cfg.credential]) {
          clsr()
        }
      }
    }

    config.each { cfg ->
      closure = registry(cfg, closure)
    }

    return closure
  }

}