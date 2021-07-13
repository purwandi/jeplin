package com.zepline

class WithEnvironment {
  static def parse(def config, def script, def closure) {
    return {
      script.withEnv(config.variables) {
        closure()
      }
    }
  }
}