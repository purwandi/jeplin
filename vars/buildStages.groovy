import com.zepline.Zepline

def call(Zepline zepline) {
  return { variables ->
    zepline.tasks.each { task ->
      stage("cmd") {
        sh "echo 'Hello'"
      }
    }
  }
}