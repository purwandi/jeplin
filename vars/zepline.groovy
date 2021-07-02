import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  Zepline zepline = new Zepline(this, config).init()
  // def closure = buildStages(zepline)

  def closure = zepline.init()

  try {
    // zepline.run()
    closure([:])
  } finally {
    log.info("ok")
  }
}


return this
// call(".jepline.yaml")