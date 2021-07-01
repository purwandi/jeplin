package com.zepline.parser

import com.zepline.Zepline

class Config {
  static Zepline parse(def yaml, def env) {
    Zepline zepline = new Zepline()

    zepline.stages = yaml.stages
  }
}