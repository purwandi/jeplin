import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: ".zepline.yaml"
  if (config == null) {
    return
  }

  def files = readFile file:".zepline.yaml"
  
  // clone include project
  if (config.include) {
    config.include.each { item ->
      dir('.include') {
        git branch: item.ref , credentialsId: item.credential, url: item.remote 
      }

      item.files.each { f ->
        def fx = readFile file: "$WORKSPACE/.include${f}"
        files = "${files}\n\n${fx}"
        // println files
        // files = files + readFile "$WORKSPACE/.include/${file}"
      }
    }
  }

  println files
  config = readYaml text: files

  println config
  println config..helm
  sh 'ls -all'
}