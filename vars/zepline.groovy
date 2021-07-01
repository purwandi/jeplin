import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  // Zepline zepline = Config.parse(config, env, this)
  Zepline zepline = new Zepline(config).init()

  // println(zepline)
  // println(this)
  // def closure = buildStages(zepline)

  try {
    closure([:])
  } finally {
    log.info("ok")
  }
}


return this
// call(".jepline.yaml")