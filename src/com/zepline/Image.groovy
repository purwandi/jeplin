package com.zepline

class Image {
  String name 
  String args = ""

  Image(def config) {
    try {
      this.name       = config.name 
      this.args       = config.args
    } catch (Exception e) {
      this.name = config
    }
  }
}