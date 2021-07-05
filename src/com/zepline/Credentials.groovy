package com.zepline

class Credentials {

  static Credentials parse(def yaml, def script, def closure) {
    def creds = []    
    yaml.each { item ->
      switch(item.type) {
        case "usernamePassword": 
          creds.add(passUsernamePassword(item, script))
          break
        case "usernameColonPassword": 
          creds.add(passUsernameColonPassword(item, script))
          break
        case "file": 
          creds.add(passFile(item, script))
          break
        case "string": 
          creds.add(passString(item, script))
          break
        default:
          throw new Exception("Undefined '${item.type}' credential ")
          break
      }
    }

    return closure()

    // return script.withCredentials(credentials) {
    //   closure()
    // }
  }

  static def passUsernamePassword(def item, def script) {
    return script.usernamePassword(
      credentialsId: item.credential,
      usernameVariable: item.variables.username,
      passwordVariable: item.variables.password
    )
  }

  static def passUsernameColonPassword(def item, def script) {
    return script.usernameColonPassword(
      credentialsId: item.credential,
      variable: item.variables.variable
    )
  }

  static def passFile(def item, def script) {
    return script.file(
      credentialsId: item.credential,
      variable: item.variables.variable
    )
  }

  static def passString(def item, def script) {
    return script.string(
      credentialsId: item.credential,
      variable: item.variables.variable
    )
  }
  
}