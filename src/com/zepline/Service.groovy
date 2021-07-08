package com.zepline

class Service {
  Image         image
  String        alias
  List<String>  entrypoint
  List<String>  command

  Service (def config) {
    config.each { k, v -> 
      if (this.hasProperty(k) && v != null) {
        if (k == "image") {
          this.image = new Image(v)
        } else {
          this."$k" = v
        }
      }
    }
  }
  
}