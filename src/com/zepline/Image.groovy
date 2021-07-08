package com.zepline

class Image {
  String name 
  String entrypoint = ""

  Image(def config) {
    if (config.name == null) {
      this.name = config
    } else {
      this.name       = config.name 
      this.args       = config.args
    }
  }
}