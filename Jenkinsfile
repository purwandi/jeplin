// @Library('zepline-ci') _
library identifier: 'zepline-ci@main', retriever: modernSCM([
  $class: 'GitSCMSource',
  // credentialsId: 'your-credentials-id',
  remote: 'https://gitlab.com/purwandi/zepline.git'
])

node {
  checkout scm

  zepline()
}
