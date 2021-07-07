package com.zepline

class Command {
  static Command parse(def script, def command) {
    if (isUnix()) {
      script.sh command
    } else {
      script.bat command
    }
  }
}