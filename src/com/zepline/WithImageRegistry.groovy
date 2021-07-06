package com.zepline

class WithImageRegistry {

  static def parse(def config, def script, def closure) {
    def registry = { cfg, clsr ->
      return {
        script.docker.withRegistry(cfg.registry, cfg.credential) {
          clsr()
        }
      }
    }

    config.each { cfg ->
      closure = registry(cfg, closure)
    }

    return closure
    

    // // return {
    // //   config.each { cfg ->
    // //     // script.docker.withRegistry(cfg.registry, cfg.credential) {
    // //     //   closure()
    // //     // }
    // //   }
    // // // }

    // // return {
    // //   registry()
    // // }
  }

}