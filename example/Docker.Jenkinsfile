
node {
  stage ("hello") {
    script {
      sh 'echo "Hello 2"'
      sh 'pwd'
      sh 'env'
      sh 'id'
      sh 'hostname'
    }
  }

  stage ("hello2") {
    docker.image('alpine:latest').inside() {
      script {
        sh 'echo "Hello 2"'
        sh 'pwd'
        sh 'env'
        sh 'id'
        sh 'hostname'
      }
    }
  }

  docker.image('alpine:latest').withRun() {
    stage ("hello2") {
      script {
        sh 'echo "Hello 2"'
        sh 'pwd'
        sh 'env'
        sh 'id'
        sh 'hostname'
      }
    }
  }
}