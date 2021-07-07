package com.zepline

class Command {
  static Command parse(def script, def command) {
    if (script.isUnix()) {
      script.sh command
    } else {
      script.bat command
    }
  }
}