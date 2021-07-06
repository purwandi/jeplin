package com.zepline

class WithImageRegistry {

  static def parse(def config, def script, def closure) {
    def registry = { cfg, clsr ->
      script.docker.withRegistry(cfg.registry, cfg.credential) {
        clsr()
      }
    }

    config.each { cfg ->
      closure = registry(cfg, closure)
    }

    // return {
    //   config.each { cfg ->
    //     // script.docker.withRegistry(cfg.registry, cfg.credential) {
    //     //   closure()
    //     // }
    //   }
    // // }

    return closure
  }

}