package com.zepline

class WithImageRegistry {

  static def parse(def config, def cfgRegistry, def script, def closure) {
    // if config registy is null 
    if (cfgRegistry == null) {
      return {
        closure()
      }
    }

    // loop in available docker property credentials
    def registry
    cfgRegistry.each { cfg -> 
      def hostname = new URL(cfg.registry).getHost()
      if (config.image.contains(hostname)) {
        registry = cfg
      }
    }
    
    // if image is not match in existing docker property we need to return it
    if (registry == null) {
      return {
        closure()
      }
    }

    // wrap closure using docker registry
    return {
      script.withDockerRegistry([url: registry.registry, credentialsId: registry.credential]) {
        closure()
      }
    }
  }

}