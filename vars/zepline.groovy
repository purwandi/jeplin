import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: ".zepline.yaml"
  if (config == null) {
    return
  }

  def files = readFile file:".zepline.yaml"
  if (config.include) {
    config.include.each { item ->
      dir('.include') {
        git branch: item.ref , credentialsId: item.credential, url: item.remote 
      }

      item.files.each { f ->
        def fx = readFile file: "$WORKSPACE/.include${f}"
        files = "${files}\n\n${fx}"
      }
    }
  }

  // final project configurations
  config = readYaml text: files
  Zepline zepline = new Zepline(this, config)

  println zepline


  // println files
  

  // println config
  // println config.helm
  // println config.".helm"

  // config.".helm".each { k, v ->
  //   println k
  //   println v
  // }


  // sh 'ls -all'
}