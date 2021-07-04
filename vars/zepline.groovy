import com.zepline.Zepline

def call(String filename) {
  def config = readYaml file: filename
  if (config == null) {
    return
  }

  dir(".config") {
    sh 'cat $WORKSPACE/.zepline.yaml >> $WORKSPACE/.config/.zepline.yaml'
  }
  
  // clone include project
  if (config.includes) {
    config.includes.each { item ->
      dir('.include') {
        git branch: item.ref , credentialsId: item.credential, url: item.remote
        // item.files.each { f -> 
        //   sh "echo ${f}"
        //   sh "cat $WORKSPACE/.include${f}" 
        //   sh "cat $WORKSPACE/.include${f} >> $WORKSPACE/.config/.zepline.yaml" 
        // }
      }
    }
  }

  sh 'ls -all'
  sh 'cat $WORKSPACE/.config/.zepline.yaml'
}