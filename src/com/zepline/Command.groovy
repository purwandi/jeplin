package com.zepline

class Command {
  static Command parse(def jenkins, def command) {
    if (jenkins.isUnix()) {
      jenkins.sh (script: "$command")
    } else {
      jenkins.bat (script: "$command")
    }
  }
}