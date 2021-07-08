package com.zepline

class Command {
  static Command parse(def jenkins, def command) {
    if (jenkins.isUnix()) {
      return jenkins.sh (script: command)
    } else {
      return jenkins.bat (script: command)
    }
  }
}