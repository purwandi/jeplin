package com.zepline

class Command {
  static Command parse(def jenkins, def command) {
    if (jenkins.isUnix()) {
      jenkins.sh command
    } else {
      jenkins.powershell command
    }
  }
}