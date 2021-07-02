import com.zepline.Zepline

def call(Zepline zepline, def defaultImage) {
  return { variables ->
    zepline.tasks.each { task ->
      // setup container image to run
      if (task.image) {
        docker.image(image).inside() {
          stage(task.name) {
            task.run()
          }
        }
      } else {
        stage(task.name) {
          task.run()
        }
      }
    }
  }
}