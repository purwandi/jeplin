package com.zepline

class Image {
  String name 
  String entrypoint = ""

  Image(def config) {
    if (config.hasProperty("name")) {
      this.name       = config.name 
      this.args       = config.args
    } else {
      this.name = config
    }
  }
}