package com.zepline

class WithEnvironment {
  static def parse(def config, def script, def closure) {
    script.sh "echo $config"
    return {
      script.withEnv(config) {
        closure()
      }
    }
  }
}