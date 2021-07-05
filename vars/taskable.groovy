def call(def tasks) {
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