package com.zepline

class WithNode {

  static def parse(def config, def jenkins, def closure) {
    return {
      jenkins.node(config) {
        jenkins.checkout scm
        closure()
      }
    }
  }
  
}