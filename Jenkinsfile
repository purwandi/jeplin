// @Library('zepline-ci') _
library identifier: 'zepline-ci@docker-runner', retriever: modernSCM([
  $class: 'GitSCMSource',
  // credentialsId: 'your-credentials-id',
  remote: 'https://github.com/purwandi/zepline.git'
])

node {
  checkout scm

  zepline()  
}
