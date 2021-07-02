import com.zepline.Zepline

def call(Zepline zepline) {
  return { variables ->
    zepline.tasks.each { stg ->
      stage(stg) {
        sh "echo ${stg}"
      }
    }
  }
}

return this