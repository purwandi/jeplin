import com.zepline.Zepline

def call(Zepline zepline) {
  return { variables ->
    zepline.tasks.each { task.name ->
      stage(task.name) {
        sh "echo ${task.name}"
      }
    }
  }
}

return this