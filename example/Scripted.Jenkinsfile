node {
  stage('prepare') {
    sh 'echo "Prepare"'
  }

  def closure = [:]
  for (sname in ["unit test", "security", "linting"]) {
    closure[sname] = {
      stage(sname) {
        sh "sleep 10"
        sh "echo ${sname}"
      }
    }
  } 

  stage('test') {
    parallel closure
  }

  for (v in closure.values()) {
    v.call()
  }

  stage('post') {
    sh 'echo "post"'
  }
  
}