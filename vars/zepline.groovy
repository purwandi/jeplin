import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  println(config.steps)

  Zepline zepline = new Zepline(this, config).init()

  // def image = config.image ? config.image : 'alpine:latest'
  // def closure = buildStages(zepline, image)

  def closure = zepline.execute()

  try {
    println(zepline.steps)
    // stage('test') {
    //   sh 'hello'
    // }
    closure([:])
  } finally {
    log.info("ok")
    cleanWs()
  }
}