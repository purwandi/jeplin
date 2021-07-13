package com.zepline

class WithEnvironment {
  static def parse(def config, def jenkins) {
    jenkins.script {
      config.each { k, v -> 
        jenkins.env."$k" = "$v"
      }
    }
  }
}