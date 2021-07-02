import com.zepline.Zepline

def call(Zepline zepline, def defaultImage) {
  return { variables ->
    zepline.tasks.each { task ->
      // setup container image to run
      def image = task.image ? task.image : defaultImage
      
      // run all task inside container
      docker.run() {
        stage(task.name) {
          task.run()
        }
      }
    }
  }
}