package com.zepline.parser

class Config {
  static Zepline parse(def yaml, def env) {
    Zepline zepline = new Zepline()

    zepline.stages = yaml.stages
  }
}