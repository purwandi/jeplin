package com.zepline

class Image {
  String name 
  String args = ""

  Image(def config) {
    if (config.name) {
      this.name       = config.name 
      this.args       = config.args
    } else {
      this.name = config
    }
  }
}