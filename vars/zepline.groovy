import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  println(config.tasks)

  Zepline zepline = new Zepline(this, config).init()

  // def image = config.image ? config.image : 'alpine:latest'
  // def closure = buildStages(zepline, image)

  def closure = zepline.execute()

  try {
    println(zepline.tasks)
    // stage('test') {
    //   sh 'hello'
    // }
    closure([:])
  } finally {
    log.info("ok")
    cleanWs()
  }
}