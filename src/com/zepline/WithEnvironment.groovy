package com.zepline

class WithEnvironment {
  static def parse(def config, def jenkins) {
    if (config.variables == null) {
      return
    }

    config.variables.each { val -> 
      if (jenkins.isUnix()) {
        jenkins.env."${val.key}" = "$(echo ${val.value})"
        // jenkins.env."${val.key}" = jenkins.sh(script: "echo ${val.value}", returnStdout: true).trim()
      } else {
        jenkins.env."${val.key}" = jenkins.powershell(script: "echo ${val.value}", returnStdout: true).trim()
      }
      
    }
  }
}