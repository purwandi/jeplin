import com.zepline.Zepline

def taskable(def tasks) {
  def closure = [:]

  tasks.each { k, task -> 
    closure[k] = {
      stage(k) {
        if (task.config != null && task.config.script != null) {
          sh "echo 'Hello'"
        } else {
          parallel taskable(task)
        }
      }
    }
  }

  return closure
}

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
  Zepline zepline = new Zepline(this, config).init()

  println zepline

  try {
    for (t in taskable(zepline.tasks).values()) {
      t.call()
    }
  } finally {

  }

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