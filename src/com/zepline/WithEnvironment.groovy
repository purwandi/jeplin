package com.zepline

class WithEnvironment {
  static def parse(def config, def jenkins, def closure) {
    jenkins.sh "set +x"
    return {
      jenkins.script {
        config.each { k, v -> 
          if (jenkins.isUnix()) {
            jenkins.env."$k" = jenkins.sh(script: "echo $v", returnStdout: true).trim()
          } else {
            jenkins.env."$k" = jenkins.powershell(script: "echo $v", returnStdout: true).trim()
          }
        }
      }
      jenkins.sh "set -x"
      closure()
    }
  }
}