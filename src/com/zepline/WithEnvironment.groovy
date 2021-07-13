package com.zepline

class WithEnvironment {
  static def parse(def config, def jenkins) {
    jenkins.sh "echo $config"
    jenkins.script {
      config.each { k, v -> 
        if (jenkins.isUnix()) {
          jenkins.env."$k" = jenkins.sh(script: "echo $v", returnStdout: true).trim()
        } else {
          jenkins.env."$k" = jenkins.powershell(script: "echo $v", returnStdout: true).trim()
        }
      }
    }
  }
}