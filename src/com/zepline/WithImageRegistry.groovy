package com.zepline

class WithImageRegistry {

  static def parse(def config, def cfgRegistry, def script, def closure) {
    def getHost = { url ->
        String domainName = new String(url)
        int index = domainName.indexOf("://")
        if (index != -1) {
          // keep everything after the "://"
          domainName = domainName.substring(index + 3)
        }

        index = domainName.indexOf('/')

        if (index != -1) {
          // keep everything before the '/'
          domainName = domainName.substring(0, index)
        }

        return domainName.replaceFirst("^www.*?\\.", "")
      }
    }

    // if config registy is null 
    if (cfgRegistry == null) {
      closure()
      return 
    }

    // loop in available docker property credentials
    def registry
    cfgRegistry.each { cfg -> 
      def hostname = getHost(cfg.registry)
      if (config.image.contains(hostname)) {
        registry = cfg
      }
    }
    
    // if image is not match in existing docker property we need to return it
    if (registry == null) {
      closure()
      return
    }

    // wrap closure using docker registry
    return script
      .withDockerRegistry([url: registry.registry, credentialsId: registry.credential]) {
        closure()
      }
  }

}