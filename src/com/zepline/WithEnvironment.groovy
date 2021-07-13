package com.zepline

class WithEnvironment {
  static def parse(def config, def jenkins, def closure) {
    return {
      withEnv(config.variables) {
        closure()
      }
    }
  }
}