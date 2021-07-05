import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: ".zepline.yaml"
  if (config == null) {
    return
  }

  def files = readFile ".zepline.yaml"
  
  // clone include project
  if (config.include) {
    config.include.each { item ->
      dir('.include') {
        git branch: item.ref , credentialsId: item.credential, url: item.remote 
      }

      item.files.each { file ->
        def f = readFile "$WORKSPACE/.include${file}"
        print f
        // files = files + readFile "$WORKSPACE/.include/${file}"
      }
    }
  }

  println files
  sh 'ls -all'
}