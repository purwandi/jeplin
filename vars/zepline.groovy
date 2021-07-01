// @Library('zepline-ci')

import com.zepline.parser.Config
import com.zepline.*

def call(String filename) {
  def config = readYaml file: filename
  println(config)
  println(config.stages)

  Zepline zepline = Config.parse(config, env)

  println(zepline)
  def closure = buildStages(zepline)

  try {
    pipeline {
      agent none
      closure([:])
    }
  } finally {
    log.info("ok")
  }
 
}

// call(".jepline.yaml")