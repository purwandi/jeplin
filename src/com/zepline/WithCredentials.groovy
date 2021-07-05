package com.zepline

class WithCredentials {

  def creds = []

  static def parse(def yaml, def script, def closure) {
    def self = new WithCredentials()

    yaml.each { item ->
      script.sh "echo ${item.type}"
      switch(item.type) {
        case "usernamePassword": 
          return self.passUsernamePassword(item, script)
        case "usernameColonPassword": 
          return self.passUsernameColonPassword(item, script)
        case "file": 
          return self.passFile(item, script)
        case "string": 
          return self.passString(item, script)
        default:
          throw new Exception("Undefined '${item.type}' credential ")
          break
      }
    }

    return script.withCredentials(self.creds) {
      closure()
    }
  }

  def passUsernamePassword(def item, def script) {
    this.creds.add(script.usernamePassword(
      credentialsId: item.credential,
      usernameVariable: "$item.variables.username",
      passwordVariable: "$item.variables.password"
    ))

    return this
  }

  def passUsernameColonPassword(def item, def script) {
    this.creds.add(script.usernameColonPassword(
      credentialsId: item.credential,
      variable: item.variables.variable
    ))

    return this
  }

  def passFile(def item, def script) {
    this.creds.add(script.file(
      credentialsId: item.credential,
      variable: item.variables.variable
    ))

    return this
  }

  def passString(def item, def script) {
    this.creds.add(script.string(
      credentialsId: item.credential,
      variable: item.variables.variable
    ))

    return this
  }
  
}