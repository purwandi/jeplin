package com.zepline

class WithEnvironment {
  static def parse(def config, def script) {
    if (config.variables == null) {
      return
    }

    config.variables.each { k, v -> 
      script.env."$k" = v
    }
  }
}