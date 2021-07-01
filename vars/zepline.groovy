import com.zepline.parser.Config
import com.zepline.*

def call(String filename) {
  def config = readYaml file: filename
  Zepline zepline = Config.parse(config, env, this)

  println(zepline)
  def closure = buildStages(zepline)

  try {
    closure([:])
  } finally {
    log.info("ok")
  }
}


return this
// call(".jepline.yaml")