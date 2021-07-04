node {
   def datas = readYaml text: """
kaniko: &kaniko
  image: docker
  script: 
    - docker build -t xxxx

docker: 
  <<: *kaniko
  variables:
    TEST_VAR: zxxx

// something: 'my second document'
"""

  println(datas.docker)
}

node {
   def datas = readYaml text: """
.docker: &docker
  image: docker
  script: 
    - docker build -t xxxx

docker: 
  <<: *docker
  variables:
    TEST_VAR: zxxx

// something: 'my second document'
"""

  println(datas.docker)
}