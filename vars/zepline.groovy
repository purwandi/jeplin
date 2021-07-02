import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  Zepline zepline = new Zepline(this, config).init()
  def closure = buildStages(zepline)

  try {
    closure([:])
  } finally {
    log.info("ok")
  }
}