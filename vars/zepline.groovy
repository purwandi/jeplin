// @Library('zepline-ci')

import com.zepline.parser.Config
import com.zepline.*

def call(String filename) {
  def config = readYaml file: filename

  Zepline zepline = Config.parse(config, env)

  def closure = buildStages(zepline)

  try {
    closure([:])
  } finally {
    log.info("ok")
  }
  
  // def config = new YamlSlurper().parse(new File(filename))
  println(config)
}

// call(".jepline.yaml")