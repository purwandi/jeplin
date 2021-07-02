import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  Zepline zepline = new Zepline(this, config).init()

  def image = config.image ? config.image : 'alpine:latest'
  def closure = buildStages(zepline, image)

  try {
    closure([:])
  } finally {
    log.info("ok")
    cleanWs()
  }
}